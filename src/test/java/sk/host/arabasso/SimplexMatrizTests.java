package sk.host.arabasso;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by arabasso on 29/09/2016.
 */
public class SimplexMatrizTests {
    private static final double PRECISAO = 0.00001;

    private double [][] matriz = new double[][]{
        {1, -1, -2, -3, 0, 0, 0, 200}, // 200/-3 = -66,66666666666667
        {0,  1,  1,  3, 1, 0, 0, 100}, // 100/3  = 33,33333333333333
        {0,  2,  4,  2, 0, 1, 0, 150}, // 150/2  = 75
        {0,  1, -2,  1, 0, 0, 1, 250}, // 250/1  = 250
    };

    @Test
    public void linhaPivoDeveSerSegunda(){
        SimplexMatriz simplexMatriz = new SimplexMatriz(matriz);

        SimplexLinha linhaPivo = simplexMatriz.out();

        assertThat(linhaPivo.indice, is(equalTo(1)));
    }

    @Test
    public void colunasPivoNaoDevemSerVazio(){
        SimplexMatriz simplexMatriz = new SimplexMatriz(matriz);

        SimplexColuna [] colunas = simplexMatriz.in();

        assertThat(colunas, not(emptyArray()));
    }

    @Test
    public void colunasPivoDevemSerQuartaColuna(){
        SimplexMatriz simplexMatriz = new SimplexMatriz(matriz);

        SimplexColuna [] colunas = simplexMatriz.in();

        for (SimplexColuna c : colunas) {
            assertThat(c.indice, is(equalTo(3)));
        }
    }
}
