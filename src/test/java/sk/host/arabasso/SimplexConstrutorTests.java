package sk.host.arabasso;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by arabasso on 24/10/2016.
 *
 */
public class SimplexConstrutorTests {
    private static final String EXPRESSAO_FO = "3X1 + 6X2 + 4X3";

    private static final String EXPRESSAO_REST_0 = " X1 +  X2 +  X3 <= 88";
    private static final String EXPRESSAO_REST_1 = "2X1 + 3X2 -  X3 <= 220";
    private static final String EXPRESSAO_REST_2 = "3X1 -  X2 + 2X3 <= 180";
    private static final String EXPRESSAO_REST_3 = "2X1 +  X2 +  X3 <= 120";

    private static final double[][] matriz = new double[][]{
            {1.0, -3.0, -6.0, -4.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0,  1.0,  1.0,  1.0, 1.0, 0.0, 0.0, 0.0, 88.0},
            {0.0,  2.0,  3.0, -1.0, 0.0, 1.0, 0.0, 0.0, 220.0},
            {0.0,  3.0, -1.0,  2.0, 0.0, 0.0, 1.0, 0.0, 180.0},
            {0.0,  2.0,  1.0,  1.0, 0.0, 0.0, 0.0, 1.0, 120.0},
    };

    private SimplexConstrutor construtor;
    private String[] variaveis = new String[]{
            "Z", "X1", "X2", "X3", "XF1", "XF2", "XF3", "XF4", "b"
    };

    @Before
    public void setUp(){
        construtor = new SimplexConstrutor();
    }

    @Test
    public void validarExpressao(){
        assertThat(construtor.validarExpressao(EXPRESSAO_FO), is(equalTo(true)));
    }

    @Test
    public void interpretarExpressao(){
        HashMap<String, Double> variaveis = new HashMap<>();

        variaveis.put("X1", 3.0);
        variaveis.put("X2", 6.0);
        variaveis.put("X3", 4.0);

        assertThat(construtor.interpretarExpressao(EXPRESSAO_FO), is(equalTo(variaveis)));
    }

    @Test
    public void interpretarExpressaoVariaveisSemNumero(){
        HashMap<String, Double> variaveis = new HashMap<>();

        variaveis.put("X1", 1.0);
        variaveis.put("X2", 1.0);
        variaveis.put("X3", -1.0);

        assertThat(construtor.interpretarExpressao("X1 + X2 - X3"), is(equalTo(variaveis)));
    }

    @Test
    public void interpretarFO(){
        HashMap<String, Double> variaveis = new HashMap<>();

        variaveis.put("Z", 1.0);
        variaveis.put("X1", -3.0);
        variaveis.put("X2", -6.0);
        variaveis.put("X3", -4.0);

        assertThat(construtor.interpretarFO(EXPRESSAO_FO), is(equalTo(variaveis)));
    }

    @Test
    public void validarExpressaoRestricao(){
        assertThat(construtor.validarExpressaoRestricao(EXPRESSAO_REST_1), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void interpretarRestricaoErradaDisparaExcecao(){
        HashMap<String, Double> variaveis = new HashMap<>();

        variaveis.put("X1", 2.0);
        variaveis.put("X2", 3.0);
        variaveis.put("X3", -1.0);
        variaveis.put("XF1", 1.0);
        variaveis.put("b", 220.0);

        assertThat(construtor.interpretarRestricao("1 + 2 + 3", 1), is(equalTo(variaveis)));
    }

    @Test
    public void interpretarRestricao(){
        HashMap<String, Double> variaveis = new HashMap<>();

        variaveis.put("X1", 2.0);
        variaveis.put("X2", 3.0);
        variaveis.put("X3", -1.0);
        variaveis.put("XF1", 1.0);
        variaveis.put("b", 220.0);

        assertThat(construtor.interpretarRestricao(EXPRESSAO_REST_1, 1), is(equalTo(variaveis)));
    }

    @Test
    public void extrairVariaveis(){
        Map<String, Double> fo = new LinkedHashMap<>();

        fo.put("Z", 0.0);
        fo.put("X1", 0.0);
        fo.put("X2", 0.0);
        fo.put("X3", 0.0);

        Map<String, Double> r = new LinkedHashMap<>();

        r.put("X1", 0.0);
        r.put("X2", 0.0);
        r.put("X3", 0.0);

        List<Map<String, Double>> restricoes = new ArrayList<>();

        restricoes.add(r);
        restricoes.add(r);
        restricoes.add(r);
        restricoes.add(r);

        assertThat(construtor.extrairVariaveis(fo, restricoes), is(equalTo(variaveis)));
    }

    @Test
    public void extrairVariaveisInterpretadas(){
        String fo = EXPRESSAO_FO;
        List<String> restricoes = new ArrayList<>();

        restricoes.add(EXPRESSAO_REST_0);
        restricoes.add(EXPRESSAO_REST_1);
        restricoes.add(EXPRESSAO_REST_2);
        restricoes.add(EXPRESSAO_REST_3);

        List<Map<String, Double>> r = new ArrayList<>();

        AtomicInteger i = new AtomicInteger();

        restricoes.stream().forEachOrdered(f ->{
            r.add(construtor.interpretarRestricao(f, i.incrementAndGet()));
        });

        assertThat(construtor.extrairVariaveis(construtor.interpretarFO(fo), r), is(equalTo(variaveis)));
    }

    private Simplex construirSimplex(){
        String fo = EXPRESSAO_FO;
        List<String> restricoes = new ArrayList<>();

        restricoes.add(EXPRESSAO_REST_0);
        restricoes.add(EXPRESSAO_REST_1);
        restricoes.add(EXPRESSAO_REST_2);
        restricoes.add(EXPRESSAO_REST_3);

        return construtor.construir(fo, restricoes);
    }

    @Test
    public void construirNaoPodeRetornarNulo(){
        assertThat(construirSimplex(), is(not(nullValue())));
    }

    @Test
    public void construirDeveRetornarVariaveisIguais(){
        assertThat(construirSimplex().variaveis, is(equalTo(variaveis)));
    }

    @Test
    public void construirDeveRetornarMatrizTotalColunasIgual(){
        assertThat(construirSimplex().matriz.totalColunas(), is(equalTo(9)));
    }

    @Test
    public void construirDeveRetornarMatrizTotalLinhasIgual(){
        assertThat(construirSimplex().matriz.linhas.length, is(equalTo(5)));
    }

    @Test
    public void construirDeveRetornarMatrizIgual(){
        assertThat(construirSimplex().matriz, is(equalTo(new SimplexMatriz(matriz))));
    }

    @Test
    public void construirDeveRetornarSimplexIgual(){
        assertThat(construirSimplex(), is(equalTo(new Simplex(matriz, variaveis))));
    }
}
