package sk.host.arabasso;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by arabasso on 29/09/2016.
 */
public class SimplexLinhaTests {
    private static final double PIVO = -3;
    private static final int PIVO_INDICE = 3;
    private static final int VALOR_TOTAL_INDICE = 7;
    private static final double PRECISAO = 0.00001;

    private double [] colunas = new double[]{
        1,      // 0
        -1,     // 1
        -2,     // 2
        PIVO,   // 3
        0,      // 4
        0,      // 5
        0,      // 6
        200     // 7
    };

    private double [] colunasMultiplicadas = new double[]{
        1 * -PIVO,      // 0
        -1 * -PIVO,     // 1
        -2 * -PIVO,     // 2
        -3 * -PIVO,     // 3
        0 * -PIVO,      // 4
        0 * -PIVO,      // 5
        0 * -PIVO,      // 6
        200 * -PIVO     // 7
    };

    private double [] colunasSomadas = new double[]{
            1 * 2,      // 0
            -1 * 2,     // 1
            -2 * 2,     // 2
            -3 * 2,     // 3
            0 * 2,      // 4
            0 * 2,      // 5
            0 * 2,      // 6
            200 * 2     // 7
    };

    @Test
    public void indicePivoDeveSerQuartoElemento(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexColuna pivo = linha.pivo();

        assertThat(pivo.indice,
                is(equalTo(3)));
    }

    @Test
    public void valorPivoDeveSerMenos3(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexColuna pivo = linha.pivo();

        assertThat(pivo.valor,
                is(closeTo(PIVO, PRECISAO)));
    }

    @Test
    public void valorTotalDeveSer200(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        assertThat(linha.valorTotal(),
                is(closeTo(200, PRECISAO)));
    }

    @Test
    public void valorTotalPeloPivoDeveSerMenos66(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        double valor = colunas[VALOR_TOTAL_INDICE] / colunas[PIVO_INDICE];

        SimplexColuna pivo = linha.pivo();

        assertThat(linha.valorTotalPeloPivo(pivo),
                is(closeTo(valor, PRECISAO)));
    }

    @Test
    public void valorTotalPeloIndicePivoDeveSerMenos66(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        double valor = colunas[VALOR_TOTAL_INDICE] / colunas[PIVO_INDICE];

        SimplexColuna pivo = linha.pivo();

        assertThat(linha.valorTotalPeloIndicePivo(pivo),
                is(closeTo(valor, PRECISAO)));
    }

    @Test
    public void multiplicarColunasPeloPivo(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexLinha novaLinha = linha.multiplicar(new SimplexColuna(PIVO, PIVO_INDICE));

        for(int i = 0; i < colunasMultiplicadas.length; i++){
            assertThat(novaLinha.colunas[i].valor,
                    is(closeTo(colunasMultiplicadas[i], PRECISAO)));
        }
    }

    @Test
    public void somarLinhas(){
        SimplexLinha linha = new SimplexLinha(colunas, 0);

        SimplexLinha novaLinha = linha.somar(linha);

        for(int i = 0; i < colunasSomadas.length; i++){
            assertThat(novaLinha.colunas[i].valor,
                    is(closeTo(colunasSomadas[i], PRECISAO)));
        }
    }
}
