/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.regra;

import com.github.kyriosdata.regras.Entidade;
import com.github.kyriosdata.regras.excecoes.CampoExigidoNaoFornecido;

import java.util.Date;
import java.util.List;

/**
 * Define conjunto de regras a ser utilizado em
 * uma avaliação, ou seja, sobre um relatório para
 * produzir uma contagem.
 *
 * @see com.github.kyriosdata.regras.Contagem
 * @see com.github.kyriosdata.regras.Relatorio
 */
public class Configuracao extends Entidade {

    /**
     * Data da configuração de regras.
     */
    private Date data;

    /**
     * Identificador da configuração.
     * Ou seja, é empregado aqui como um "nome de fantasia".
     * Contraste com {@link #id}.
     */
    private String nome;

    /**
     * Descrição ou informação adicional sobre
     * a configuração.
     */
    private String descricao;

    /**
     * Conjunto de regras definido pela configuração.
     */
    private List<Regra> regras;

    /**
     * Cria uma configuração.
     * @param id O identificador único da configuração.
     * @param configuracao O nome pelo qual seres humanos identificam
     *                     a configuração.
     * @param detalhes A descrição da configuração.
     * @param criacao A data de criação da configuração.
     * @param normas Conjunto de regras que definem a configuração.
     */
    public Configuracao(final String id, final String configuracao,
                        final String detalhes,
                        final Date criacao, final List<Regra> normas) {
        super(id);

        if (detalhes == null || detalhes.isEmpty()) {
            throw new CampoExigidoNaoFornecido("descricao");
        }

        if (criacao == null) {
            throw new CampoExigidoNaoFornecido("data");
        }

        if (normas == null || normas.isEmpty()) {
            throw new CampoExigidoNaoFornecido("regras");
        }

        this.nome = configuracao;
        this.descricao = detalhes;
        this.data = criacao;
        this.regras = normas;
    }

    /**
     * Recupera o nome da configuração.
     *
     * @return O nome da configuração.
     */
    public final String getNome() {
        return nome;
    }

    /**
     * Recupera a descrição da configuração.
     *
     * @return A descrição da configuração.
     */
    public final String getDescricao() {
        return descricao;
    }

    /**
     * Recupera a data de criação da configuração.
     *
     * @return Data de criação da resolução.
     */
    public final Date getData() {
        return data;
    }

    /**
     * Recupera o conjunto de regras definido
     * pela configuração.
     *
     * @return Conjunto de regras definido pela configuração.
     */
    public final List<Regra> getRegras() {
        return regras;
    }
}
