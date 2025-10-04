--
-- PostgreSQL database dump
--

\restrict 8ohQ9QORiUW0iW4pFzCW3hLZn5RLkNbyLrM0hLNFW94jDGcb4xWDNwcGG3cO2gM

-- Dumped from database version 18.0 (Debian 18.0-1.pgdg13+3)
-- Dumped by pg_dump version 18.0 (Debian 18.0-1.pgdg13+3)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: rule_amount_limit; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.rule_amount_limit (id, amount_limit, transaction_type) VALUES (1, 50000.00, 'WIRE');
INSERT INTO public.rule_amount_limit (id, amount_limit, transaction_type) VALUES (2, 5000.00, 'CARD');
INSERT INTO public.rule_amount_limit (id, amount_limit, transaction_type) VALUES (3, 10000.00, 'CRYPTO');
INSERT INTO public.rule_amount_limit (id, amount_limit, transaction_type) VALUES (4, 25000.00, 'FX');


--
-- Data for Name: rule_exchange_rate; Type: TABLE DATA; Schema: public; Owner: admin
--



--
-- Data for Name: rule_master; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (1, NULL, 1, 'AMOUNT_LIMIT', 'WIRE');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (2, NULL, 2, 'SANCTION', 'WIRE');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (3, NULL, 1, 'AMOUNT_LIMIT', 'CARD');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (4, NULL, 2, 'LUHN_CHECK', 'CARD');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (5, NULL, 3, 'SANCTION', 'CARD');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (6, NULL, 1, 'AMOUNT_LIMIT', 'CRYPTO');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (7, NULL, 2, 'SANCTION', 'CRYPTO');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (8, NULL, 1, 'AMOUNT_LIMIT', 'FX');
INSERT INTO public.rule_master (id, post_validation, priority, rule, type) VALUES (9, NULL, 2, 'SANCTION', 'FX');


--
-- Data for Name: rule_sanction; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('RU', true, 'Military aggression and violation of international law');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('IR', true, 'Nuclear program violations and human rights concerns');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('KP', true, 'Nuclear weapons development and human rights violations');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('AF', true, 'Terrorism support and Taliban government');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('SY', true, 'Civil war and chemical weapons usage');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('VE', true, 'Authoritarian governance and human rights violations');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('MM', true, 'Military coup and human rights violations');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('BY', true, 'Electoral fraud and human rights violations');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('CU', false, 'Human rights violations and authoritarian regime');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('SD', false, 'Past conflicts and humanitarian concerns');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('LY', false, 'Political instability and past conflicts');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('ZW', false, 'Electoral irregularities and governance issues');
INSERT INTO public.rule_sanction (country_code, active, reason) VALUES ('IQ', false, 'Post-conflict reconstruction period');


--
-- Name: rule_amount_limit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.rule_amount_limit_id_seq', 4, true);


--
-- Name: rule_master_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.rule_master_id_seq', 9, true);


--
-- PostgreSQL database dump complete
--

\unrestrict 8ohQ9QORiUW0iW4pFzCW3hLZn5RLkNbyLrM0hLNFW94jDGcb4xWDNwcGG3cO2gM

