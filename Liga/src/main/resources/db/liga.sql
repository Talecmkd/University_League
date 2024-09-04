--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

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
-- Name: basketball_match; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.basketball_match (
    basketball_match_id bigint NOT NULL,
    away_team_points integer NOT NULL,
    end_time timestamp(6) without time zone,
    home_team_points integer NOT NULL,
    is_basketball_playoff_match boolean,
    start_time timestamp(6) without time zone,
    basketball_away_id bigint,
    basketball_home_id bigint
);


ALTER TABLE public.basketball_match OWNER TO liga;

--
-- Name: basketball_match_basketball_match_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.basketball_match_basketball_match_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.basketball_match_basketball_match_id_seq OWNER TO liga;

--
-- Name: basketball_match_basketball_match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.basketball_match_basketball_match_id_seq OWNED BY public.basketball_match.basketball_match_id;


--
-- Name: basketball_player; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.basketball_player (
    basketball_player_id bigint NOT NULL,
    appearances integer NOT NULL,
    assists integer NOT NULL,
    birthdate timestamp(6) without time zone,
    city character varying(255),
    basketball_profile_image oid,
    index integer NOT NULL,
    name character varying(255),
    points integer NOT NULL,
    "position" character varying(255),
    rebounds integer NOT NULL,
    surname character varying(255),
    team_id bigint
);


ALTER TABLE public.basketball_player OWNER TO liga;

--
-- Name: basketball_player_basketball_player_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.basketball_player_basketball_player_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.basketball_player_basketball_player_id_seq OWNER TO liga;

--
-- Name: basketball_player_basketball_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.basketball_player_basketball_player_id_seq OWNED BY public.basketball_player.basketball_player_id;


--
-- Name: basketball_player_match_stats; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.basketball_player_match_stats (
    id bigint NOT NULL,
    assists integer NOT NULL,
    points_scored integer NOT NULL,
    rebounds integer NOT NULL,
    basketball_match_id bigint NOT NULL,
    basketball_player_id bigint NOT NULL
);


ALTER TABLE public.basketball_player_match_stats OWNER TO liga;

--
-- Name: basketball_player_match_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.basketball_player_match_stats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.basketball_player_match_stats_id_seq OWNER TO liga;

--
-- Name: basketball_player_match_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.basketball_player_match_stats_id_seq OWNED BY public.basketball_player_match_stats.id;


--
-- Name: basketball_team; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.basketball_team (
    id bigint NOT NULL,
    logo bytea,
    team_league_points integer NOT NULL,
    team_loses integer NOT NULL,
    team_matches_played integer NOT NULL,
    team_name character varying(255),
    team_wins integer NOT NULL
);


ALTER TABLE public.basketball_team OWNER TO liga;

--
-- Name: basketball_team_fixtures; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.basketball_team_fixtures (
    basketball_team_id bigint NOT NULL,
    basketball_match_id bigint NOT NULL
);


ALTER TABLE public.basketball_team_fixtures OWNER TO liga;

--
-- Name: basketball_team_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.basketball_team_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.basketball_team_id_seq OWNER TO liga;

--
-- Name: basketball_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.basketball_team_id_seq OWNED BY public.basketball_team.id;


--
-- Name: basketball_team_results; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.basketball_team_results (
    basketball_team_id bigint NOT NULL,
    basketball_match_id bigint NOT NULL
);


ALTER TABLE public.basketball_team_results OWNER TO liga;

--
-- Name: football_match; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.football_match (
    football_match_id bigint NOT NULL,
    away_team_points integer NOT NULL,
    end_time timestamp(6) without time zone,
    home_team_points integer NOT NULL,
    is_playoff_match boolean,
    start_time timestamp(6) without time zone,
    football_away_id bigint,
    football_home_id bigint
);


ALTER TABLE public.football_match OWNER TO liga;

--
-- Name: football_match_football_match_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.football_match_football_match_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.football_match_football_match_id_seq OWNER TO liga;

--
-- Name: football_match_football_match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.football_match_football_match_id_seq OWNED BY public.football_match.football_match_id;


--
-- Name: football_player; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.football_player (
    football_player_id bigint NOT NULL,
    appearances integer NOT NULL,
    assists integer NOT NULL,
    birthdate timestamp(6) without time zone,
    city character varying(255),
    goals integer NOT NULL,
    profile_image oid,
    index integer NOT NULL,
    name character varying(255),
    "position" character varying(255),
    saves integer NOT NULL,
    surname character varying(255),
    team_id bigint
);


ALTER TABLE public.football_player OWNER TO liga;

--
-- Name: football_player_football_player_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.football_player_football_player_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.football_player_football_player_id_seq OWNER TO liga;

