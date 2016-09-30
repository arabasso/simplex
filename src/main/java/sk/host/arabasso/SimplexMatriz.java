package sk.host.arabasso;

import java.util.Arrays;

/**
 * Created by arabasso on 29/09/2016.
 */
public class SimplexMatriz {
    SimplexLinha[] linhas;

    public SimplexMatriz(double[][] matriz) {
        this.linhas = new SimplexLinha[matriz.length];

        for(int i = 0; i < this.linhas.length; i++){
            this.linhas[i] = new SimplexLinha(matriz[i], i);
        }
    }

    public SimplexLinha out() {
        SimplexColuna pivo = linhas[0].pivo();
        SimplexLinha linhaPivo = null;

        double menorValorPositivo = Double.MAX_VALUE;

        for (SimplexLinha linha : linhas) {
            double valorTotal = linha.valorTotalPeloIndicePivo(pivo);

            if (valorTotal > 0 && valorTotal < menorValorPositivo){
                menorValorPositivo = valorTotal;

                linhaPivo = linha;
            }
        }

        return linhaPivo;
//        double menorValorPositivo = Double.MAX_VALUE;
//        int linha = 0;
//
//        int totalColunas = matriz[0].length;
//
//        int coluna = in();
//        int colunab = totalColunas - 1;
//
//        for(int i = 0; i < matriz.length; i++){
//            double valorPivo = matriz[i][coluna];
//            double valorb = matriz[i][colunab];
//
//            double valor = valorb / valorPivo;
//
//            if (valor >= 0 && valor < menorValorPositivo){
//                menorValorPositivo = valor;
//                linha = i;
//            }
//        }
//
//        return linha;
    }

    public SimplexColuna[] in() {
        SimplexColuna pivo = linhas[0].pivo();

        SimplexColuna [] colunas = new SimplexColuna[linhas.length];

        for (int i = 0; i < linhas.length; i++) {
            colunas[i] = linhas[i].colunas[pivo.indice];
        }

        return colunas;
    }

//    public int in() {
//        double menorValor = Double.MAX_VALUE;
//        int coluna = 0;
//
//        for(int i = 0; i < matriz.length; i++){
//            if (matriz[0][i] < menorValor){
//                menorValor = matriz[0][i];
//                coluna = i;
//            }
//        }
//
//        return coluna;
//    }
//
//    public int out() {
//        double menorValorPositivo = Double.MAX_VALUE;
//        int linha = 0;
//
//        int totalColunas = matriz[0].length;
//
//        int coluna = in();
//        int colunab = totalColunas - 1;
//
//        for(int i = 0; i < matriz.length; i++){
//            double valorPivo = matriz[i][coluna];
//            double valorb = matriz[i][colunab];
//
//            double valor = valorb / valorPivo;
//
//            if (valor >= 0 && valor < menorValorPositivo){
//                menorValorPositivo = valor;
//                linha = i;
//            }
//        }
//
//        return linha;
//    }
}
