package com.cs.ClearingStreams.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleResult<T> {

    private boolean success;
    private T data;
    private List<RuleFailure> failures;


    public static <T> RuleResult<T> pass() {
        return new RuleResult<>(true, null, null);
    }

    public static <T> RuleResult<T> fail(T data, String code, String message, String... fieldNames) {
        String fieldPath = String.join(".", fieldNames);
        return new RuleResult<>(false, data, List.of(new RuleFailure(code, message, fieldPath)));
    }

    public static <T> RuleResult<T> fail(T data, List<RuleFailure> failures) {
        return new RuleResult<>(false, data, failures);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RuleFailure {
        private String code;
        private String message;
        private String field;

        public RuleFailure(String code, String message, String... fieldNames) {
            this.code = code;
            this.message = message;
            this.field = String.join(".", fieldNames);
        }
    }

}