--
-- Name: football_player_football_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.football_player_football_player_id_seq OWNED BY public.football_player.football_player_id;


--
-- Name: football_player_scored; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.football_player_scored (
    football_player_id bigint NOT NULL,
    assists_scored integer NOT NULL,
    goals_scored integer NOT NULL,
    saves integer NOT NULL,
    time_scored timestamp(6) without time zone,
    football_match_id bigint
);


ALTER TABLE public.football_player_scored OWNER TO liga;

--
-- Name: football_team; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.football_team (
    id bigint NOT NULL,
    goal_difference integer NOT NULL,
    goals_against integer NOT NULL,
    goals_for integer NOT NULL,
    logo bytea,
    team_draws integer NOT NULL,
    team_league_points integer NOT NULL,
    team_loses integer NOT NULL,
    team_matches_played integer NOT NULL,
    team_name character varying(255),
    team_wins integer NOT NULL
);


ALTER TABLE public.football_team OWNER TO liga;

--
-- Name: football_team_fixtures; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.football_team_fixtures (
    football_team_id bigint NOT NULL,
    football_match_id bigint NOT NULL
);


ALTER TABLE public.football_team_fixtures OWNER TO liga;

--
-- Name: football_team_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.football_team_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.football_team_id_seq OWNER TO liga;

--
-- Name: football_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.football_team_id_seq OWNED BY public.football_team.id;


--
-- Name: football_team_results; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.football_team_results (
    football_team_id bigint NOT NULL,
    football_match_id bigint NOT NULL
);


ALTER TABLE public.football_team_results OWNER TO liga;

--
-- Name: playoff; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.playoff (
    id bigint NOT NULL
);


ALTER TABLE public.playoff OWNER TO liga;

--
-- Name: playoff_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.playoff_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.playoff_id_seq OWNER TO liga;

--
-- Name: playoff_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.playoff_id_seq OWNED BY public.playoff.id;


--
-- Name: playoff_match; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.playoff_match (
    id bigint NOT NULL,
    away_team_points integer NOT NULL,
    home_team_points integer NOT NULL,
    is_completed boolean NOT NULL,
    away_team_id bigint,
    home_team_id bigint,
    stage_id bigint
);


ALTER TABLE public.playoff_match OWNER TO liga;

--
-- Name: playoff_match_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.playoff_match_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.playoff_match_id_seq OWNER TO liga;

--
-- Name: playoff_match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.playoff_match_id_seq OWNED BY public.playoff_match.id;


--
-- Name: playoff_stage; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.playoff_stage (
    id bigint NOT NULL,
    stage_number integer NOT NULL,
    playoff_id bigint
);


ALTER TABLE public.playoff_stage OWNER TO liga;

--
-- Name: playoff_stage_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.playoff_stage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.playoff_stage_id_seq OWNER TO liga;

--
-- Name: playoff_stage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.playoff_stage_id_seq OWNED BY public.playoff_stage.id;


--
-- Name: team_last_five_matches; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.team_last_five_matches (
    team_id bigint NOT NULL,
    match_result character varying(255)
);


ALTER TABLE public.team_last_five_matches OWNER TO liga;

--
-- Name: volleyball_match; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.volleyball_match (
    volleyball_match_id bigint NOT NULL,
    away_team_points integer NOT NULL,
    end_time timestamp(6) without time zone,
    home_team_points integer NOT NULL,
    is_volleyball_playoff_match boolean,
    start_time timestamp(6) without time zone,
    volleyball_away_id bigint,
    volleyball_home_id bigint
);


ALTER TABLE public.volleyball_match OWNER TO liga;

--
-- Name: volleyball_match_volleyball_match_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.volleyball_match_volleyball_match_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.volleyball_match_volleyball_match_id_seq OWNER TO liga;

--
-- Name: volleyball_match_volleyball_match_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.volleyball_match_volleyball_match_id_seq OWNED BY public.volleyball_match.volleyball_match_id;


--
-- Name: volleyball_player; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.volleyball_player (
    volleyball_player_id bigint NOT NULL,
    appearances integer NOT NULL,
    assists integer NOT NULL,
    birthdate timestamp(6) without time zone,
    blocks integer NOT NULL,
    city character varying(255),
    image oid,
    index integer NOT NULL,
    name character varying(255),
    "position" character varying(255),
    scored_points integer NOT NULL,
    servings integer NOT NULL,
    surname character varying(255),
    team_volleyball_team_id bigint
);


ALTER TABLE public.volleyball_player OWNER TO liga;

--
-- Name: volleyball_player_match_stats; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.volleyball_player_match_stats (
    id bigint NOT NULL,
    assists integer NOT NULL,
    blocks integer NOT NULL,
    scored_points integer NOT NULL,
    servings integer NOT NULL,
    volleyball_player_id bigint NOT NULL,
    volleyball_match_id bigint NOT NULL
);


