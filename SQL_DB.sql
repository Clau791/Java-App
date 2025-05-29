--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: facultate; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.facultate (
    id integer NOT NULL,
    nume character varying(255) NOT NULL
);


ALTER TABLE public.facultate OWNER TO postgres;

--
-- Name: facultate_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.facultate_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.facultate_id_seq OWNER TO postgres;

--
-- Name: facultate_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.facultate_id_seq OWNED BY public.facultate.id;


--
-- Name: facultate_sala; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.facultate_sala (
    id_facultate integer NOT NULL,
    id_sala integer NOT NULL
);


ALTER TABLE public.facultate_sala OWNER TO postgres;

--
-- Name: profesor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profesor (
    id integer NOT NULL,
    nume character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    departament character varying(100),
    facultate_id integer
);


ALTER TABLE public.profesor OWNER TO postgres;

--
-- Name: profesor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.profesor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.profesor_id_seq OWNER TO postgres;

--
-- Name: profesor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.profesor_id_seq OWNED BY public.profesor.id;


--
-- Name: rezervare; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rezervare (
    id integer NOT NULL,
    zi integer NOT NULL,
    ora character varying(15) NOT NULL,
    sala_id integer NOT NULL,
    student_id integer,
    profesor_id integer
);


ALTER TABLE public.rezervare OWNER TO postgres;

--
-- Name: rezervare_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.rezervare_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rezervare_id_seq OWNER TO postgres;

--
-- Name: rezervare_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.rezervare_id_seq OWNED BY public.rezervare.id;


--
-- Name: sala; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sala (
    id integer NOT NULL,
    nume character varying(255) NOT NULL,
    capacitate integer NOT NULL,
    facultate_id integer NOT NULL,
    tip character varying(50) NOT NULL
);


ALTER TABLE public.sala OWNER TO postgres;

--
-- Name: sala_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sala_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sala_id_seq OWNER TO postgres;

--
-- Name: sala_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sala_id_seq OWNED BY public.sala.id;


--
-- Name: student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student (
    id integer NOT NULL,
    nume character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    an_studiu integer,
    nr_matricol integer,
    facultate_id integer,
    nr_rez_disponibil integer,
    grupa integer DEFAULT 0
);


ALTER TABLE public.student OWNER TO postgres;

--
-- Name: student_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.student_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.student_id_seq OWNER TO postgres;

--
-- Name: student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.student_id_seq OWNED BY public.student.id;


--
-- Name: facultate id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultate ALTER COLUMN id SET DEFAULT nextval('public.facultate_id_seq'::regclass);


--
-- Name: profesor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesor ALTER COLUMN id SET DEFAULT nextval('public.profesor_id_seq'::regclass);


--
-- Name: rezervare id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rezervare ALTER COLUMN id SET DEFAULT nextval('public.rezervare_id_seq'::regclass);


--
-- Name: sala id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sala ALTER COLUMN id SET DEFAULT nextval('public.sala_id_seq'::regclass);


--
-- Name: student id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student ALTER COLUMN id SET DEFAULT nextval('public.student_id_seq'::regclass);


--
-- Data for Name: facultate; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.facultate (id, nume) FROM stdin;
1	Matematica si Informatica
2	Istorie
3	Psihologie
4	Geografie
\.


--
-- Data for Name: facultate_sala; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.facultate_sala (id_facultate, id_sala) FROM stdin;
1	1
1	2
1	3
2	5
3	6
2	4
\.


--
-- Data for Name: profesor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.profesor (id, nume, email, departament, facultate_id) FROM stdin;
1	Dr. Elena Georgescu	elena.georgescu@uvt.ro	Matematică Aplicată	1
2	Dr. Mihai Radu	mihai.radu@uvt.ro	Sisteme Energetice	2
3	Dr. Ana Dumitrescu	ana.dumitrescu@uvt.ro	Calculatoare	3
4	Dr. Vlad Popa	vlad.popa@uvt.ro	Informatică Teoretică	1
11	andrei	andrei@unibuc.ro	matematica	1
12	dr. Andrei Iulian	iulian@unibuc.ro	Retele	1
16	claudiu	claudiu@unibuc.ro	Informatica	2
\.


--
-- Data for Name: rezervare; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rezervare (id, zi, ora, sala_id, student_id, profesor_id) FROM stdin;
6	1	08:00-10:00	1	1	\N
7	2	10:00-15:00	3	2	\N
8	3	12:00-13:00	5	\N	3
10	5	16:00-17:00	4	3	\N
11	1	10:00-11:00	1	1	\N
9	4	14:00-16:00	2	12	\N
13	1	12:00-14:00	1	1	\N
14	12	12:00-14:00	5	1	\N
15	2	12:00-13:00	5	1	\N
16	12	12:00-13:00	1	1	\N
17	1	15:00-16:00	12	1	\N
18	1	12:00-14:00	6	1	\N
19	2	12:00-13:00	10	\N	1
21	12	12:00-14:00	10	12	\N
22	17	17:00-18:00	2	13	\N
23	14	15:00-16:00	2	13	\N
24	5	14:00-15:00	14	13	\N
25	23	13:00-15:00	4	7	\N
29	13	11:00-14:00	2	6	\N
30	21	12:00-15:00	15	15	\N
31	15	14:00-15:00	12	15	\N
32	2	1:00-2:00	2	6	\N
33	15	14:00-15:00	2	6	\N
34	14	14:00-15:00	2	6	\N
35	5	14:00-15:00	2	6	\N
36	7	14:00-15:00	2	6	\N
37	2	14:00-15:00	14	16	\N
39	2	14:00-14:30	13	\N	16
\.


