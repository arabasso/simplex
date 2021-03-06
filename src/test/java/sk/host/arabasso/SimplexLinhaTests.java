package sk.host.arabasso;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by arabasso on 29/09/2016.
 *
 */
public class SimplexLinhaTests {
    private static final double PIVO = -3.0;
    private static final int PIVO_INDICE = 3;
    private static final int VALOR_TOTAL_INDICE = 7;
    private static final String COLUNAS_TEXTO = "1\t-4\t-3\t-2\t-1\t0\t0\t0\t0";

    private double [] colunas = new double[]{
        1.0,      // 0
        -1.0,     // 1
        -2.0,     // 2
        PIVO,     // 3
        0.0,      // 4
        0.0,      // 5
        0.0,      // 6
        200.0     // 7
    };

    private double [] colunasDivididas = new double[]{
            1.0 / PIVO,      // 0
            -1.0 / PIVO,     // 1
            -2.0 / PIVO,     // 2
            -3.0 / PIVO,     // 3
            0.0 / PIVO,      // 4
            0.0 / PIVO,      // 5
            0.0 / PIVO,      // 6
            200.0 / PIVO     // 7
    };

    private double [] colunasMultiplicadas = new double[]{
        1.0 * -PIVO,      // 0
        -1.0 * -PIVO,     // 1
        -2.0 * -PIVO,     // 2
        -3.0 * -PIVO,     // 3
        0.0 * -PIVO,      // 4
        0.0 * -PIVO,      // 5
        0.0 * -PIVO,      // 6
        200.0 * -PIVO     // 7
    };

    private double [] colunasSomadas = new double[]{
            1.0 * 2.0,      // 0
            -1.0 * 2.0,     // 1
            -2.0 * 2.0,     // 2
            -3.0 * 2.0,     // 3
            0.0 * 2.0,      // 4
            0.0 * 2.0,      // 5
            0.0 * 2.0,      // 6
            200.0 * 2.0     // 7
    };

    private double [] colunasTexto = new double[]{
            1.0,      // 0
            -4.0,     // 1
            -3.0,     // 2
            -2.0,     // 3
            -1.0,     // 4
            0.0,      // 5
            0.0,      // 6
            0.0,      // 7
            0.0,      // 8
    };

    @Test
    public void colunasComoStringDevemTer9Colunas(){
        SimplexLinha linha = new SimplexLinha(COLUNAS_TEXTO, 0);

        assertThat(linha.totalColunas(), is(equalTo(9)));
    }

    @Test
    public void colunasComoStringDevemSer1Menos4Menos3Menos2Menos1_0_0_0_0(){
        SimplexLinha linha = new SimplexLinha(COLUNAS_TEXTO, 0);

        for (int i = 0; i < colunasTexto.length; i++) {
            assertThat(linha.colunas[i].valor, is(closeTo(colunasTexto[i], SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void colunasComoStringDevemSerTerIndicesSequencias(){
        SimplexLinha linha = new SimplexLinha("1\t-4\t-3\t-2\t-1\t0\t0\t0\t0", 0);

        for (int i = 0; i < colunasTexto.length; i++) {
            assertThat(linha.colunas[i].indice, is(equalTo(i)));
        }
    }

    @Test
    public void totalColunasDeveSer8(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        assertThat(linha.totalColunas(), is(equalTo(8)));
    }

    @Test
    public void indicePivoDeveSerQuartoElemento(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexColuna pivo = linha.colunaMenor();

        assertThat(pivo.indice,
                is(equalTo(3)));
    }

    @Test
    public void valorTotalDeveSer200(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        assertThat(linha.valorTotal(),
                is(closeTo(200.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void valorTotalPeloPivoDeveSerMenos66(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        double valor = colunas[VALOR_TOTAL_INDICE] / colunas[PIVO_INDICE];

        SimplexColuna pivo = new SimplexColuna(PIVO, PIVO_INDICE);

        assertThat(linha.valorTotalPeloPivo(pivo),
                is(closeTo(valor, SimplexColuna.EPSILON)));
    }

    @Test
    public void valorTotalPeloIndicePivoDeveSerMenos66(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        double valor = colunas[VALOR_TOTAL_INDICE] / colunas[PIVO_INDICE];

        SimplexColuna pivo = linha.colunaMenor();

        assertThat(linha.valorTotalPeloIndicePivo(pivo),
                is(closeTo(valor, SimplexColuna.EPSILON)));
    }

    @Test
    public void dividirColunasPeloPivo(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexColuna coluna = new SimplexColuna(PIVO, PIVO_INDICE);

        SimplexLinha novaLinha = linha.dividir(coluna);

        for (int i = 0; i < colunasDivididas.length; i++){
            assertThat(novaLinha.colunas[i].valor,
                    is(closeTo(colunasDivididas[i], SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void multiplicarColunasPeloPivo(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexColuna coluna = new SimplexColuna(PIVO, PIVO_INDICE);

        SimplexLinha novaLinha = linha.multiplicar(coluna);

        for (int i = 0; i < colunasMultiplicadas.length; i++){
            assertThat(novaLinha.colunas[i].valor,
                    is(closeTo(colunasMultiplicadas[i], SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void somarLinhas(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexLinha novaLinha = linha.somar(linha);

        for (int i = 0; i < colunasSomadas.length; i++){
            assertThat(novaLinha.colunas[i].valor,
                    is(closeTo(colunasSomadas[i], SimplexColuna.EPSILON)));
        }
    }

    @Test
    public void todosValoresSaoPositivos(){
        double [] colunas = new double [] {1.0, 0.0, 2.0, 50.0};

        SimplexLinha linha = new SimplexLinha(colunas, 0);

        assertThat(linha.todosValoresSaoPositivos(), is(true));
    }

    @Test
    public void contemAlgumValorNegativo(){
        double [] colunas = new double [] {-1.0, 0.0, 2.0, 50.0};

        SimplexLinha linha = new SimplexLinha(colunas, 0);

        assertThat(linha.todosValoresSaoPositivos(), is(false));
    }

    @Test
    public void igualdade(){
        assertThat(new SimplexLinha(colunas, 0), is(equalTo(new SimplexLinha(colunas, 0))));
    }
}