ALTER TABLE public.volleyball_player_match_stats OWNER TO liga;

--
-- Name: volleyball_player_match_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.volleyball_player_match_stats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.volleyball_player_match_stats_id_seq OWNER TO liga;

--
-- Name: volleyball_player_match_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.volleyball_player_match_stats_id_seq OWNED BY public.volleyball_player_match_stats.id;


--
-- Name: volleyball_player_volleyball_player_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.volleyball_player_volleyball_player_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.volleyball_player_volleyball_player_id_seq OWNER TO liga;

--
-- Name: volleyball_player_volleyball_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.volleyball_player_volleyball_player_id_seq OWNED BY public.volleyball_player.volleyball_player_id;


--
-- Name: volleyball_team; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.volleyball_team (
    volleyball_team_id bigint NOT NULL,
    logo bytea,
    team_league_points integer NOT NULL,
    team_loses integer NOT NULL,
    team_matches_played integer NOT NULL,
    team_name character varying(255),
    team_wins integer NOT NULL
);


ALTER TABLE public.volleyball_team OWNER TO liga;

--
-- Name: volleyball_team_fixtures; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.volleyball_team_fixtures (
    volleyball_team_id bigint NOT NULL,
    volleyball_match_id bigint NOT NULL
);


ALTER TABLE public.volleyball_team_fixtures OWNER TO liga;

--
-- Name: volleyball_team_results; Type: TABLE; Schema: public; Owner: liga
--

CREATE TABLE public.volleyball_team_results (
    volleyball_team_id bigint NOT NULL,
    volleyball_match_id bigint NOT NULL
);


ALTER TABLE public.volleyball_team_results OWNER TO liga;

--
-- Name: volleyball_team_volleyball_team_id_seq; Type: SEQUENCE; Schema: public; Owner: liga
--

CREATE SEQUENCE public.volleyball_team_volleyball_team_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.volleyball_team_volleyball_team_id_seq OWNER TO liga;

--
-- Name: volleyball_team_volleyball_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: liga
--

ALTER SEQUENCE public.volleyball_team_volleyball_team_id_seq OWNED BY public.volleyball_team.volleyball_team_id;


--
-- Name: basketball_match basketball_match_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_match ALTER COLUMN basketball_match_id SET DEFAULT nextval('public.basketball_match_basketball_match_id_seq'::regclass);


--
-- Name: basketball_player basketball_player_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player ALTER COLUMN basketball_player_id SET DEFAULT nextval('public.basketball_player_basketball_player_id_seq'::regclass);


--
-- Name: basketball_player_match_stats id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player_match_stats ALTER COLUMN id SET DEFAULT nextval('public.basketball_player_match_stats_id_seq'::regclass);


--
-- Name: basketball_team id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_team ALTER COLUMN id SET DEFAULT nextval('public.basketball_team_id_seq'::regclass);


--
-- Name: football_match football_match_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_match ALTER COLUMN football_match_id SET DEFAULT nextval('public.football_match_football_match_id_seq'::regclass);


--
-- Name: football_player football_player_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_player ALTER COLUMN football_player_id SET DEFAULT nextval('public.football_player_football_player_id_seq'::regclass);


--
-- Name: football_team id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_team ALTER COLUMN id SET DEFAULT nextval('public.football_team_id_seq'::regclass);


--
-- Name: playoff id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff ALTER COLUMN id SET DEFAULT nextval('public.playoff_id_seq'::regclass);


--
-- Name: playoff_match id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_match ALTER COLUMN id SET DEFAULT nextval('public.playoff_match_id_seq'::regclass);


--
-- Name: playoff_stage id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_stage ALTER COLUMN id SET DEFAULT nextval('public.playoff_stage_id_seq'::regclass);


--
-- Name: volleyball_match volleyball_match_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_match ALTER COLUMN volleyball_match_id SET DEFAULT nextval('public.volleyball_match_volleyball_match_id_seq'::regclass);


--
-- Name: volleyball_player volleyball_player_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player ALTER COLUMN volleyball_player_id SET DEFAULT nextval('public.volleyball_player_volleyball_player_id_seq'::regclass);


--
-- Name: volleyball_player_match_stats id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player_match_stats ALTER COLUMN id SET DEFAULT nextval('public.volleyball_player_match_stats_id_seq'::regclass);


--
-- Name: volleyball_team volleyball_team_id; Type: DEFAULT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_team ALTER COLUMN volleyball_team_id SET DEFAULT nextval('public.volleyball_team_volleyball_team_id_seq'::regclass);


