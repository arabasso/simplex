package sk.host.arabasso;

import java.util.Arrays;

/**
 * Created by arabasso on 29/09/2016.
 */
public final class SimplexLinha {
    public final int indice;
    public final SimplexColuna [] colunas;

    public SimplexLinha(double [] colunas, int indice) {
        final int[] indiceColuna = {0};

        this.colunas = new SimplexColuna[colunas.length];

        for(int i = 0; i < this.colunas.length; i++){
            this.colunas[i] = new SimplexColuna(colunas[i], i);
        }

        this.indice = indice;
    }

    public SimplexColuna pivo() {
        double valor = Double.MAX_VALUE;
        SimplexColuna coluna = null;

        for(int i = 0; i < colunas.length; i++){
            if (colunas[i].valor < valor){
                coluna = colunas[i];

                valor = coluna.valor;
            }
        }

        return coluna;
    }

    public double valorTotal() {
        return colunas[colunas.length - 1].valor;
    }

    public double valorTotalPeloPivo(SimplexColuna pivo) {
        return valorTotal() / pivo.valor;
    }

    public double valorTotalPeloIndicePivo(SimplexColuna pivo) {
        return valorTotal() / colunas[pivo.indice].valor;
    }

    public SimplexLinha multiplicar(SimplexColuna simplexColuna) {
        double [] colunasMultiplicadas = new double[colunas.length];

        for(int i = 0; i < colunasMultiplicadas.length; i++){
            colunasMultiplicadas[i] = colunas[i].valor * -simplexColuna.valor;
        }

        return new SimplexLinha(colunasMultiplicadas, indice);
    }

    public SimplexLinha somar(SimplexLinha linha) {
        double [] colunasSomadas = new double[colunas.length];

        for(int i = 0; i < colunasSomadas.length; i++){
            colunasSomadas[i] = colunas[i].valor + linha.colunas[i].valor;
        }

        return new SimplexLinha(colunasSomadas, indice);
    }
}
