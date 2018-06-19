CREATE TABLE if not exists public.allievo
(
  id bigint NOT NULL,
  codice_fiscale character varying(16) NOT NULL,
  cognome character varying(255) NOT NULL,
  data_nascita date,
  email character varying(255),
  luogo_nascita character varying(255),
  nome character varying(255) NOT NULL,
  telefono character varying(255),
  CONSTRAINT allievo_pkey PRIMARY KEY (id),
  CONSTRAINT uk_q1kh3vng6w9tclf951foqx11o UNIQUE (codice_fiscale)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.allievo
  OWNER TO formazione;

CREATE TABLE public.centro
(
  id bigint NOT NULL,
  capienza integer NOT NULL,
  email character varying(255) NOT NULL,
  indirizzo character varying(255) NOT NULL,
  nome character varying(255) NOT NULL,
  telefono character varying(255) NOT NULL,
  CONSTRAINT centro_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.centro
  OWNER TO formazione;


CREATE TABLE if not exists public.attivita
(
  id bigint NOT NULL,
  nome character varying(255) NOT NULL,
  orario_fine timestamp without time zone NOT NULL,
  orario_inizio timestamp without time zone NOT NULL,
  centro_id bigint,
  CONSTRAINT attivita_pkey PRIMARY KEY (id),
  CONSTRAINT fka269dh4w7u1nmuxthi07m4u20 FOREIGN KEY (centro_id)
      REFERENCES public.centro (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.attivita
  OWNER TO formazione;

CREATE TABLE if not exists public.allievo_attivita_prenotate
(
  allievi_prenotati_id bigint NOT NULL,
  attivita_prenotate_id bigint NOT NULL,
  CONSTRAINT allievo_attivita_prenotate_pkey PRIMARY KEY (allievi_prenotati_id, attivita_prenotate_id),
  CONSTRAINT fkg9my5tprw9uao0ry64kxicvfv FOREIGN KEY (attivita_prenotate_id)
      REFERENCES public.attivita (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkjyfbg5sg2m422msf44m1qg562 FOREIGN KEY (allievi_prenotati_id)
      REFERENCES public.allievo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.allievo_attivita_prenotate
  OWNER TO formazione;

CREATE SEQUENCE if not exists public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.hibernate_sequence
  OWNER TO formazione;
