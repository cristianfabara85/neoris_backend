--
-- PostgreSQL database dump
--

-- Dumped from database version 14.11
-- Dumped by pg_dump version 14.11

-- Started on 2024-09-27 17:52:11

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

DROP DATABASE IF EXISTS neorisdata;
--
-- TOC entry 3359 (class 1262 OID 16394)
-- Name: neorisdata; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE neorisdata WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Spain.1252';


ALTER DATABASE neorisdata OWNER TO postgres;

\connect neorisdata

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16446)
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    id numeric NOT NULL,
    client_id numeric NOT NULL,
    number numeric NOT NULL,
    state numeric DEFAULT 1 NOT NULL,
    type numeric NOT NULL,
    initial_balance character varying DEFAULT 0 NOT NULL
);


ALTER TABLE public.account OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16445)
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_id_seq OWNER TO postgres;

--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 213
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;


--
-- TOC entry 215 (class 1259 OID 16490)
-- Name: catalog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.catalog (
    parent_id numeric,
    description character varying(50) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.catalog OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16497)
-- Name: catalog_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.catalog_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.catalog_id_seq OWNER TO postgres;

--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 216
-- Name: catalog_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.catalog_id_seq OWNED BY public.catalog.id;


--
-- TOC entry 210 (class 1259 OID 16403)
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id numeric NOT NULL,
    person_id numeric NOT NULL,
    password character varying(50),
    state numeric(2,0) NOT NULL
);


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16402)
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.client_id_seq OWNER TO postgres;

--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 209
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- TOC entry 218 (class 1259 OID 16551)
-- Name: movements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movements (
    id numeric NOT NULL,
    account_id numeric NOT NULL,
    type numeric NOT NULL,
    amount double precision,
    balance double precision,
    transaction_date timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.movements OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16550)
-- Name: movements_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movements_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movements_id_seq OWNER TO postgres;

--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 217
-- Name: movements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movements_id_seq OWNED BY public.movements.id;


--
-- TOC entry 212 (class 1259 OID 16413)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    person_id numeric NOT NULL,
    name character varying(500) NOT NULL,
    gender numeric,
    age numeric(3,0),
    document_id character varying(10) NOT NULL,
    address character varying(500) NOT NULL,
    phone character varying(10) NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16412)
-- Name: person_person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_person_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_person_id_seq OWNER TO postgres;

--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 211
-- Name: person_person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_person_id_seq OWNED BY public.person.person_id;


--
-- TOC entry 3187 (class 2604 OID 16464)
-- Name: account id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);


--
-- TOC entry 3189 (class 2604 OID 16498)
-- Name: catalog id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.catalog ALTER COLUMN id SET DEFAULT nextval('public.catalog_id_seq'::regclass);


--
-- TOC entry 3184 (class 2604 OID 16455)
-- Name: client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- TOC entry 3190 (class 2604 OID 16559)
-- Name: movements id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movements ALTER COLUMN id SET DEFAULT nextval('public.movements_id_seq'::regclass);


--
-- TOC entry 3185 (class 2604 OID 16421)
-- Name: person person_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN person_id SET DEFAULT nextval('public.person_person_id_seq'::regclass);


--
-- TOC entry 3349 (class 0 OID 16446)
-- Dependencies: 214
-- Data for Name: account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (1, 1, 1760552, 13, 9, '200.65');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (2, 1, 23587541, 13, 8, '100');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (5, 20, 1057376913, 13, 8, '300');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (6, 20, 4587965410, 13, 9, '50');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (15, 23, 987654314, 13, 8, '100');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (14, 22, 987654213, 13, 8, '100.54');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (16, 22, 547845621, 13, 8, '0');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (17, 24, 478758, 13, 8, '2000');
INSERT INTO public.account (id, client_id, number, state, type, initial_balance) VALUES (18, 24, 585545, 14, 9, '1000');


--
-- TOC entry 3350 (class 0 OID 16490)
-- Dependencies: 215
-- Data for Name: catalog; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.catalog (parent_id, description, id) VALUES (NULL, 'Genero', 1);
INSERT INTO public.catalog (parent_id, description, id) VALUES (NULL, 'Tipo_cuenta', 2);
INSERT INTO public.catalog (parent_id, description, id) VALUES (NULL, 'Tipo_movimiento', 3);
INSERT INTO public.catalog (parent_id, description, id) VALUES (NULL, 'Estado', 4);
INSERT INTO public.catalog (parent_id, description, id) VALUES (1, 'Masculino', 5);
INSERT INTO public.catalog (parent_id, description, id) VALUES (1, 'Femenino', 6);
INSERT INTO public.catalog (parent_id, description, id) VALUES (1, 'No definido', 7);
INSERT INTO public.catalog (parent_id, description, id) VALUES (2, 'Ahorro', 8);
INSERT INTO public.catalog (parent_id, description, id) VALUES (2, 'Corriente', 9);
INSERT INTO public.catalog (parent_id, description, id) VALUES (2, 'Poliza', 10);
INSERT INTO public.catalog (parent_id, description, id) VALUES (3, 'Deposito', 11);
INSERT INTO public.catalog (parent_id, description, id) VALUES (3, 'Retiro', 12);
INSERT INTO public.catalog (parent_id, description, id) VALUES (4, 'activo', 13);
INSERT INTO public.catalog (parent_id, description, id) VALUES (5, 'inactivo', 14);
INSERT INTO public.catalog (parent_id, description, id) VALUES (3, 'Deposito_inicial', 15);


