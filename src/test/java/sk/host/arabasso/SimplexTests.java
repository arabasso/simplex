package sk.host.arabasso;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by araba on 01/10/2016.
 */
public class SimplexTests {
    double [][] matriz = new double[][]{
        {1, -4, -3, -2, -1, 0, 0, 0, 0},
        {0,  1,  1,  1,  1, 1, 0, 0, 120},
        {0,  2,  1,  4, -1, 0, 1, 0, 260},
        {0,  1,  2,  1,  1, 0, 0, 1, 180},
    };

    SimplexColuna [] variaveisBasicas = new SimplexColuna[]{
            new SimplexColuna(120.0, 1),
            new SimplexColuna(20.0, 6),
            new SimplexColuna(60.0, 7),
    };

    SimplexColuna [] variaveisNaoBasicas = new SimplexColuna[]{
            new SimplexColuna(0.0, 2),
            new SimplexColuna(0.0, 3),
            new SimplexColuna(0.0, 4),
            new SimplexColuna(0.0, 5),
    };

    private SimplexSolucao solucaoInicial;
    private SimplexSolucao solucaoProximoAlgoritmo;

    @Before
    public void setUp(){
        Simplex simplex = new Simplex(matriz);

        solucaoInicial = simplex.solucao();

        Simplex simplexProximoAlgoritmo = simplex.proximoAlgoritmo();

        solucaoProximoAlgoritmo = simplexProximoAlgoritmo.solucao();
    }

    @Test
    public void solucaoInicialNaoEhOtima(){
        assertThat(solucaoInicial.otima, is(false));
    }

    @Test
    public void solucaoProximoAlgoritmoEhOtima(){
        assertThat(solucaoProximoAlgoritmo.otima, is(true));
    }

    @Test
    public void primeiraSolucaoNaoEhOtima(){
        for (int i = 0; i < variaveisBasicas.length; i++) {
            int indice = variaveisBasicas[i].indice;
            int indiceSolucao = solucaoProximoAlgoritmo.variaveisBasicas[i].indice;

            assertThat(indiceSolucao, Is.is(equalTo(indice)));
        }
    }

    @Test
    public void solucaoProximoAlgoritmoVariaveisBasicasIndicesDevemSerX1Xf2Xf3(){
        for (int i = 0; i < variaveisBasicas.length; i++) {
            int indice = variaveisBasicas[i].indice;
            int indiceSolucao = solucaoProximoAlgoritmo.variaveisBasicas[i].indice;

            assertThat(indiceSolucao, Is.is(equalTo(indice)));
        }
    }

    @Test
    public void solucaoProximoAlgoritmoVariaveisBasicasValoresDevemSer120_20_60(){
        for (int i = 0; i < variaveisBasicas.length; i++) {
            double valor = variaveisBasicas[i].valor;
            double valorSolucao = solucaoProximoAlgoritmo.variaveisBasicas[i].valor;

            assertThat(valorSolucao, Is.is(closeTo(valor, SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void solucaoProximoAlgoritmoVariaveisNaoBasicasIndicesDevemSerX2X3X4Xf1(){
        for (int i = 0; i < variaveisNaoBasicas.length; i++) {
            int indice = variaveisNaoBasicas[i].indice;
            int indiceSolucao = solucaoProximoAlgoritmo.variaveisNaoBasicas[i].indice;

            assertThat(indiceSolucao, Is.is(equalTo(indice)));
        }
    }

    @Test
    public void solucaoProximoAlgoritmoVariaveisNaoBasicasIndicesDevemSer0(){
        for (int i = 0; i < variaveisNaoBasicas.length; i++) {
            double valorSolucao = solucaoProximoAlgoritmo.variaveisNaoBasicas[i].valor;

            assertThat(valorSolucao, is(closeTo(0.0, SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void valorZDeveSer480(){
        assertThat(solucaoProximoAlgoritmo.valorZ.valor, is(closeTo(480.0, SimplexColuna.EPSILON)));
    }
}