--
-- Data for Name: basketball_match; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.basketball_match (basketball_match_id, away_team_points, end_time, home_team_points, is_basketball_playoff_match, start_time, basketball_away_id, basketball_home_id) FROM stdin;
1	0	2024-09-04 19:05:54.118153	0	f	2024-09-04 17:05:54.118153	2	1
2	0	2024-09-04 19:05:54.129157	0	f	2024-09-04 17:05:54.129157	3	2
3	0	2024-09-04 19:05:54.147156	0	f	2024-09-04 17:05:54.147156	4	3
4	0	2024-09-04 19:05:54.166153	0	f	2024-09-04 17:05:54.166153	5	4
5	0	2024-09-04 19:05:54.191156	0	f	2024-09-04 17:05:54.191156	1	5
6	0	2024-09-04 19:05:54.207152	0	f	2024-09-04 17:05:54.207152	2	6
7	0	2024-09-04 19:05:54.225152	0	f	2024-09-04 17:05:54.225152	3	7
8	0	2024-09-04 19:05:54.240152	0	f	2024-09-04 17:05:54.240152	4	8
\.


--
-- Data for Name: basketball_player; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.basketball_player (basketball_player_id, appearances, assists, birthdate, city, basketball_profile_image, index, name, points, "position", rebounds, surname, team_id) FROM stdin;
1	0	0	\N	\N	\N	1	Player 1	0	\N	0	Surname1	1
2	0	0	\N	\N	\N	2	Player 2	0	\N	0	Surname2	2
3	0	0	\N	\N	\N	3	Player 3	0	\N	0	Surname3	3
4	0	0	\N	\N	\N	4	Player 4	0	\N	0	Surname4	4
5	0	0	\N	\N	\N	5	Player 5	0	\N	0	Surname5	5
6	0	0	\N	\N	\N	6	Player 6	0	\N	0	Surname6	6
7	0	0	\N	\N	\N	7	Player 7	0	\N	0	Surname7	7
8	0	0	\N	\N	\N	8	Player 8	0	\N	0	Surname8	8
\.


--
-- Data for Name: basketball_player_match_stats; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.basketball_player_match_stats (id, assists, points_scored, rebounds, basketball_match_id, basketball_player_id) FROM stdin;
\.


--
-- Data for Name: basketball_team; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.basketball_team (id, logo, team_league_points, team_loses, team_matches_played, team_name, team_wins) FROM stdin;
1	\N	0	0	0	Team 1	0
2	\N	0	0	0	Team 2	0
3	\N	0	0	0	Team 3	0
4	\N	0	0	0	Team 4	0
5	\N	0	0	0	Team 5	0
6	\N	0	0	0	Team 6	0
7	\N	0	0	0	Team 7	0
8	\N	0	0	0	Team 8	0
\.


--
-- Data for Name: basketball_team_fixtures; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.basketball_team_fixtures (basketball_team_id, basketball_match_id) FROM stdin;
5	4
5	5
1	1
1	5
6	6
2	1
2	2
2	6
7	7
3	2
3	3
3	7
8	8
4	3
4	4
4	8
\.


--
-- Data for Name: basketball_team_results; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.basketball_team_results (basketball_team_id, basketball_match_id) FROM stdin;
\.


--
-- Data for Name: football_match; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.football_match (football_match_id, away_team_points, end_time, home_team_points, is_playoff_match, start_time, football_away_id, football_home_id) FROM stdin;
1	0	2024-09-04 19:05:53.780225	0	f	2024-09-04 17:05:53.780225	2	1
2	0	2024-09-04 19:05:53.816226	0	f	2024-09-04 17:05:53.816226	3	2
3	0	2024-09-04 19:05:53.843335	0	f	2024-09-04 17:05:53.843335	4	3
4	0	2024-09-04 19:05:53.861064	0	f	2024-09-04 17:05:53.861064	5	4
5	0	2024-09-04 19:05:53.881524	0	f	2024-09-04 17:05:53.881524	1	5
6	0	2024-09-04 19:05:53.899445	0	f	2024-09-04 17:05:53.899445	2	6
7	0	2024-09-04 19:05:53.91948	0	f	2024-09-04 17:05:53.91948	3	7
8	0	2024-09-04 19:05:53.954447	0	f	2024-09-04 17:05:53.954447	4	8
\.