--
-- TOC entry 3345 (class 0 OID 16403)
-- Dependencies: 210
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.client (id, person_id, password, state) VALUES (1, 1, 'prueba_123', 13);
INSERT INTO public.client (id, person_id, password, state) VALUES (20, 38, 'Password_vsantos', 13);
INSERT INTO public.client (id, person_id, password, state) VALUES (21, 39, 'Password_rriofiro', 13);
INSERT INTO public.client (id, person_id, password, state) VALUES (22, 40, 'Password_gfabara', 13);
INSERT INTO public.client (id, person_id, password, state) VALUES (23, 41, 'Password_dfabara', 13);
INSERT INTO public.client (id, person_id, password, state) VALUES (24, 42, '1234a', 13);


--
-- TOC entry 3353 (class 0 OID 16551)
-- Dependencies: 218
-- Data for Name: movements; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (8, 14, 15, 100.54, 100.54, '2024-09-27 09:53:41.377');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (1, 1, 15, 150.58, 351.23, '2024-09-27 16:59:44.975061');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (9, 15, 15, 100, 100, '2024-09-27 10:06:07.947');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (10, 16, 15, 0, 0, '2024-09-27 10:18:57.62');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (18, 14, 12, 100, 0.54, '2024-09-27 14:07:52.968');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (19, 14, 12, 0.53, 0.01, '2024-09-27 14:08:17.839');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (20, 14, 11, 200.49, 200.5, '2024-09-27 14:08:52.063');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (21, 14, 12, 200.5, 0, '2024-09-27 14:09:16.159');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (24, 14, 11, 5, 5, '2024-09-27 14:15:01.768');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (25, 14, 11, 16.5, 21.5, '2024-09-27 14:15:13.731');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (26, 14, 12, 5, 16.5, '2024-09-27 14:17:17.863');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (27, 14, 12, 16, 0.5, '2024-09-27 14:17:25.92');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (28, 14, 12, 0.5, 0, '2024-09-27 14:17:33.846');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (29, 14, 11, 10, 10, '2024-09-27 14:18:01.491');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (30, 14, 11, 13.5, 23.5, '2024-09-27 14:18:09.552');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (31, 14, 11, 10, 33.5, '2024-09-27 14:34:33.863');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (32, 14, 12, 30, 3.5, '2024-09-27 14:35:04.164');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (33, 14, 12, 3.5, 0, '2024-09-27 14:35:23.756');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (34, 14, 11, 100, 100, '2024-09-27 14:35:47.759');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (35, 14, 12, 14.58, 85.42, '2024-09-27 14:36:17.756');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (36, 17, 15, 2000, 2000, '2024-09-27 14:40:55.029');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (37, 18, 15, 1000, 1000, '2024-09-27 14:41:26.904');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (38, 17, 12, 575, 1425, '2024-09-27 14:42:55.675');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (39, 14, 11, 10, 95.42, '2024-09-27 17:17:30.297');
INSERT INTO public.movements (id, account_id, type, amount, balance, transaction_date) VALUES (40, 14, 12, 5, 90.42, '2024-09-27 17:17:49.435');


--
-- TOC entry 3347 (class 0 OID 16413)
-- Dependencies: 212
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (person_id, name, gender, age, document_id, address, phone) VALUES (1, 'Christian Fabara', 5, 38, '1714717277', 'Julian estrella s29-123', '0983334621');
INSERT INTO public.person (person_id, name, gender, age, document_id, address, phone) VALUES (38, 'Viviana Santos Negrete', 6, 39, '1719211383', 'Chillogallo Casa 7', '0989079772');
INSERT INTO public.person (person_id, name, gender, age, document_id, address, phone) VALUES (39, 'Rita Riofrio', 6, 39, '1709276792', 'Caminos del sur', '0997956244');
INSERT INTO public.person (person_id, name, gender, age, document_id, address, phone) VALUES (40, 'Gabriel Fabara Santos', 5, 18, '1758875650', 'Chillogallo', '0997956244');
INSERT INTO public.person (person_id, name, gender, age, document_id, address, phone) VALUES (41, 'Daniela Fabara Santos', 6, 18, '1758875668', 'Chillogallo', '0997956244');
INSERT INTO public.person (person_id, name, gender, age, document_id, address, phone) VALUES (42, 'Jose Lema', 5, 30, '1700334764', 'Otavalo sn y principal ', '098254785');


--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 213
-- Name: account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.account_id_seq', 18, true);


--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 216
-- Name: catalog_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.catalog_id_seq', 15, true);


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 209
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 24, true);


--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 217
-- Name: movements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movements_id_seq', 40, true);


--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 211
-- Name: person_person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_person_id_seq', 42, true);


-- Completed on 2024-09-27 17:52:11

--
-- PostgreSQL database dump complete
--

