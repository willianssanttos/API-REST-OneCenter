--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

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
-- Name: atualizar_endereco_por_pessoa(character varying, character varying, character varying, character varying, character varying, character varying, integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.atualizar_endereco_por_pessoa(IN p_rua character varying, IN p_numero character varying, IN p_bairro character varying, IN p_cidade character varying, IN p_cep character varying, IN p_uf character varying, IN p_id_pessoa integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE enderecos
    SET 
        nm_rua = p_rua,
        ds_numero = p_numero,
        ds_bairro = p_bairro,
        ds_cidade = p_cidade,
        ds_cep = p_cep,
        ds_uf = p_uf
    WHERE fk_nr_id_pessoa = p_id_pessoa;
END;
$$;


ALTER PROCEDURE public.atualizar_endereco_por_pessoa(IN p_rua character varying, IN p_numero character varying, IN p_bairro character varying, IN p_cidade character varying, IN p_cep character varying, IN p_uf character varying, IN p_id_pessoa integer) OWNER TO postgres;

--
-- Name: atualizar_pessoa(integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.atualizar_pessoa(p_nr_id_pessoa integer, p_nm_nome_razaosocial character varying, p_ds_email character varying, p_ds_senha character varying, p_ds_telefone character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE pessoas
    SET nm_nome_razaosocial = p_nm_nome_razaosocial,
        ds_email = p_ds_email,
        ds_senha = p_ds_senha,
        ds_telefone = p_ds_telefone
    WHERE nr_id_pessoa = p_nr_id_pessoa;

    RETURN 1; -- sucesso
END;
$$;


ALTER FUNCTION public.atualizar_pessoa(p_nr_id_pessoa integer, p_nm_nome_razaosocial character varying, p_ds_email character varying, p_ds_senha character varying, p_ds_telefone character varying) OWNER TO postgres;

--
-- Name: atualizar_pessoa_fisica(character varying, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.atualizar_pessoa_fisica(p_cpf character varying, p_data_nascimento date, p_id_pessoa integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE pessoas_fisicas
    SET ds_cpf = p_cpf,
        ds_data_nascimento = p_data_nascimento
    WHERE fk_nr_id_pessoa = p_id_pessoa;

    RETURN p_id_pessoa;
END;
$$;


ALTER FUNCTION public.atualizar_pessoa_fisica(p_cpf character varying, p_data_nascimento date, p_id_pessoa integer) OWNER TO postgres;

--
-- Name: atualizar_produto(integer, character varying, double precision, text, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.atualizar_produto(id_produto integer, nome character varying, preco double precision, descricao text, imagem character varying, id_categoria integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE produtos 
    SET nm_nome = nome, ds_preco = preco, ds_descricao = descricao, ds_imagem_produto = imagem, fk_nr_id_categoria = id_categoria
    WHERE nr_id_produto = id_produto;
END;
$$;


ALTER FUNCTION public.atualizar_produto(id_produto integer, nome character varying, preco double precision, descricao text, imagem character varying, id_categoria integer) OWNER TO postgres;

--
-- Name: atualizar_status_cupom(character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.atualizar_status_cupom(IN p_nome_cupom character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE cupons
    SET ds_usado = TRUE
    WHERE nm_nome_cupom = p_nome_cupom;
END;
$$;


ALTER PROCEDURE public.atualizar_status_cupom(IN p_nome_cupom character varying) OWNER TO postgres;

--
-- Name: atualizar_status_pagamento_pedido(integer, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.atualizar_status_pagamento_pedido(IN p_id_pedido integer, IN p_status character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE pedidos
    SET status_pedido = p_status
    WHERE id_pedido = p_id_pedido;
END;
$$;


ALTER PROCEDURE public.atualizar_status_pagamento_pedido(IN p_id_pedido integer, IN p_status character varying) OWNER TO postgres;

--
-- Name: buscar_cupom_por_nome(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_cupom_por_nome(p_nome_cupom character varying) RETURNS TABLE(nr_id_cupom integer, nm_nome_cupom character varying, ds_valor_desconto numeric, ds_data_validade timestamp without time zone, ds_usado boolean)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 
        c.nr_id_cupom,
        c.nm_nome_cupom,
        c.ds_valor_desconto,
        c.ds_data_validade,
        c.ds_usado
    FROM cupons c
    WHERE TRIM(LOWER(c.nm_nome_cupom)) = TRIM(LOWER(p_nome_cupom));
END;
$$;


ALTER FUNCTION public.buscar_cupom_por_nome(p_nome_cupom character varying) OWNER TO postgres;

--
-- Name: buscar_endereco_por_pessoa(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_endereco_por_pessoa(p_id_pessoa integer) RETURNS TABLE(nr_id_endereco integer, nm_rua character varying, ds_numero character varying, ds_bairro character varying, ds_cidade character varying, ds_uf character varying, ds_cep character varying, fk_nr_id_pessoa integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 
        e.nr_id_endereco,
        e.nm_rua,
        e.ds_numero,
        e.ds_bairro,
        e.ds_cidade,
        e.ds_uf,
        e.ds_cep,
        e.fk_nr_id_pessoa
    FROM enderecos e
    WHERE e.fk_nr_id_pessoa = p_id_pessoa;
END;
$$;


ALTER FUNCTION public.buscar_endereco_por_pessoa(p_id_pessoa integer) OWNER TO postgres;

--
-- Name: buscar_id_categoria_por_nome(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_id_categoria_por_nome(nome_categoria character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    id_categoria INTEGER;
BEGIN
    SELECT c.nr_id_categoria INTO id_categoria
    FROM categorias
    WHERE c.nm_nome = nome_categoria;

    RETURN id_categoria;
END;
$$;


ALTER FUNCTION public.buscar_id_categoria_por_nome(nome_categoria character varying) OWNER TO postgres;

--
-- Name: buscar_id_pessoa_por_cnpj(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_id_pessoa_por_cnpj(p_cnpj character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_id_pessoa INTEGER;
BEGIN
    SELECT p.nr_id_pessoa
    INTO v_id_pessoa
    FROM pessoas p
    INNER JOIN pessoas_juridicas pj ON p.nr_id_pessoa = pj.fk_nr_id_pessoa
    WHERE pj.ds_cnpj = p_cnpj;

    RETURN v_id_pessoa;
END;
$$;


ALTER FUNCTION public.buscar_id_pessoa_por_cnpj(p_cnpj character varying) OWNER TO postgres;

--
-- Name: buscar_id_pessoa_por_cpf(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_id_pessoa_por_cpf(p_cpf character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_id_pessoa INTEGER;
BEGIN
    SELECT p.nr_id_pessoa
    INTO v_id_pessoa
    FROM pessoas p
    INNER JOIN pessoas_fisicas pf ON p.nr_id_pessoa = pf.fk_nr_id_pessoa
    WHERE pf.ds_cpf = p_cpf;

    RETURN v_id_pessoa;
END;
$$;


ALTER FUNCTION public.buscar_id_pessoa_por_cpf(p_cpf character varying) OWNER TO postgres;

--
-- Name: buscar_imagens_produto(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_imagens_produto(id_produto integer) RETURNS TABLE(ds_caminho character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY 
    SELECT ip.ds_caminho 
    FROM imagens_produtos ip 
    WHERE ip.fk_nr_id_produto = id_produto;
END;
$$;


ALTER FUNCTION public.buscar_imagens_produto(id_produto integer) OWNER TO postgres;

--
-- Name: buscar_item_pedido_por_id(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_item_pedido_por_id(p_nr_id_itens_pedido integer) RETURNS TABLE(nr_id_itens_pedido integer, fk_nr_id_pedido integer, fk_nr_id_produto integer, ds_quantidade integer, ds_preco_unitario double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 
        i.nr_id_itens_pedido,
        i.fk_nr_id_pedido,
        i.fk_nr_id_produto,
        i.ds_quantidade,
        i.ds_preco_unitario
    FROM itens_pedido i
    WHERE i.nr_id_itens_pedido = p_nr_id_itens_pedido;
END;
$$;


ALTER FUNCTION public.buscar_item_pedido_por_id(p_nr_id_itens_pedido integer) OWNER TO postgres;

--
-- Name: buscar_pessoa_por_cnpj(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_pessoa_por_cnpj(cnpj_param character varying) RETURNS TABLE(nr_id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying, ds_cnpj character varying, nm_nome_fantasia character varying, nm_rua character varying, ds_numero character varying, ds_bairro character varying, ds_cidade character varying, ds_cep character varying, ds_uf character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.nr_id_pessoa,
        p.nm_roles,
        p.nm_nome_razaosocial,
        p.ds_email,
		p.ds_senha,
        p.ds_telefone,
        e.nm_rua,
        e.ds_numero,
        e.ds_bairro,
        e.ds_cidade,
        e.ds_cep,
        e.ds_uf,
		pj.ds_cnpj,
        pj.nm_nome_fantasia
    FROM pessoas p
    LEFT JOIN pessoas_juridicas pj ON p.nr_id_pessoa = pj.fk_nr_id_pessoa
    LEFT JOIN enderecos e ON p.nr_id_pessoa = e.fk_nr_id_pessoa
    WHERE pj.ds_cnpj = cnpj_param;
END;
$$;


ALTER FUNCTION public.buscar_pessoa_por_cnpj(cnpj_param character varying) OWNER TO postgres;

--
-- Name: buscar_pessoa_por_cpf(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_pessoa_por_cpf(p_cpf character varying) RETURNS TABLE(nr_id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying, nm_rua character varying, ds_numero character varying, ds_bairro character varying, ds_cidade character varying, ds_cep character varying, ds_uf character varying, ds_cpf character varying, ds_data_nascimento date)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.nr_id_pessoa,
		p.nm_roles,
        p.nm_nome_razaosocial,
        p.ds_email,
		p.ds_senha,
        p.ds_telefone,
        e.nm_rua,
        e.ds_numero,
        e.ds_bairro,
        e.ds_cidade,
        e.ds_cep,
        e.ds_uf,
		pf.ds_cpf,
        pf.ds_data_nascimento
    FROM pessoas p
    LEFT JOIN pessoas_fisicas pf ON p.nr_id_pessoa = pf.fk_nr_id_pessoa
    LEFT JOIN enderecos e ON p.nr_id_pessoa = e.fk_nr_id_pessoa
    WHERE pf.ds_cpf = p_cpf;
END;
$$;


ALTER FUNCTION public.buscar_pessoa_por_cpf(p_cpf character varying) OWNER TO postgres;

--
-- Name: buscar_pessoa_por_id(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_pessoa_por_id(p_id integer) RETURNS TABLE(nr_id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.nr_id_pessoa,
        p.nm_roles,
        p.nm_nome_razaosocial,
        p.ds_email,
        p.ds_senha,
        p.ds_telefone
    FROM pessoas p
    WHERE p.nr_id_pessoa = p_id;
END;
$$;


ALTER FUNCTION public.buscar_pessoa_por_id(p_id integer) OWNER TO postgres;

--
-- Name: buscar_produto_id(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.buscar_produto_id(id_produto integer) RETURNS TABLE(nr_id_produto integer, nm_nome character varying, ds_preco double precision, ds_descricao text, ds_imagem_produto character varying, categoria_nome character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.nr_id_produto,
        p.nm_nome,
        p.ds_preco,
        p.ds_descricao,
        p.ds_imagem_produto,
        c.nm_categoria
    FROM produtos p
    INNER JOIN categorias c ON p.fk_nr_id_categoria = c.nr_id_categoria
    WHERE p.nr_id_produto = buscar_produto_id.id_produto;
END;
$$;


ALTER FUNCTION public.buscar_produto_id(id_produto integer) OWNER TO postgres;

--
-- Name: contar_uso_cupom(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.contar_uso_cupom(p_id_cupom integer, p_id_pessoa integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_total INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM cupons_utilizados
    WHERE fk_nr_id_cupom = p_id_cupom
      AND fk_nr_id_pessoa = p_id_pessoa;

    RETURN v_total;
END;
$$;


ALTER FUNCTION public.contar_uso_cupom(p_id_cupom integer, p_id_pessoa integer) OWNER TO postgres;

--
-- Name: criar_pedido(timestamp without time zone, character varying, numeric, numeric, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.criar_pedido(p_dt_pedido timestamp without time zone, p_ds_status character varying, p_ds_desconto_aplicado numeric, p_ds_valor_total numeric, p_fk_nr_id_pessoa integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_nr_id_pedido INT;
BEGIN
    INSERT INTO pedidos (
        dt_pedido,
        ds_status,
        ds_desconto_aplicado,
		ds_valor_total,
        fk_nr_id_pessoa
    )
    VALUES (
        p_dt_pedido,
    	p_ds_status,
    	p_ds_desconto_aplicado,
		p_ds_valor_total,
        p_fk_nr_id_pessoa
    )
    RETURNING nr_id_pedido INTO v_nr_id_pedido;

    RETURN v_nr_id_pedido;
END;
$$;


ALTER FUNCTION public.criar_pedido(p_dt_pedido timestamp without time zone, p_ds_status character varying, p_ds_desconto_aplicado numeric, p_ds_valor_total numeric, p_fk_nr_id_pessoa integer) OWNER TO postgres;

--
-- Name: criar_pessoa(character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.criar_pessoa(p_nm_roles character varying, p_nm_nome_razaosocial character varying, p_ds_email character varying, p_ds_senha character varying, p_ds_telefone character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_id_pessoa INTEGER;
BEGIN
    INSERT INTO pessoas (nm_roles, nm_nome_razaosocial, ds_email, ds_senha, ds_telefone)
    VALUES (p_nm_roles, p_nm_nome_razaosocial, p_ds_email, p_ds_senha, p_ds_telefone)
    RETURNING nr_id_pessoa INTO v_id_pessoa;

    RETURN v_id_pessoa;
END;
$$;


ALTER FUNCTION public.criar_pessoa(p_nm_roles character varying, p_nm_nome_razaosocial character varying, p_ds_email character varying, p_ds_senha character varying, p_ds_telefone character varying) OWNER TO postgres;

--
-- Name: deletar_produto(integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.deletar_produto(IN id_produto integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    DELETE FROM produtos WHERE nr_id_produto = id_produto;
END;
$$;


ALTER PROCEDURE public.deletar_produto(IN id_produto integer) OWNER TO postgres;

--
-- Name: inserir_categoria(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_categoria(nome_categoria character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    novo_id INTEGER;
BEGIN
    INSERT INTO categorias (nm_nome)
    VALUES (nome_categoria)
    RETURNING nr_id_categoria INTO novo_id;

    RETURN novo_id;
END;
$$;


ALTER FUNCTION public.inserir_categoria(nome_categoria character varying) OWNER TO postgres;

--
-- Name: inserir_cupom(character varying, numeric, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_cupom(p_nm_nome_cupom character varying, p_valor_desconto numeric, p_data_validade timestamp without time zone) RETURNS TABLE(nr_id_cupom integer, nm_nome_cupom character varying, ds_valor_desconto numeric, ds_data_validade timestamp without time zone, ds_usado boolean)
    LANGUAGE plpgsql
    AS $$
BEGIN

    INSERT INTO cupons (nm_nome_cupom, ds_valor_desconto, ds_data_validade)
    VALUES (p_nm_nome_cupom, p_valor_desconto, p_data_validade)
    RETURNING
        cupons.nr_id_cupom,
        cupons.nm_nome_cupom,
        cupons.ds_valor_desconto,
        cupons.ds_data_validade,
        cupons.ds_usado
    INTO
        nr_id_cupom,
        nm_nome_cupom,
        ds_valor_desconto,
        ds_data_validade,
        ds_usado;
    
    RETURN NEXT;
END;
$$;


ALTER FUNCTION public.inserir_cupom(p_nm_nome_cupom character varying, p_valor_desconto numeric, p_data_validade timestamp without time zone) OWNER TO postgres;

--
-- Name: inserir_endereco(character varying, character varying, character varying, character varying, character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_endereco(p_rua character varying, p_numero character varying, p_bairro character varying, p_cidade character varying, p_uf character varying, p_cep character varying, p_id_pessoa integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    novo_id INTEGER;
BEGIN
    INSERT INTO enderecos (nm_rua, ds_numero, ds_bairro, ds_cidade, ds_uf, ds_cep, fk_nr_id_pessoa)
    VALUES (p_rua, p_numero, p_bairro, p_cidade, p_uf, p_cep, p_id_pessoa)
    RETURNING nr_id_endereco INTO novo_id;

    RETURN novo_id;
END;
$$;


ALTER FUNCTION public.inserir_endereco(p_rua character varying, p_numero character varying, p_bairro character varying, p_cidade character varying, p_uf character varying, p_cep character varying, p_id_pessoa integer) OWNER TO postgres;

--
-- Name: inserir_imagem_produto(integer, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_imagem_produto(id_produto integer, caminho character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    novo_id INTEGER;
BEGIN
    INSERT INTO imagens_produtos (fk_nr_id_produto, ds_caminho)
    VALUES (id_produto, caminho)
    RETURNING nr_id_imagem INTO novo_id;

    RETURN novo_id;
END;
$$;


ALTER FUNCTION public.inserir_imagem_produto(id_produto integer, caminho character varying) OWNER TO postgres;

--
-- Name: inserir_item_pedido(integer, integer, integer, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_item_pedido(p_fk_nr_id_pedido integer, p_fk_nr_id_produto integer, p_ds_quantidade integer, p_ds_preco_quantidade double precision) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_id_item_pedido INTEGER;
BEGIN
    INSERT INTO itens_pedido (
        fk_nr_id_pedido,
        fk_nr_id_produto,
        ds_quantidade,
        ds_preco_unitario
    ) VALUES (
        p_fk_nr_id_pedido,
        p_fk_nr_id_produto,
        p_ds_quantidade,
        p_ds_preco_quantidade
    )
    RETURNING nr_id_itens_pedido INTO v_id_item_pedido;

    RETURN v_id_item_pedido;
END;
$$;


ALTER FUNCTION public.inserir_item_pedido(p_fk_nr_id_pedido integer, p_fk_nr_id_produto integer, p_ds_quantidade integer, p_ds_preco_quantidade double precision) OWNER TO postgres;

--
-- Name: inserir_pessoa_fisica(character varying, date, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_pessoa_fisica(p_cpf character varying, p_data_nascimento date, p_fk_id_pessoa integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_id_pessoa_fisica INTEGER;
BEGIN
    INSERT INTO pessoas_fisicas (ds_cpf, ds_data_nascimento, fk_nr_id_pessoa)
    VALUES (p_cpf, p_data_nascimento, p_fk_id_pessoa)
    RETURNING nr_id_pessoa_fisica INTO v_id_pessoa_fisica;

    RETURN v_id_pessoa_fisica;
END;
$$;


ALTER FUNCTION public.inserir_pessoa_fisica(p_cpf character varying, p_data_nascimento date, p_fk_id_pessoa integer) OWNER TO postgres;

--
-- Name: inserir_pessoa_juridica(character varying, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_pessoa_juridica(p_nome_fantasia character varying, p_cnpj character varying, p_id_pessoa integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_id_pessoa_juridica INTEGER;
BEGIN
    INSERT INTO pessoas_juridicas (nm_nome_fantasia, ds_cnpj, fk_nr_id_pessoa)
    VALUES (p_nome_fantasia, p_cnpj, p_id_pessoa)
    RETURNING nr_id_pessoa_juridica INTO v_id_pessoa_juridica;

    RETURN v_id_pessoa_juridica;
END;
$$;


ALTER FUNCTION public.inserir_pessoa_juridica(p_nome_fantasia character varying, p_cnpj character varying, p_id_pessoa integer) OWNER TO postgres;

--
-- Name: inserir_produto(character varying, double precision, text, character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.inserir_produto(nome character varying, preco double precision, descricao text, imagem character varying, id_categoria integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    novo_id INTEGER;
BEGIN
    INSERT INTO produtos (nm_nome, ds_preco, ds_descricao, ds_imagem_produto, fk_nr_id_categoria)
    VALUES (nome, preco, descricao, imagem, id_categoria)
    RETURNING nr_id_produto INTO novo_id;
    
    RETURN novo_id;
END;
$$;


ALTER FUNCTION public.inserir_produto(nome character varying, preco double precision, descricao text, imagem character varying, id_categoria integer) OWNER TO postgres;

--
-- Name: listar_categorias(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.listar_categorias() RETURNS TABLE(nr_id_categoria integer, nm_nome character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY 
    SELECT c.nr_id_categoria, c.nm_nome FROM categorias c;
END;
$$;


ALTER FUNCTION public.listar_categorias() OWNER TO postgres;

--
-- Name: obter_pedido_por_id(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.obter_pedido_por_id(p_id integer) RETURNS TABLE(id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying, nr_id_itens_pedido integer, fk_nr_id_pedido integer, fk_nr_id_produto integer, ds_quantidade integer, ds_preco_unitario double precision, nr_id_pedido integer, dt_pedido timestamp without time zone, ds_status character varying, ds_desconto_aplicado numeric, ds_valor_total double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 
        pes.nr_id_pessoa AS id_pessoa,
        pes.nm_roles,
        pes.nm_nome_razaosocial,
        pes.ds_email,
        pes.ds_senha,
        pes.ds_telefone,

        ip.nr_id_itens_pedido,
        ip.fk_nr_id_pedido,
        ip.fk_nr_id_produto,
        ip.ds_quantidade,
        ip.ds_preco_unitario,

        p.nr_id_pedido,
        p.dt_pedido,
        p.ds_status,
        p.ds_desconto_aplicado,
        p.ds_valor_total

    FROM pedidos p
    JOIN pessoas pes ON p.fk_nr_id_pessoa = pes.nr_id_pessoa
    JOIN itens_pedido ip ON p.nr_id_pedido = ip.fk_nr_id_pedido
    WHERE p.nr_id_pedido = p_id;
END;
$$;


ALTER FUNCTION public.obter_pedido_por_id(p_id integer) OWNER TO postgres;

--
-- Name: obter_pedidos_por_id_pessoa(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.obter_pedidos_por_id_pessoa(p_id_pessoa integer) RETURNS TABLE(id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying, id_itens_pedido integer, item_pedido integer, item_produto integer, item_quantidade integer, item_preco_unitario double precision, nr_id_pedido integer, dt_pedido timestamp without time zone, ds_status character varying, ds_desconto_aplicado numeric, ds_valor_total double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 

		pes.nr_id_pessoa AS id_pessoa,
        pes.nm_roles,
        pes.nm_nome_razaosocial,
        pes.ds_email,
        pes.ds_senha,
        pes.ds_telefone,

 		ip.nr_id_itens_pedido AS id_itens_pedido,
        ip.fk_nr_id_pedido AS item_pedido,
        ip.fk_nr_id_produto AS item_produto,
        ip.ds_quantidade AS item_quantidade,
        ip.ds_preco_unitario AS item_preco_unitario,

        p.nr_id_pedido,
        p.dt_pedido,
        p.ds_status,
        p.ds_desconto_aplicado,
		p.ds_valor_total

    FROM pedidos p
    JOIN pessoas pes ON p.fk_nr_id_pessoa = pes.nr_id_pessoa
    JOIN itens_pedido ip ON ip.fk_nr_id_pedido = p.nr_id_pedido
    WHERE pes.nr_id_pessoa = p_id_pessoa;
END;
$$;


ALTER FUNCTION public.obter_pedidos_por_id_pessoa(p_id_pessoa integer) OWNER TO postgres;

--
-- Name: obter_todos_pedidos_completo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.obter_todos_pedidos_completo() RETURNS TABLE(nr_id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying, nr_id_itens_pedido integer, fk_nr_id_pedido integer, fk_nr_id_produto integer, ds_quantidade integer, ds_preco_unitario double precision, nr_id_pedido integer, dt_pedido timestamp without time zone, ds_status character varying, ds_desconto_aplicado numeric, ds_valor_total double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 
        pes.nr_id_pessoa,
        pes.nm_roles,
        pes.nm_nome_razaosocial,
        pes.ds_email,
        pes.ds_senha,
        pes.ds_telefone,

        ip.nr_id_itens_pedido,
        ip.fk_nr_id_pedido,
        ip.fk_nr_id_produto,
        ip.ds_quantidade,
        ip.ds_preco_unitario,

        p.nr_id_pedido,
        p.dt_pedido,
        p.ds_status,
        p.ds_desconto_aplicado,
        p.ds_valor_total

    FROM pedidos p
    JOIN pessoas pes ON p.fk_nr_id_pessoa = pes.nr_id_pessoa
    JOIN itens_pedido ip ON ip.fk_nr_id_pedido = p.nr_id_pedido;
END;
$$;


ALTER FUNCTION public.obter_todos_pedidos_completo() OWNER TO postgres;

--
-- Name: obter_todos_produtos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.obter_todos_produtos() RETURNS TABLE(nr_id_produto integer, nm_nome character varying, ds_preco double precision, ds_descricao text, ds_imagem_produto character varying, categoria_nome character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.nr_id_produto,
        p.nm_nome,
        p.ds_preco,
        p.ds_descricao,
        p.ds_imagem_produto,
        c.nm_categoria
    FROM produtos p
    INNER JOIN categorias c ON p.fk_nr_id_categoria = c.nr_id_categoria;
END;
$$;


ALTER FUNCTION public.obter_todos_produtos() OWNER TO postgres;

--
-- Name: registrar_uso_cupom(integer, integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.registrar_uso_cupom(IN p_id_cupom integer, IN p_id_pessoa integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO cupons_utilizados (fk_nr_id_cupom, fk_nr_id_pessoa)
    VALUES (p_id_cupom, p_id_pessoa);
END;
$$;


ALTER PROCEDURE public.registrar_uso_cupom(IN p_id_cupom integer, IN p_id_pessoa integer) OWNER TO postgres;

--
-- Name: validar_usuario(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validar_usuario(p_email character varying) RETURNS TABLE(nr_id_pessoa integer, nm_roles character varying, nm_nome_razaosocial character varying, ds_email character varying, ds_senha character varying, ds_telefone character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT 
        p.nr_id_pessoa,
        p.nm_roles,
        p.nm_nome_razaosocial,
        p.ds_email,
        p.ds_senha,
        p.ds_telefone
    FROM pessoas p
    WHERE p.ds_email = p_email;
END;
$$;


ALTER FUNCTION public.validar_usuario(p_email character varying) OWNER TO postgres;

--
-- Name: verificar_cnpj_existe(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.verificar_cnpj_existe(p_cnpj character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
    existe BOOLEAN;
BEGIN
    SELECT EXISTS (
        SELECT 1 FROM pessoas_juridicas WHERE ds_cnpj = p_cnpj
    ) INTO existe;

    RETURN existe;
END;
$$;


ALTER FUNCTION public.verificar_cnpj_existe(p_cnpj character varying) OWNER TO postgres;

--
-- Name: verificar_cpf_existe(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.verificar_cpf_existe(p_cpf character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
    existe BOOLEAN;
BEGIN
    SELECT EXISTS (
        SELECT 1 FROM pessoas_fisicas WHERE ds_cpf = p_cpf
    ) INTO existe;

    RETURN existe;
END;
$$;


ALTER FUNCTION public.verificar_cpf_existe(p_cpf character varying) OWNER TO postgres;

--
-- Name: verificar_email_existe(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.verificar_email_existe(p_email text) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN EXISTS (
        SELECT 1 FROM pessoas WHERE ds_email = TRIM(LOWER(p_email))
    );
END;
$$;


ALTER FUNCTION public.verificar_email_existe(p_email text) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorias (
    nr_id_categoria integer NOT NULL,
    nm_categoria character varying(100) NOT NULL
);


ALTER TABLE public.categorias OWNER TO postgres;

--
-- Name: categorias_nr_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categorias_nr_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categorias_nr_id_categoria_seq OWNER TO postgres;

--
-- Name: categorias_nr_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categorias_nr_id_categoria_seq OWNED BY public.categorias.nr_id_categoria;


--
-- Name: cupons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cupons (
    nr_id_cupom integer NOT NULL,
    nm_nome_cupom character varying(100) NOT NULL,
    ds_valor_desconto numeric(10,2) NOT NULL,
    ds_data_validade timestamp without time zone NOT NULL,
    ds_usado boolean DEFAULT false
);


ALTER TABLE public.cupons OWNER TO postgres;

--
-- Name: cupons_nr_id_cupom_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cupons_nr_id_cupom_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cupons_nr_id_cupom_seq OWNER TO postgres;

--
-- Name: cupons_nr_id_cupom_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cupons_nr_id_cupom_seq OWNED BY public.cupons.nr_id_cupom;


--
-- Name: cupons_utilizados; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cupons_utilizados (
    nr_id_cupons_utilizados integer NOT NULL,
    fk_nr_id_cupom integer,
    fk_nr_id_pessoa integer,
    ds_data_uso timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.cupons_utilizados OWNER TO postgres;

--
-- Name: cupons_utilizados_nr_id_cupons_utilizados_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cupons_utilizados_nr_id_cupons_utilizados_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cupons_utilizados_nr_id_cupons_utilizados_seq OWNER TO postgres;

--
-- Name: cupons_utilizados_nr_id_cupons_utilizados_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cupons_utilizados_nr_id_cupons_utilizados_seq OWNED BY public.cupons_utilizados.nr_id_cupons_utilizados;


--
-- Name: enderecos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enderecos (
    nr_id_endereco integer NOT NULL,
    nm_rua character varying(50) NOT NULL,
    ds_numero character varying(50),
    ds_bairro character varying(50) NOT NULL,
    ds_cidade character varying(50) NOT NULL,
    ds_uf character varying(2) NOT NULL,
    ds_cep character varying(10) NOT NULL,
    fk_nr_id_pessoa integer NOT NULL
);


ALTER TABLE public.enderecos OWNER TO postgres;

--
-- Name: enderecos_nr_id_endereco_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enderecos_nr_id_endereco_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.enderecos_nr_id_endereco_seq OWNER TO postgres;

--
-- Name: enderecos_nr_id_endereco_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enderecos_nr_id_endereco_seq OWNED BY public.enderecos.nr_id_endereco;


--
-- Name: imagens_produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.imagens_produtos (
    nr_id_imagem integer NOT NULL,
    fk_nr_id_produto integer,
    ds_caminho character varying(255)
);


ALTER TABLE public.imagens_produtos OWNER TO postgres;

--
-- Name: imagens_produtos_nr_id_imagem_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.imagens_produtos_nr_id_imagem_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.imagens_produtos_nr_id_imagem_seq OWNER TO postgres;

--
-- Name: imagens_produtos_nr_id_imagem_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.imagens_produtos_nr_id_imagem_seq OWNED BY public.imagens_produtos.nr_id_imagem;


--
-- Name: itens_pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itens_pedido (
    nr_id_itens_pedido integer NOT NULL,
    fk_nr_id_pedido integer NOT NULL,
    fk_nr_id_produto integer NOT NULL,
    ds_quantidade integer NOT NULL,
    ds_preco_unitario double precision NOT NULL
);


ALTER TABLE public.itens_pedido OWNER TO postgres;

--
-- Name: itens_pedido_nr_id_itens_pedido_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.itens_pedido_nr_id_itens_pedido_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.itens_pedido_nr_id_itens_pedido_seq OWNER TO postgres;

--
-- Name: itens_pedido_nr_id_itens_pedido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.itens_pedido_nr_id_itens_pedido_seq OWNED BY public.itens_pedido.nr_id_itens_pedido;


--
-- Name: pagamentos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagamentos (
    nr_id_pagamento integer NOT NULL,
    ds_forma_pagamento character varying(50) NOT NULL,
    ds_status_pagamento character varying(50) NOT NULL,
    ds_valor_total double precision,
    ds_data_pagamento timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    fk_nr_id_pedido integer NOT NULL,
    fk_nr_id_transacao_externa character varying(255)
);


ALTER TABLE public.pagamentos OWNER TO postgres;

--
-- Name: pagamentos_nr_id_pagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagamentos_nr_id_pagamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pagamentos_nr_id_pagamento_seq OWNER TO postgres;

--
-- Name: pagamentos_nr_id_pagamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagamentos_nr_id_pagamento_seq OWNED BY public.pagamentos.nr_id_pagamento;


--
-- Name: pedidos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidos (
    nr_id_pedido integer NOT NULL,
    dt_pedido timestamp without time zone,
    ds_status character varying(50),
    ds_desconto_aplicado numeric,
    ds_valor_total double precision,
    fk_nr_id_pessoa integer NOT NULL
);


ALTER TABLE public.pedidos OWNER TO postgres;

--
-- Name: pedidos_nr_id_pedido_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedidos_nr_id_pedido_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedidos_nr_id_pedido_seq OWNER TO postgres;

--
-- Name: pedidos_nr_id_pedido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedidos_nr_id_pedido_seq OWNED BY public.pedidos.nr_id_pedido;


--
-- Name: pessoas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoas (
    nr_id_pessoa integer NOT NULL,
    nm_roles character varying(100) NOT NULL,
    nm_nome_razaosocial character varying(100) NOT NULL,
    ds_email character varying(100) NOT NULL,
    ds_senha character varying(100) NOT NULL,
    ds_telefone character varying(100)
);


ALTER TABLE public.pessoas OWNER TO postgres;

--
-- Name: pessoas_fisicas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoas_fisicas (
    nr_id_pessoa_fisica integer NOT NULL,
    ds_cpf character varying(11) NOT NULL,
    ds_data_nascimento date NOT NULL,
    fk_nr_id_pessoa integer NOT NULL
);


ALTER TABLE public.pessoas_fisicas OWNER TO postgres;

--
-- Name: pessoas_fisicas_nr_id_pessoa_fisica_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pessoas_fisicas_nr_id_pessoa_fisica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pessoas_fisicas_nr_id_pessoa_fisica_seq OWNER TO postgres;

--
-- Name: pessoas_fisicas_nr_id_pessoa_fisica_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pessoas_fisicas_nr_id_pessoa_fisica_seq OWNED BY public.pessoas_fisicas.nr_id_pessoa_fisica;


--
-- Name: pessoas_juridicas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoas_juridicas (
    nr_id_pessoa_juridica integer NOT NULL,
    nm_nome_fantasia character varying(255),
    ds_cnpj character varying(14) NOT NULL,
    fk_nr_id_pessoa integer NOT NULL
);


ALTER TABLE public.pessoas_juridicas OWNER TO postgres;

--
-- Name: pessoas_juridicas_nr_id_pessoa_juridica_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pessoas_juridicas_nr_id_pessoa_juridica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pessoas_juridicas_nr_id_pessoa_juridica_seq OWNER TO postgres;

--
-- Name: pessoas_juridicas_nr_id_pessoa_juridica_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pessoas_juridicas_nr_id_pessoa_juridica_seq OWNED BY public.pessoas_juridicas.nr_id_pessoa_juridica;


--
-- Name: pessoas_nr_id_pessoa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pessoas_nr_id_pessoa_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pessoas_nr_id_pessoa_seq OWNER TO postgres;

--
-- Name: pessoas_nr_id_pessoa_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pessoas_nr_id_pessoa_seq OWNED BY public.pessoas.nr_id_pessoa;


--
-- Name: produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produtos (
    nr_id_produto integer NOT NULL,
    nm_nome character varying(255) NOT NULL,
    ds_preco double precision NOT NULL,
    ds_descricao text NOT NULL,
    ds_imagem_produto character varying(255) NOT NULL,
    fk_nr_id_categoria integer NOT NULL
);


ALTER TABLE public.produtos OWNER TO postgres;

--
-- Name: produtos_nr_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produtos_nr_id_produto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produtos_nr_id_produto_seq OWNER TO postgres;

--
-- Name: produtos_nr_id_produto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produtos_nr_id_produto_seq OWNED BY public.produtos.nr_id_produto;


--
-- Name: categorias nr_id_categoria; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias ALTER COLUMN nr_id_categoria SET DEFAULT nextval('public.categorias_nr_id_categoria_seq'::regclass);


--
-- Name: cupons nr_id_cupom; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupons ALTER COLUMN nr_id_cupom SET DEFAULT nextval('public.cupons_nr_id_cupom_seq'::regclass);


--
-- Name: cupons_utilizados nr_id_cupons_utilizados; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupons_utilizados ALTER COLUMN nr_id_cupons_utilizados SET DEFAULT nextval('public.cupons_utilizados_nr_id_cupons_utilizados_seq'::regclass);


--
-- Name: enderecos nr_id_endereco; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecos ALTER COLUMN nr_id_endereco SET DEFAULT nextval('public.enderecos_nr_id_endereco_seq'::regclass);


--
-- Name: imagens_produtos nr_id_imagem; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagens_produtos ALTER COLUMN nr_id_imagem SET DEFAULT nextval('public.imagens_produtos_nr_id_imagem_seq'::regclass);


--
-- Name: itens_pedido nr_id_itens_pedido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido ALTER COLUMN nr_id_itens_pedido SET DEFAULT nextval('public.itens_pedido_nr_id_itens_pedido_seq'::regclass);


--
-- Name: pagamentos nr_id_pagamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamentos ALTER COLUMN nr_id_pagamento SET DEFAULT nextval('public.pagamentos_nr_id_pagamento_seq'::regclass);


--
-- Name: pedidos nr_id_pedido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos ALTER COLUMN nr_id_pedido SET DEFAULT nextval('public.pedidos_nr_id_pedido_seq'::regclass);


--
-- Name: pessoas nr_id_pessoa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas ALTER COLUMN nr_id_pessoa SET DEFAULT nextval('public.pessoas_nr_id_pessoa_seq'::regclass);


--
-- Name: pessoas_fisicas nr_id_pessoa_fisica; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_fisicas ALTER COLUMN nr_id_pessoa_fisica SET DEFAULT nextval('public.pessoas_fisicas_nr_id_pessoa_fisica_seq'::regclass);


--
-- Name: pessoas_juridicas nr_id_pessoa_juridica; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_juridicas ALTER COLUMN nr_id_pessoa_juridica SET DEFAULT nextval('public.pessoas_juridicas_nr_id_pessoa_juridica_seq'::regclass);


--
-- Name: produtos nr_id_produto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos ALTER COLUMN nr_id_produto SET DEFAULT nextval('public.produtos_nr_id_produto_seq'::regclass);


--
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categorias (nr_id_categoria, nm_categoria) FROM stdin;
1	Eletronicos
3	Jogos
5	Console
6	   Video Game
7	Video
8	Camiseta
\.


--
-- Data for Name: cupons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cupons (nr_id_cupom, nm_nome_cupom, ds_valor_desconto, ds_data_validade, ds_usado) FROM stdin;
10	PRESENTE10	100.00	2025-05-10 09:00:00	f
11	PRESENTE	100.00	2025-05-10 09:00:00	t
2	DESCONTO10	10.00	2025-05-20 23:59:59	t
9	PRESENTE20	100.00	2025-05-10 09:00:00	t
15	SAMSUNG50	50.00	2025-05-14 09:00:00	t
7	TESTE	100.00	2025-05-10 09:00:00	t
\.


--
-- Data for Name: cupons_utilizados; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cupons_utilizados (nr_id_cupons_utilizados, fk_nr_id_cupom, fk_nr_id_pessoa, ds_data_uso) FROM stdin;
1	15	3	2025-05-13 10:36:10.787178
2	7	3	2025-05-13 10:39:53.559428
\.


--
-- Data for Name: enderecos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enderecos (nr_id_endereco, nm_rua, ds_numero, ds_bairro, ds_cidade, ds_uf, ds_cep, fk_nr_id_pessoa) FROM stdin;
2	Rua di Cavalcanti	123	Parque Imperial	Barueri	SP	06462-210	2
4	Rua Alpina	123	Parque Imperial	Barueri	SP	06462-510	4
5	Rua Nassimen Mussi	917	Jardim Itaipu	Marília	SP	17519-570	5
6	Rua Doutor Romeu Lages	433	Santa Cruz	Betim	MG	32667-374	6
1	Rua Alpina	225	Parque Imperial	Barueri	SP	06462-510	1
7	Rua Nassimen Mussi	917	Jardim Itaipu	Marília	SP	17519-570	7
3	Rua Alpina	225	Parque Imperial	Barueri	SP	06462-510	3
\.


--
-- Data for Name: imagens_produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.imagens_produtos (nr_id_imagem, fk_nr_id_produto, ds_caminho) FROM stdin;
1	7	/uploads/1742921209488_tablet_1.png .png
2	7	/uploads/1742927890330_tablet_2.png .png
3	7	/uploads/1742927946268_tablet_3.png .png
4	7	/uploads/1742928301744_tablet_4.png .png
5	8	/uploads/1744037759330_Computador_1.png
6	8	/uploads/1744037796382_Computador_2.png
7	8	/uploads/1744037815841_Computador_3.png
\.


--
-- Data for Name: itens_pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.itens_pedido (nr_id_itens_pedido, fk_nr_id_pedido, fk_nr_id_produto, ds_quantidade, ds_preco_unitario) FROM stdin;
1	1	7	5	806.16
2	2	7	5	806.16
3	3	7	5	806.16
\.


--
-- Data for Name: pagamentos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pagamentos (nr_id_pagamento, ds_forma_pagamento, ds_status_pagamento, ds_valor_total, ds_data_pagamento, fk_nr_id_pedido, fk_nr_id_transacao_externa) FROM stdin;
\.


--
-- Data for Name: pedidos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedidos (nr_id_pedido, dt_pedido, ds_status, ds_desconto_aplicado, ds_valor_total, fk_nr_id_pessoa) FROM stdin;
1	2025-05-13 10:36:11.968028	AGUARDANDO_PAGAMENTO	453.080	3577.72	3
2	2025-05-13 10:39:53.55911	AGUARDANDO_PAGAMENTO	503.080	3527.72	3
3	2025-05-13 11:22:51.716914	AGUARDANDO_PAGAMENTO	403.080	3627.72	3
\.


--
-- Data for Name: pessoas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pessoas (nr_id_pessoa, nm_roles, nm_nome_razaosocial, ds_email, ds_senha, ds_telefone) FROM stdin;
2	ADMINISTRADOR	Corretoras Associados LTDA	corretoras@associados.com	$2a$10$7ssFn1EWtIbImM/FGcgSIuCRew5QTrQoX96T05dmqGUfeGlap2fDS	(11) 98226-8833
4	CLIENTE	Bento Paulo	sant@gmail.com	$2a$10$ekpBmB5yX.8qHjRbvBoxye.IIF.1g2nBOAUKiCQOQHcxrSaBylxvG	(42) 98503-9931
5	ADMINISTRADOR	ME Financeira Associados	mefinanceira@associados.com.br	$2a$10$wY4Yd3vYEy82Sb50LbUIOuY2IIbUE/2HDXUVOObLNIPNLkWm6BneC	(14) 98387-6252
6	CLIENTE	Maya Elaine	maya@gmail.com	$2a$10$uql4ZshvHtL55p3mle0bauR3WEGPC7ZrXohYkXmCJlEETpffAgfiy	(31) 99931-5946
1	CLIENTE	Almeida Barreto	Almeida@gmail.com	$2a$10$EgcqqLjxV7HUfgnDTc9cCejMS52MUDXFj3yLM7y5P1.mqmf4LtbhG	(42) 98503-9931
7	ADMINISTRADOR	Doces Salgados LTDA	doces@associados.com.br	$2a$10$oIIdgmTVBt0OR.PBQMVLV.ITtEtXJjac.sKm/DZMgam5WSPtnXoCK	(14) 98387-6252
3	CLIENTE	Willians Santos	willianssantos227@gmail.com	$2a$10$M3mHFG7w4YocbfW69JzsBOQG6MvRMUtD2ENllWG2j8.pH9zD/yzyG	(42) 98503-9931
\.


--
-- Data for Name: pessoas_fisicas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pessoas_fisicas (nr_id_pessoa_fisica, ds_cpf, ds_data_nascimento, fk_nr_id_pessoa) FROM stdin;
3	33075631817	1963-04-14	4
4	13774199167	2003-01-12	6
1	47502188800	2000-10-24	1
2	48281854880	2000-10-24	3
\.


--
-- Data for Name: pessoas_juridicas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pessoas_juridicas (nr_id_pessoa_juridica, nm_nome_fantasia, ds_cnpj, fk_nr_id_pessoa) FROM stdin;
1	Corretoras ME	15998660000180	2
2	ME Financeira	13593334000168	5
3	DocesSalgados	15919079000126	7
\.


--
-- Data for Name: produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produtos (nr_id_produto, nm_nome, ds_preco, ds_descricao, ds_imagem_produto, fk_nr_id_categoria) FROM stdin;
1	Smartphone Samsung Galaxy S24 Ultra, Galaxy AI, Selfie de 12MP, Tela de 6.8" 1-120Hz, 256GB, 12GB RAM - Titânio Preto	6079.9	Marca\tSAMSUNG\nSistema operacional\tAndroid 14\nTamanho instalado da memória RAM\t12 GB\nModelo da CPU\t1.2GHz Cortex A8 Processor\nVelocidade da CPU\t2,3 GHz\nSobre este item\n256GB de Memória Interna(*) e 12GB RAM;\nBateria de 5000mAh\nGalaxy AI; Circule para pesquisar; Tradução simultânea de voz e texto por IA; Galeria Inteligente;\nCâmera Quádrupla de 200MP + 50MP +12MP + 10MP; Selfie de 12MP Dual Pixel AF;\nTela Infinita de 6.8"** 1-120Hz; Cadeado Galaxy;	/uploads/1743785151073_51rPU0jDc0L._AC_SL1000_.jpg	1
2	Fone de bets	150	Fone de ouvido	/uploads/1741276125753_best_2.png	1
3	MacBook	15000	notebook apple	/uploads/1741282999681_41J9j6iVDvS._AC_SL1000_.jpg	1
5	Caixa de som	500	caixa de som jbl	/uploads/1741719240276_new_5.jpg	1
7	Tablet Smartphone Caneta + Capa 10 Polegadas 128gb 8gb Ram	806.16	A Tablet Smartphone Caneta + Capa de 10 polegadas é a escolha ideal para quem busca versatilidade e desempenho em um único dispositivo. Com 128 GB de memória interna e 8 GB de RAM, você terá espaço de sobra para armazenar seus aplicativos, fotos e vídeos, além de garantir uma navegação fluida e rápida. Seu sistema operacional Android 12 proporciona uma experiência intuitiva e moderna, permitindo acesso a uma vasta gama de aplicativos e funcionalidades.	/uploads/1742828036861_tablet.png	1
8	Computador Completo Intel Core i5 8GB SSD 256GB Microsoft Windows 10 Monitor 19.5" 3green\n	952.93	De acordo com os comentários, este item se destaca pela sua velocidade e desempenho, sendo ideal para tarefas básicas e até mesmo para trabalhos mais exigentes. A resolução do monitor também recebeu muitos elogios, assim como a praticidade e o design compacto. Apesar de alguns relatos de problemas técnicos, a maioria dos usuários expressou satisfação com o produto.	/uploads/1743771410117_Computador.png	1
\.


--
-- Name: categorias_nr_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categorias_nr_id_categoria_seq', 8, true);


--
-- Name: cupons_nr_id_cupom_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cupons_nr_id_cupom_seq', 15, true);


--
-- Name: cupons_utilizados_nr_id_cupons_utilizados_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cupons_utilizados_nr_id_cupons_utilizados_seq', 2, true);


--
-- Name: enderecos_nr_id_endereco_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enderecos_nr_id_endereco_seq', 7, true);


--
-- Name: imagens_produtos_nr_id_imagem_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.imagens_produtos_nr_id_imagem_seq', 7, true);


--
-- Name: itens_pedido_nr_id_itens_pedido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itens_pedido_nr_id_itens_pedido_seq', 3, true);


--
-- Name: pagamentos_nr_id_pagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagamentos_nr_id_pagamento_seq', 1, false);


--
-- Name: pedidos_nr_id_pedido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedidos_nr_id_pedido_seq', 3, true);


--
-- Name: pessoas_fisicas_nr_id_pessoa_fisica_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pessoas_fisicas_nr_id_pessoa_fisica_seq', 4, true);


--
-- Name: pessoas_juridicas_nr_id_pessoa_juridica_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pessoas_juridicas_nr_id_pessoa_juridica_seq', 3, true);


--
-- Name: pessoas_nr_id_pessoa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pessoas_nr_id_pessoa_seq', 7, true);


--
-- Name: produtos_nr_id_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produtos_nr_id_produto_seq', 8, true);


--
-- Name: categorias categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias
    ADD CONSTRAINT categorias_pkey PRIMARY KEY (nr_id_categoria);


--
-- Name: cupons cupons_nm_nome_cupom_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupons
    ADD CONSTRAINT cupons_nm_nome_cupom_key UNIQUE (nm_nome_cupom);


--
-- Name: cupons cupons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupons
    ADD CONSTRAINT cupons_pkey PRIMARY KEY (nr_id_cupom);


--
-- Name: cupons_utilizados cupons_utilizados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupons_utilizados
    ADD CONSTRAINT cupons_utilizados_pkey PRIMARY KEY (nr_id_cupons_utilizados);


--
-- Name: enderecos enderecos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecos
    ADD CONSTRAINT enderecos_pkey PRIMARY KEY (nr_id_endereco);


--
-- Name: imagens_produtos imagens_produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imagens_produtos
    ADD CONSTRAINT imagens_produtos_pkey PRIMARY KEY (nr_id_imagem);


--
-- Name: itens_pedido itens_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido
    ADD CONSTRAINT itens_pedido_pkey PRIMARY KEY (nr_id_itens_pedido);


--
-- Name: pagamentos pagamentos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamentos
    ADD CONSTRAINT pagamentos_pkey PRIMARY KEY (nr_id_pagamento);


--
-- Name: pedidos pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_pkey PRIMARY KEY (nr_id_pedido);


--
-- Name: pessoas_fisicas pessoas_fisicas_ds_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_fisicas
    ADD CONSTRAINT pessoas_fisicas_ds_cpf_key UNIQUE (ds_cpf);


--
-- Name: pessoas_fisicas pessoas_fisicas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_fisicas
    ADD CONSTRAINT pessoas_fisicas_pkey PRIMARY KEY (nr_id_pessoa_fisica);


--
-- Name: pessoas_juridicas pessoas_juridicas_ds_cnpj_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_juridicas
    ADD CONSTRAINT pessoas_juridicas_ds_cnpj_key UNIQUE (ds_cnpj);


--
-- Name: pessoas_juridicas pessoas_juridicas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_juridicas
    ADD CONSTRAINT pessoas_juridicas_pkey PRIMARY KEY (nr_id_pessoa_juridica);


--
-- Name: pessoas pessoas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas
    ADD CONSTRAINT pessoas_pkey PRIMARY KEY (nr_id_pessoa);


--
-- Name: produtos produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_pkey PRIMARY KEY (nr_id_produto);


--
-- Name: cupons_utilizados cupons_utilizados_fk_nr_id_cupom_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cupons_utilizados
    ADD CONSTRAINT cupons_utilizados_fk_nr_id_cupom_fkey FOREIGN KEY (fk_nr_id_cupom) REFERENCES public.cupons(nr_id_cupom);


--
-- Name: enderecos enderecos_fk_nr_id_pessoa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enderecos
    ADD CONSTRAINT enderecos_fk_nr_id_pessoa_fkey FOREIGN KEY (fk_nr_id_pessoa) REFERENCES public.pessoas(nr_id_pessoa);


--
-- Name: itens_pedido itens_pedido_fk_nr_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido
    ADD CONSTRAINT itens_pedido_fk_nr_id_pedido_fkey FOREIGN KEY (fk_nr_id_pedido) REFERENCES public.pedidos(nr_id_pedido);


--
-- Name: itens_pedido itens_pedido_fk_nr_id_produto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedido
    ADD CONSTRAINT itens_pedido_fk_nr_id_produto_fkey FOREIGN KEY (fk_nr_id_produto) REFERENCES public.produtos(nr_id_produto);


--
-- Name: pedidos pedidos_fk_nr_id_pessoa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_fk_nr_id_pessoa_fkey FOREIGN KEY (fk_nr_id_pessoa) REFERENCES public.pessoas(nr_id_pessoa);


--
-- Name: pessoas_fisicas pessoas_fisicas_fk_nr_id_pessoa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_fisicas
    ADD CONSTRAINT pessoas_fisicas_fk_nr_id_pessoa_fkey FOREIGN KEY (fk_nr_id_pessoa) REFERENCES public.pessoas(nr_id_pessoa);


--
-- Name: pessoas_juridicas pessoas_juridicas_fk_nr_id_pessoa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoas_juridicas
    ADD CONSTRAINT pessoas_juridicas_fk_nr_id_pessoa_fkey FOREIGN KEY (fk_nr_id_pessoa) REFERENCES public.pessoas(nr_id_pessoa);


--
-- Name: produtos produtos_fk_nr_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_fk_nr_id_categoria_fkey FOREIGN KEY (fk_nr_id_categoria) REFERENCES public.categorias(nr_id_categoria);


--
-- PostgreSQL database dump complete
--