--
-- Data for Name: football_player; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.football_player (football_player_id, appearances, assists, birthdate, city, goals, profile_image, index, name, "position", saves, surname, team_id) FROM stdin;
1	0	0	\N	\N	0	\N	1	Player 1	\N	0	Surname1	1
2	0	0	\N	\N	0	\N	2	Player 2	\N	0	Surname2	2
3	0	0	\N	\N	0	\N	3	Player 3	\N	0	Surname3	3
4	0	0	\N	\N	0	\N	4	Player 4	\N	0	Surname4	4
5	0	0	\N	\N	0	\N	5	Player 5	\N	0	Surname5	5
6	0	0	\N	\N	0	\N	6	Player 6	\N	0	Surname6	6
7	0	0	\N	\N	0	\N	7	Player 7	\N	0	Surname7	7
8	0	0	\N	\N	0	\N	8	Player 8	\N	0	Surname8	8
\.


--
-- Data for Name: football_player_scored; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.football_player_scored (football_player_id, assists_scored, goals_scored, saves, time_scored, football_match_id) FROM stdin;
\.


--
-- Data for Name: football_team; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.football_team (id, goal_difference, goals_against, goals_for, logo, team_draws, team_league_points, team_loses, team_matches_played, team_name, team_wins) FROM stdin;
2	0	0	0	\N	0	0	0	0	Team 2	0
3	0	0	0	\N	0	0	0	0	Team 3	0
4	0	0	0	\N	0	0	0	0	Team 4	0
5	0	0	0	\N	0	0	0	0	Team 5	0
6	0	0	0	\N	0	0	0	0	Team 6	0
7	0	0	0	\N	0	0	0	0	Team 7	0
8	0	0	0	\N	0	0	0	0	Team 8	0
1	0	0	0	\N	0	3	0	0	Team 1	0
\.


--
-- Data for Name: football_team_fixtures; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.football_team_fixtures (football_team_id, football_match_id) FROM stdin;
5	4
5	5
1	1
1	5
6	6
2	1
2	2
2	6
7	7
3	2
3	3
3	7
8	8
4	3
4	4
4	8
\.


--
-- Data for Name: football_team_results; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.football_team_results (football_team_id, football_match_id) FROM stdin;
\.


--
-- Data for Name: playoff; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.playoff (id) FROM stdin;
\.


--
-- Data for Name: playoff_match; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.playoff_match (id, away_team_points, home_team_points, is_completed, away_team_id, home_team_id, stage_id) FROM stdin;
\.


--
-- Data for Name: playoff_stage; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.playoff_stage (id, stage_number, playoff_id) FROM stdin;
\.


--
-- Data for Name: team_last_five_matches; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.team_last_five_matches (team_id, match_result) FROM stdin;
\.


--
-- Data for Name: volleyball_match; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.volleyball_match (volleyball_match_id, away_team_points, end_time, home_team_points, is_volleyball_playoff_match, start_time, volleyball_away_id, volleyball_home_id) FROM stdin;
1	0	2024-09-04 19:05:53.98845	0	f	2024-09-04 17:05:53.98845	2	1
2	0	2024-09-04 19:05:54.010446	0	f	2024-09-04 17:05:54.010446	3	2
3	0	2024-09-04 19:05:54.03245	0	f	2024-09-04 17:05:54.03245	4	3
4	0	2024-09-04 19:05:54.047451	0	f	2024-09-04 17:05:54.047451	5	4
5	0	2024-09-04 19:05:54.063445	0	f	2024-09-04 17:05:54.063445	1	5
6	0	2024-09-04 19:05:54.078445	0	f	2024-09-04 17:05:54.078445	2	6
7	0	2024-09-04 19:05:54.091445	0	f	2024-09-04 17:05:54.091445	3	7
8	0	2024-09-04 19:05:54.103154	0	f	2024-09-04 17:05:54.103154	4	8
\.


--
-- Data for Name: volleyball_player; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.volleyball_player (volleyball_player_id, appearances, assists, birthdate, blocks, city, image, index, name, "position", scored_points, servings, surname, team_volleyball_team_id) FROM stdin;
1	0	0	\N	0	\N	\N	1	Player 1	\N	0	0	Surname1	1
2	0	0	\N	0	\N	\N	2	Player 2	\N	0	0	Surname2	2
3	0	0	\N	0	\N	\N	3	Player 3	\N	0	0	Surname3	3
4	0	0	\N	0	\N	\N	4	Player 4	\N	0	0	Surname4	4
5	0	0	\N	0	\N	\N	5	Player 5	\N	0	0	Surname5	5
6	0	0	\N	0	\N	\N	6	Player 6	\N	0	0	Surname6	6
7	0	0	\N	0	\N	\N	7	Player 7	\N	0	0	Surname7	7
8	0	0	\N	0	\N	\N	8	Player 8	\N	0	0	Surname8	8
\.


--
-- Data for Name: volleyball_player_match_stats; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.volleyball_player_match_stats (id, assists, blocks, scored_points, servings, volleyball_player_id, volleyball_match_id) FROM stdin;
\.


