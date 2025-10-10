package com.cs.ClearingStreams.services.routes;

import com.cs.ClearingStreams.dtos.CanonicalTransactionDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

@Component
public class RouteCriteriaEvaluator {

    private final Map<String, Function<CanonicalTransactionDto, Object>> fieldAccessors;

    public RouteCriteriaEvaluator() {
        this.fieldAccessors = Map.ofEntries(
                // Root-level fields
                Map.entry("id", CanonicalTransactionDto::getId),
                Map.entry("type", dto -> dto.getType() != null ? dto.getType().name() : null),
                Map.entry("timestamp", CanonicalTransactionDto::getTimestamp),
                Map.entry("amount", CanonicalTransactionDto::getAmount),
                Map.entry("currency", dto -> dto.getCurrency() != null ? dto.getCurrency().getCurrencyCode() : null),
                Map.entry("instrument", CanonicalTransactionDto::getInstrument),

                // Nested fields
                Map.entry("payer.account", dto -> dto.getPayer() != null ? dto.getPayer().getAccount() : null),
                Map.entry("payer.country", dto -> dto.getPayer() != null ? dto.getPayer().getCountry().getCountry() : null),
                Map.entry("payee.account", dto -> dto.getPayee() != null ? dto.getPayee().getAccount() : null),
                Map.entry("payee.country", dto -> dto.getPayee() != null ? dto.getPayee().getCountry().getCountry() : null)
        );
    }

    public boolean matches(CanonicalTransactionDto ctd, JsonNode criteria, Map<String, Object> variables) {
        if (criteria == null || ctd == null) return false;

        if (criteria.has("and")) {
            for (JsonNode node : criteria.get("and")) {
                if (!matches(ctd, node, variables)) return false;
            }
            return true;
        } else if (criteria.has("or")) {
            for (JsonNode node : criteria.get("or")) {
                if (matches(ctd, node, variables)) return true;
            }
            return false;
        } else {
            return evaluateCondition(ctd, criteria, variables);
        }
    }

    private boolean evaluateCondition(CanonicalTransactionDto ctd, JsonNode condition, Map<String, Object> variables) {
        String fieldPath = condition.get("field").asText();

        Function<CanonicalTransactionDto, Object> accessor = fieldAccessors.get(fieldPath);
        if (accessor == null)
            throw new IllegalArgumentException("Unknown field: " + fieldPath);

        Object leftVal = accessor.apply(ctd);

        String operator = null;
        JsonNode rhsNode = null;
        for (Iterator<Map.Entry<String, JsonNode>> it = condition.fields(); it.hasNext();) {
            Map.Entry<String, JsonNode> entry = it.next();
            if (!"field".equals(entry.getKey())) {
                operator = entry.getKey();
                rhsNode = entry.getValue();
                break;
            }
        }

        if (operator == null)
            throw new IllegalArgumentException("No operator found in condition: " + condition);

        Object rhsVal = resolveValue(rhsNode, variables);

        return compareValues(leftVal, rhsVal, operator);
    }

    private Object resolveValue(JsonNode node, Map<String, Object> variables) {
        if (node.isTextual() && node.asText().startsWith("$")) {
            return variables.get(node.asText().substring(1));
        } else if (node.isArray()) {
            List<Object> list = new ArrayList<>();
            for (JsonNode item : node) list.add(resolveValue(item, variables));
            return list;
        } else if (node.isNumber()) {
            return node.decimalValue();
        } else if (node.isTextual()) {
            return node.asText();
        }
        return node.toString();
    }

    @SuppressWarnings("unchecked")
    private boolean compareValues(Object left, Object right, String operator) {
        if (left == null || right == null) return false;

        // Handle 'in' and 'notIn' for lists
        if (right instanceof Collection<?> list) {
            return switch (operator) {
                case "in" -> list.contains(normalize(left));
                case "notIn" -> !list.contains(normalize(left));
                default -> false;
            };
        }

        Object normLeft = normalize(left);
        Object normRight = normalize(right);

        int cmp = compare(normLeft, normRight);

        return switch (operator) {
            case "eq" -> Objects.equals(normLeft, normRight);
            case "ne" -> !Objects.equals(normLeft, normRight);
            case "gt" -> cmp > 0;
            case "gte" -> cmp >= 0;
            case "lt" -> cmp < 0;
            case "lte" -> cmp <= 0;
            default -> false;
        };
    }

    private Object normalize(Object val) {
        if (val instanceof Currency c) return c.getCurrencyCode();
        if (val instanceof Locale l) return l.getCountry();
        if (val instanceof Enum<?> e) return e.name();
        if (val instanceof BigDecimal b) return b.stripTrailingZeros();
        return val;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private int compare(Object left, Object right) {
        if (left instanceof Comparable l && right instanceof Comparable r) {
            try {
                return l.compareTo(r);
            } catch (ClassCastException e) {
                return l.toString().compareTo(r.toString());
            }
        }
        return 0;
    }
}


