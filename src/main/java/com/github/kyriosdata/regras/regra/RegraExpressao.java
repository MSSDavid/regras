/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.regra;

import com.github.kyriosdata.parser.Expressao;
import com.github.kyriosdata.parser.IParser;
import com.github.kyriosdata.regras.Avaliavel;
import com.github.kyriosdata.regras.Funcao;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.excecoes.CampoExigidoNaoFornecido;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Regra definida por uma expressão formada por constantes,
 * variáveis (definidas em um contexto), identificadores de
 * funções e atributos de itens avaliáveis.
 *
 * <p>Uma expressão pode ser numérica, por exemplo,
 * "ch * 12.5", ou produzir um valor lógico, "3 &gt; 1".
 */
public class RegraExpressao extends Regra {

    /**
     * Expressão a ser avaliada para obtenção do
     * resultado da avaliação da regra.
     */
    private String expressao;

    /**
     * Árvore sintática da expressão executável.
     */
    protected Expressao ast;

    /**
     * Variáveis empregadas pela regra com
     * respectivos valores a serem utilizados.
     * Valores devem ser atualizados antes da
     * execução da regra.
     */
    protected Map<String, Float> ctx;

    /**
     * Cria uma regra.
     *
     * @param variavel      O identificador (nome) da variável que retém o
     *                      valor da avaliação da regra. Em um dado conjunto de
     *                      regras, existe uma variável distinta para cada uma
     *                      delas.
     * @param descricao     Texto que fornece alguma explanação sobre a regra.
     * @param valorMaximo   O valor máximo a ser utilizado como resultado da
     *                      avaliação da regra. Esse valor é empregado apenas
     *                      se a avaliação resultar em valor superior ao
     *                      expresso por esse parâmetro.
     * @param valorMinimo   O valor mínimo a ser utilizado como resultado da
     *                      avaliação da regra. Esse valor é empregado apenas
     *                      se a avaliação resultar em valor inferior ao
     *                      expresso por esse parâmetro.
     * @param expressao     A expressão empregada para avaliar a regra,
     *                      conforme o tipo.
     * @throws CampoExigidoNaoFornecido Caso um campo obrigatório para a
     *                                  definição de uma regra não seja fornecido.
     */
    public RegraExpressao(final String variavel, final String descricao,
                          final float valorMaximo, final float valorMinimo,
                          final String expressao) {
        super(variavel, descricao, valorMaximo, valorMinimo);
        if (expressao == null || expressao.isEmpty()) {
            throw new CampoExigidoNaoFornecido("expressao");
        }

        this.expressao = expressao;
    }

    /**
     * Esperado por algumas ferramentas, por exemplo, Gson.
     */
    private RegraExpressao() {
    }

    @Override
    public void preparacao(final IParser parser) {
        if (parser == null) {
            throw new CampoExigidoNaoFornecido("parser");
        }

        List<String> dd = parser.dependencias(expressao);
        setDependeDe(dd);

        ctx = new HashMap<>(dd.size());
        for (String dep : dd) {
           ctx.put(dep, 0f);
        }

        ast = parser.ast(expressao);
    }

    /**
     * Recupera a expressão associada à regra.
     *
     * @return A expressão empregada pela regra.
     */
    public String getExpressao() {
        return expressao;
    }

    /**
     * Recupera o contexto empregado pela expressão.
     *
     * @return Contexto empregado pela expressão.
     */
    protected Map<String, Float> getContexto() {
        return ctx;
    }

    @Override
    public Valor avalie(List<? extends Avaliavel> avaliaveis, Map<String, Valor> contexto) {
        atualizaContexto(contexto);

        // Avalia eventuais funções empregadas pela regra.
        for(Funcao f : getFuncoes()) {}

        return new Valor(ast.valor(ctx));
    }

    /**
     * As variáveis empregadas na avaliação da expressão e para as quais há
     * valor correspondente fornecido pelo contexto (argumento), assumirão os
     * valores definidos pelo contexto.
     *
     * @param contexto Contexto contendo valores para variáveis empregadas
     *                 na avaliação da expressão.
     */
    protected void atualizaContexto(final Map<String, Valor> contexto) {
        for(String dd : ctx.keySet()) {
            float valor = 0f;
            if (contexto.containsKey(dd)) {
                valor = contexto.get(dd).getReal();
            }

            ctx.put(dd, valor);
        }
    }

    @Override
    public final boolean equals(final Object outro) {
        if (this == outro) {
            return true;
        }

        if (outro == null || getClass() != outro.getClass()) {
            return false;
        }

        RegraExpressao regra = (RegraExpressao) outro;

        return expressao.equals(regra.expressao);
    }

    @Override
    public final int hashCode() {
        return expressao.hashCode();
    }
}