--
-- Data for Name: volleyball_team; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.volleyball_team (volleyball_team_id, logo, team_league_points, team_loses, team_matches_played, team_name, team_wins) FROM stdin;
1	\N	0	0	0	Team 1	0
2	\N	0	0	0	Team 2	0
3	\N	0	0	0	Team 3	0
4	\N	0	0	0	Team 4	0
5	\N	0	0	0	Team 5	0
6	\N	0	0	0	Team 6	0
7	\N	0	0	0	Team 7	0
8	\N	0	0	0	Team 8	0
\.


--
-- Data for Name: volleyball_team_fixtures; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.volleyball_team_fixtures (volleyball_team_id, volleyball_match_id) FROM stdin;
5	4
5	5
1	1
1	5
6	6
2	1
2	2
2	6
7	7
3	2
3	3
3	7
8	8
4	3
4	4
4	8
\.


--
-- Data for Name: volleyball_team_results; Type: TABLE DATA; Schema: public; Owner: liga
--

COPY public.volleyball_team_results (volleyball_team_id, volleyball_match_id) FROM stdin;
\.


--
-- Name: basketball_match_basketball_match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.basketball_match_basketball_match_id_seq', 8, true);


--
-- Name: basketball_player_basketball_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.basketball_player_basketball_player_id_seq', 8, true);


--
-- Name: basketball_player_match_stats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.basketball_player_match_stats_id_seq', 1, false);


--
-- Name: basketball_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.basketball_team_id_seq', 8, true);


--
-- Name: football_match_football_match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.football_match_football_match_id_seq', 8, true);


--
-- Name: football_player_football_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.football_player_football_player_id_seq', 8, true);


--
-- Name: football_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.football_team_id_seq', 8, true);


--
-- Name: playoff_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.playoff_id_seq', 1, false);


--
-- Name: playoff_match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.playoff_match_id_seq', 1, false);


--
-- Name: playoff_stage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.playoff_stage_id_seq', 1, false);


--
-- Name: volleyball_match_volleyball_match_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.volleyball_match_volleyball_match_id_seq', 8, true);


--
-- Name: volleyball_player_match_stats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.volleyball_player_match_stats_id_seq', 1, false);


--
-- Name: volleyball_player_volleyball_player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.volleyball_player_volleyball_player_id_seq', 8, true);


--
-- Name: volleyball_team_volleyball_team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: liga
--

SELECT pg_catalog.setval('public.volleyball_team_volleyball_team_id_seq', 8, true);


--
-- Name: basketball_match basketball_match_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_match
    ADD CONSTRAINT basketball_match_pkey PRIMARY KEY (basketball_match_id);


--
-- Name: basketball_player_match_stats basketball_player_match_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player_match_stats
    ADD CONSTRAINT basketball_player_match_stats_pkey PRIMARY KEY (id);


--
-- Name: basketball_player basketball_player_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player
    ADD CONSTRAINT basketball_player_pkey PRIMARY KEY (basketball_player_id);


--
-- Name: basketball_team basketball_team_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_team
    ADD CONSTRAINT basketball_team_pkey PRIMARY KEY (id);


--
-- Name: football_match football_match_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_match
    ADD CONSTRAINT football_match_pkey PRIMARY KEY (football_match_id);


--
-- Name: football_player football_player_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_player
    ADD CONSTRAINT football_player_pkey PRIMARY KEY (football_player_id);


--
-- Name: football_player_scored football_player_scored_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_player_scored
    ADD CONSTRAINT football_player_scored_pkey PRIMARY KEY (football_player_id);


--
-- Name: football_team football_team_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_team
    ADD CONSTRAINT football_team_pkey PRIMARY KEY (id);


--
-- Name: playoff_match playoff_match_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_match
    ADD CONSTRAINT playoff_match_pkey PRIMARY KEY (id);


--
-- Name: playoff playoff_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff
    ADD CONSTRAINT playoff_pkey PRIMARY KEY (id);


--
-- Name: playoff_stage playoff_stage_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_stage
    ADD CONSTRAINT playoff_stage_pkey PRIMARY KEY (id);


--
-- Name: volleyball_match volleyball_match_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_match
    ADD CONSTRAINT volleyball_match_pkey PRIMARY KEY (volleyball_match_id);


--
-- Name: volleyball_player_match_stats volleyball_player_match_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player_match_stats
    ADD CONSTRAINT volleyball_player_match_stats_pkey PRIMARY KEY (id);


--
-- Name: volleyball_player volleyball_player_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player
    ADD CONSTRAINT volleyball_player_pkey PRIMARY KEY (volleyball_player_id);


--
-- Name: volleyball_team volleyball_team_pkey; Type: CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_team
    ADD CONSTRAINT volleyball_team_pkey PRIMARY KEY (volleyball_team_id);


