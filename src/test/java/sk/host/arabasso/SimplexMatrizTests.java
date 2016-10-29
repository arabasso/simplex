package sk.host.arabasso;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by arabasso on 29/09/2016.
 *
 */
public class SimplexMatrizTests {
    private double [][] matriz = new double[][]{
        {1, -1, -2, -3, 0, 0, 0, 0},   // 0/-3  = 0
        {0,  1,  1,  3, 1, 0, 0, 100}, // 100/3 = 33,33333333333333
        {0,  2,  4,  2, 0, 1, 0, 150}, // 150/2 = 75
        {0,  1, -2,  1, 0, 0, 1, 250}, // 250/1 = 250
    };

    private SimplexColuna [] variaveisBasicas = new SimplexColuna[]{
        new SimplexColuna(100.0, 4),
        new SimplexColuna(150.0, 5),
        new SimplexColuna(250.0, 6),
    };

    private SimplexColuna [] variaveisNaoBasicas = new SimplexColuna[]{
            new SimplexColuna(0.0, 1),
            new SimplexColuna(0.0, 2),
            new SimplexColuna(0.0, 3),
    };

    private SimplexMatriz simplexMatriz;

    @Before
    public void setUp(){
        simplexMatriz = new SimplexMatriz(matriz);
    }

    @Test
    public void valorPivoDeveSer3(){
        SimplexColuna pivo = simplexMatriz.pivo();

        assertThat(pivo.valor,
                Matchers.is(closeTo(3.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void indicePivoDeveSerQuartoElemento(){
        SimplexColuna pivo = simplexMatriz.pivo();

        assertThat(pivo.indice,
                Matchers.is(Matchers.equalTo(3)));
    }

    @Test
    public void totalColunasDeveSer8(){
        assertThat(simplexMatriz.totalColunas(), is(equalTo(8)));
    }

    @Test
    public void linhaPivoDeveSerSegunda(){
        SimplexLinha linhaPivo = simplexMatriz.out();

        assertThat(linhaPivo.indice, is(equalTo(1)));
    }

    @Test
    public void colunasPivoNaoDevemSerVazio(){
        SimplexColuna [] colunas = simplexMatriz.in();

        assertThat(colunas, not(emptyArray()));
    }

    @Test
    public void colunasPivoDevemSerQuartaColuna(){
        for (SimplexColuna c : simplexMatriz.in()) {
            assertThat(c.indice, is(equalTo(3)));
        }
    }

    @Test
    public void variaveisBasicasIndicesDevemSerXf1Xf2Xf3(){
        SimplexColuna [] variaveis = simplexMatriz.variaveisBasicas();

        for (int i = 0; i < variaveisBasicas.length; i++) {
            int indice = variaveisBasicas[i].indice;
            int indiceSolucao = variaveis[i].indice;

            assertThat(indiceSolucao, is(equalTo(indice)));
        }
    }

    @Test
    public void variaveisBasicasValoresDevemSer0_100_150_250(){
        SimplexColuna [] variaveis = simplexMatriz.variaveisBasicas();

        for (int i = 0; i < variaveisBasicas.length; i++) {
            double valor = variaveisBasicas[i].valor;
            double valorSolucao = variaveis[i].valor;

            assertThat(valorSolucao, is(closeTo(valor, SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void variaveisNaoBasicasIndicesDevemSerX1X2X3(){
        SimplexColuna [] variaveis = simplexMatriz.variaveisNaoBasicas();

        for (int i = 0; i < variaveisNaoBasicas.length; i++) {
            int indice = variaveisNaoBasicas[i].indice;
            int indiceSolucao = variaveis[i].indice;

            assertThat(indiceSolucao, is(equalTo(indice)));
        }
    }

    @Test
    public void variaveisNaoBasicasIndicesDevemSer0(){
        SimplexColuna [] variaveis = simplexMatriz.variaveisNaoBasicas();

        for (int i = 0; i < variaveisNaoBasicas.length; i++) {
            double valorSolucao = variaveis[i].valor;

            assertThat(valorSolucao, is(closeTo(0.0, SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void valorZDeveSer0(){
        SimplexColuna c = simplexMatriz.valorZ();

        assertThat(c.valor, is(closeTo(0.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void igualdade(){
        assertThat(new SimplexMatriz(matriz), is(equalTo(new SimplexMatriz(matriz))));
    }
}
