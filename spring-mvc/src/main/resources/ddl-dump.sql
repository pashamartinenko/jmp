--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Debian 15.1-1.pgdg110+1)
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-05 12:37:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 32850)
-- Name: bookings; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA bookings;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 32859)
-- Name: event; Type: TABLE; Schema: bookings; Owner: -
--

CREATE TABLE bookings.event (
    date timestamp(6) without time zone,
    id bigint NOT NULL,
    title character varying(255),
    price bigint
);


--
-- TOC entry 221 (class 1259 OID 41002)
-- Name: ticket; Type: TABLE; Schema: bookings; Owner: -
--

CREATE TABLE bookings.ticket (
    place integer NOT NULL,
    event_id bigint,
    id bigint NOT NULL,
    user_id bigint,
    category character varying(255),
    CONSTRAINT ticket_category_check CHECK (((category)::text = ANY ((ARRAY['STANDARD'::character varying, 'PREMIUM'::character varying, 'BAR'::character varying])::text[])))
);


--
-- TOC entry 216 (class 1259 OID 32851)
-- Name: user; Type: TABLE; Schema: bookings; Owner: -
--

CREATE TABLE bookings."user" (
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255),
    balance bigint
);

--
-- TOC entry 3192 (class 2606 OID 32863)
-- Name: event event_pkey; Type: CONSTRAINT; Schema: bookings; Owner: -
--

ALTER TABLE ONLY bookings.event
    ADD CONSTRAINT event_pkey PRIMARY KEY (id);


--
-- TOC entry 3194 (class 2606 OID 41007)
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: bookings; Owner: -
--

ALTER TABLE ONLY bookings.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);


--
-- TOC entry 3190 (class 2606 OID 32857)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: bookings; Owner: -
--

ALTER TABLE ONLY bookings."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 41013)
-- Name: ticket fkdvt57mcco3ogsosi97odw563o; Type: FK CONSTRAINT; Schema: bookings; Owner: -
--

ALTER TABLE ONLY bookings.ticket
    ADD CONSTRAINT fkdvt57mcco3ogsosi97odw563o FOREIGN KEY (user_id) REFERENCES bookings."user"(id);


--
-- TOC entry 3196 (class 2606 OID 41008)
-- Name: ticket fkfytuhjopeamxbt1cpudy92x5n; Type: FK CONSTRAINT; Schema: bookings; Owner: -
--

ALTER TABLE ONLY bookings.ticket
    ADD CONSTRAINT fkfytuhjopeamxbt1cpudy92x5n FOREIGN KEY (event_id) REFERENCES bookings.event(id);


-- Completed on 2023-05-05 12:37:48

--
-- PostgreSQL database dump complete
--