--
-- Name: football_match fk1h1tenl6d5nq41ye10s11me5y; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_match
    ADD CONSTRAINT fk1h1tenl6d5nq41ye10s11me5y FOREIGN KEY (football_home_id) REFERENCES public.football_team(id);


--
-- Name: volleyball_player_match_stats fk2492rncppl1n0iqybeo23sfiy; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player_match_stats
    ADD CONSTRAINT fk2492rncppl1n0iqybeo23sfiy FOREIGN KEY (volleyball_player_id) REFERENCES public.volleyball_player(volleyball_player_id);


--
-- Name: basketball_team_results fk4ikh9x430ny936nwgjgds306e; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_team_results
    ADD CONSTRAINT fk4ikh9x430ny936nwgjgds306e FOREIGN KEY (basketball_match_id) REFERENCES public.basketball_match(basketball_match_id);


--
-- Name: football_player_scored fk55c6hs50qop57jpn1feugda9j; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_player_scored
    ADD CONSTRAINT fk55c6hs50qop57jpn1feugda9j FOREIGN KEY (football_player_id) REFERENCES public.football_player(football_player_id);


--
-- Name: volleyball_player fk7b689ya1oj2frn3mh53fjjpwv; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player
    ADD CONSTRAINT fk7b689ya1oj2frn3mh53fjjpwv FOREIGN KEY (team_volleyball_team_id) REFERENCES public.volleyball_team(volleyball_team_id);


--
-- Name: basketball_player_match_stats fk7lym9o82jdnkqgvl905blg3bx; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player_match_stats
    ADD CONSTRAINT fk7lym9o82jdnkqgvl905blg3bx FOREIGN KEY (basketball_player_id) REFERENCES public.basketball_player(basketball_player_id);


--
-- Name: team_last_five_matches fk8c792rdtwa3wrjhuafn2uu7fu; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.team_last_five_matches
    ADD CONSTRAINT fk8c792rdtwa3wrjhuafn2uu7fu FOREIGN KEY (team_id) REFERENCES public.football_team(id);


--
-- Name: football_match fk9iy7ocluhu4on34svd38he9x; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_match
    ADD CONSTRAINT fk9iy7ocluhu4on34svd38he9x FOREIGN KEY (football_away_id) REFERENCES public.football_team(id);


--
-- Name: football_team_fixtures fka3d68y7gj71i70j0qh0bpmgts; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_team_fixtures
    ADD CONSTRAINT fka3d68y7gj71i70j0qh0bpmgts FOREIGN KEY (football_team_id) REFERENCES public.football_team(id);


--
-- Name: volleyball_team_results fkb0hr8sfh725gym9lf66v8gdyx; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_team_results
    ADD CONSTRAINT fkb0hr8sfh725gym9lf66v8gdyx FOREIGN KEY (volleyball_match_id) REFERENCES public.volleyball_match(volleyball_match_id);


--
-- Name: basketball_match fkc10mf09kua7kv72tassialkqa; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_match
    ADD CONSTRAINT fkc10mf09kua7kv72tassialkqa FOREIGN KEY (basketball_home_id) REFERENCES public.basketball_team(id);


--
-- Name: playoff_match fkcfm73jjq4a055liecpbrcxo5o; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_match
    ADD CONSTRAINT fkcfm73jjq4a055liecpbrcxo5o FOREIGN KEY (away_team_id) REFERENCES public.football_team(id);


--
-- Name: volleyball_team_results fkdw1p5dkis08cju8wkbrswg20x; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_team_results
    ADD CONSTRAINT fkdw1p5dkis08cju8wkbrswg20x FOREIGN KEY (volleyball_team_id) REFERENCES public.volleyball_team(volleyball_team_id);


--
-- Name: basketball_player fkf2ha84th9twutfoudq86vq6ro; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player
    ADD CONSTRAINT fkf2ha84th9twutfoudq86vq6ro FOREIGN KEY (team_id) REFERENCES public.basketball_team(id);


--
-- Name: volleyball_player_match_stats fkfcts66i83tn8lb6rrjophhi61; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_player_match_stats
    ADD CONSTRAINT fkfcts66i83tn8lb6rrjophhi61 FOREIGN KEY (volleyball_match_id) REFERENCES public.volleyball_match(volleyball_match_id);


--
-- Name: volleyball_team_fixtures fkg2q5xnf8e5mm5bv1wicva5qn3; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_team_fixtures
    ADD CONSTRAINT fkg2q5xnf8e5mm5bv1wicva5qn3 FOREIGN KEY (volleyball_match_id) REFERENCES public.volleyball_match(volleyball_match_id);