--
-- Data for Name: sala; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sala (id, nume, capacitate, facultate_id, tip) FROM stdin;
1	FMI Sala 101	30	1	seminar
2	FMI Sala 102	25	1	seminar
3	FMI Lab 1	20	1	laborator
4	FMI Lab 2	22	1	laborator
5	FMI Amfiteatru A	100	1	amfiteatru
6	Ist Sala 10	40	2	seminar
7	Ist Sala 11	35	2	seminar
8	Ist Lab 1	20	2	laborator
9	Ist Lab 2	25	2	laborator
10	Ist Amfiteatru B	120	2	amfiteatru
11	Geo Sala 20	30	4	seminar
12	Geo Sala 21	28	4	seminar
13	Geo Lab 1	24	4	laborator
14	Geo Lab 2	26	4	laborator
15	Geo Amfiteatru C	110	4	amfiteatru
\.


--
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student (id, nume, email, an_studiu, nr_matricol, facultate_id, nr_rez_disponibil, grupa) FROM stdin;
2	Maria Ionescu	maria.ionescu@stud.uvt.ro	1	3	2	2	0
3	Ion Vasilescu	ion.vasilescu@stud.uvt.ro	3	2	1	1	0
4	Elena Pavel	elena.pavel@stud.uvt.ro	1	5	3	2	0
5	Victor Stan	victor.stan@stud.uvt.ro	2	1	2	3	0
1	Andrei Popescu	andrei.popescu@stud.uvt.ro	2	4	1	0	0
7	Rares 	rares@s.unibuc.ro	2025	6	3	0	0
6	claudiu	claudiu@gmail.com	2023	12	1	3	232
9	clau	clau@gmail.com	2025	22	2	3	0
\.


--
-- Name: facultate_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.facultate_id_seq', 12, true);


--
-- Name: profesor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.profesor_id_seq', 16, true);


--
-- Name: rezervare_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rezervare_id_seq', 39, true);


--
-- Name: sala_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sala_id_seq', 40, true);


--
-- Name: student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.student_id_seq', 9, true);


--
-- Name: facultate facultate_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultate
    ADD CONSTRAINT facultate_pkey PRIMARY KEY (id);


--
-- Name: facultate_sala facultate_sala_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultate_sala
    ADD CONSTRAINT facultate_sala_pkey PRIMARY KEY (id_facultate, id_sala);


--
-- Name: profesor profesor_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesor
    ADD CONSTRAINT profesor_email_key UNIQUE (email);


--
-- Name: profesor profesor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesor
    ADD CONSTRAINT profesor_pkey PRIMARY KEY (id);


--
-- Name: rezervare rezervare_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rezervare
    ADD CONSTRAINT rezervare_pkey PRIMARY KEY (id);


--
-- Name: sala sala_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sala
    ADD CONSTRAINT sala_pkey PRIMARY KEY (id);


--
-- Name: student student_nr_matricol; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_nr_matricol UNIQUE (nr_matricol);


--
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- Name: facultate_sala facultate_sala_id_facultate_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultate_sala
    ADD CONSTRAINT facultate_sala_id_facultate_fkey FOREIGN KEY (id_facultate) REFERENCES public.facultate(id);


--
-- Name: facultate_sala facultate_sala_id_sala_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.facultate_sala
    ADD CONSTRAINT facultate_sala_id_sala_fkey FOREIGN KEY (id_sala) REFERENCES public.sala(id);


--
-- Name: profesor profesor_facultate_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profesor
    ADD CONSTRAINT profesor_facultate_id_fkey FOREIGN KEY (facultate_id) REFERENCES public.facultate(id);


--
-- Name: sala sala_facultate_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sala
    ADD CONSTRAINT sala_facultate_id_fkey FOREIGN KEY (facultate_id) REFERENCES public.facultate(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT USAGE ON SCHEMA public TO clau;


--
-- Name: TABLE facultate; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.facultate TO clau;


--
-- Name: SEQUENCE facultate_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.facultate_id_seq TO clau;


--
-- Name: TABLE facultate_sala; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT ON TABLE public.facultate_sala TO clau;


--
-- Name: TABLE profesor; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.profesor TO clau;


--
-- Name: SEQUENCE profesor_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.profesor_id_seq TO clau;


--
-- Name: TABLE rezervare; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.rezervare TO clau;


--
-- Name: SEQUENCE rezervare_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.rezervare_id_seq TO clau;


--
-- Name: TABLE sala; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.sala TO clau;


--
-- Name: SEQUENCE sala_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.sala_id_seq TO clau;


--
-- Name: TABLE student; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.student TO clau;


--
-- Name: SEQUENCE student_id_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.student_id_seq TO clau;


--
-- PostgreSQL database dump complete
--