--
-- Name: volleyball_team_fixtures fkh1e238tfidsq1jpf85wixiun8; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_team_fixtures
    ADD CONSTRAINT fkh1e238tfidsq1jpf85wixiun8 FOREIGN KEY (volleyball_team_id) REFERENCES public.volleyball_team(volleyball_team_id);


--
-- Name: football_player_scored fkh2lplq34ujwllf4jhs1vfatic; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_player_scored
    ADD CONSTRAINT fkh2lplq34ujwllf4jhs1vfatic FOREIGN KEY (football_match_id) REFERENCES public.football_match(football_match_id);


--
-- Name: playoff_stage fkh6w1fr2nw17ud0xectrma0ptq; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_stage
    ADD CONSTRAINT fkh6w1fr2nw17ud0xectrma0ptq FOREIGN KEY (playoff_id) REFERENCES public.playoff(id);


--
-- Name: volleyball_match fki5tpo5v7etpyewfdhdig7425f; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_match
    ADD CONSTRAINT fki5tpo5v7etpyewfdhdig7425f FOREIGN KEY (volleyball_away_id) REFERENCES public.volleyball_team(volleyball_team_id);


--
-- Name: basketball_player_match_stats fkj732y83uxqs1vspt9bee0hkhi; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_player_match_stats
    ADD CONSTRAINT fkj732y83uxqs1vspt9bee0hkhi FOREIGN KEY (basketball_match_id) REFERENCES public.basketball_match(basketball_match_id);


--
-- Name: basketball_match fkjvktch29sxukrmelvyn4bd3hy; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_match
    ADD CONSTRAINT fkjvktch29sxukrmelvyn4bd3hy FOREIGN KEY (basketball_away_id) REFERENCES public.basketball_team(id);


--
-- Name: basketball_team_results fkmhvm5fj3j8dm68cq0d7fr5j5k; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_team_results
    ADD CONSTRAINT fkmhvm5fj3j8dm68cq0d7fr5j5k FOREIGN KEY (basketball_team_id) REFERENCES public.basketball_team(id);


--
-- Name: football_team_fixtures fkml4r57vuy06w0xw1ablo6ynbe; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_team_fixtures
    ADD CONSTRAINT fkml4r57vuy06w0xw1ablo6ynbe FOREIGN KEY (football_match_id) REFERENCES public.football_match(football_match_id);


--
-- Name: playoff_match fkmn6b2pr4vd61r4d17yci2gabn; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_match
    ADD CONSTRAINT fkmn6b2pr4vd61r4d17yci2gabn FOREIGN KEY (stage_id) REFERENCES public.playoff_stage(id);


--
-- Name: football_team_results fknbc0lauypl4ecnxthobateb45; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_team_results
    ADD CONSTRAINT fknbc0lauypl4ecnxthobateb45 FOREIGN KEY (football_match_id) REFERENCES public.football_match(football_match_id);


--
-- Name: basketball_team_fixtures fknbqo1ls599xc6pguc4ghbiwav; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_team_fixtures
    ADD CONSTRAINT fknbqo1ls599xc6pguc4ghbiwav FOREIGN KEY (basketball_match_id) REFERENCES public.basketball_match(basketball_match_id);


--
-- Name: volleyball_match fkp3fis7w3j489qmo6imk8o0mbd; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.volleyball_match
    ADD CONSTRAINT fkp3fis7w3j489qmo6imk8o0mbd FOREIGN KEY (volleyball_home_id) REFERENCES public.volleyball_team(volleyball_team_id);


--
-- Name: football_player fkphdoibwy6e5hygehueqt95kkn; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_player
    ADD CONSTRAINT fkphdoibwy6e5hygehueqt95kkn FOREIGN KEY (team_id) REFERENCES public.football_team(id);


--
-- Name: basketball_team_fixtures fkpm8qx9fxkgsyhp7cfxjlxe3o4; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.basketball_team_fixtures
    ADD CONSTRAINT fkpm8qx9fxkgsyhp7cfxjlxe3o4 FOREIGN KEY (basketball_team_id) REFERENCES public.basketball_team(id);


--
-- Name: football_team_results fkpwsvhvpo3ulpjx16k303gcks4; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.football_team_results
    ADD CONSTRAINT fkpwsvhvpo3ulpjx16k303gcks4 FOREIGN KEY (football_team_id) REFERENCES public.football_team(id);


--
-- Name: playoff_match fkqfphemyfyhvurawhr2ljuf38c; Type: FK CONSTRAINT; Schema: public; Owner: liga
--

ALTER TABLE ONLY public.playoff_match
    ADD CONSTRAINT fkqfphemyfyhvurawhr2ljuf38c FOREIGN KEY (home_team_id) REFERENCES public.football_team(id);


--
-- PostgreSQL database dump complete
--

