package sk.host.arabasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by arabasso on 29/09/2016.
 *
 */
public class SimplexMatriz {
    SimplexLinha[] linhas;

    public SimplexMatriz(double[][] matriz) {
        this.linhas = new SimplexLinha[matriz.length];

        for(int i = 0; i < this.linhas.length; i++){
            this.linhas[i] = new SimplexLinha(matriz[i], i);
        }
    }

    SimplexColuna pivo() {
        SimplexColuna colunaMenor = linhas[0].colunaMenor();

        return out().colunas[colunaMenor.indice];
    }

    public SimplexMatriz(SimplexLinha[] linhas) {
        this.linhas = Arrays.copyOf(linhas, linhas.length);
    }

    int totalColunas() {
        return linhas[0].totalColunas();
    }

    SimplexLinha out() {
        SimplexColuna colunaMenor = linhas[0].colunaMenor();
        SimplexLinha linhaPivo = null;

        double menorValorPositivo = Double.MAX_VALUE;

        for (SimplexLinha linha : linhas) {
            double valorTotal = linha.valorTotalPeloIndicePivo(colunaMenor);

            if (valorTotal > 0 && valorTotal < menorValorPositivo){
                menorValorPositivo = valorTotal;

                linhaPivo = linha;
            }
        }

        return linhaPivo;
    }

    SimplexColuna[] in() {
        SimplexColuna pivo = pivo();

        SimplexColuna [] colunas = new SimplexColuna[linhas.length];

        for (int i = 0; i < linhas.length; i++) {
            colunas[i] = linhas[i].colunas[pivo.indice];
        }

        return colunas;
    }

    SimplexColuna[] variaveisBasicas() {
        ArrayList<SimplexColuna> colunas = new ArrayList<>();

        SimplexLinha linhaBasica = null;

        for(int i = 1; i < totalColunas(); i++){
            SimplexColuna variavelBasica = new SimplexColuna(0.0, i);

            for (SimplexLinha linha : linhas) {
                SimplexColuna coluna = linha.colunas[i];

                if (coluna.valorProximoDe1()) {
                    linhaBasica = linha;
                }

                variavelBasica = variavelBasica.somar(coluna.abs());
            }

            if (variavelBasica.valorProximoDe1()){
                assert linhaBasica != null;

                colunas.add(new SimplexColuna(linhaBasica.valorTotal(), variavelBasica.indice));
            }
        }

        return colunas.toArray(new SimplexColuna[0]);
    }

    SimplexColuna[] variaveisNaoBasicas() {
        ArrayList<SimplexColuna> colunas = new ArrayList<>();

        for(int i = 1; i < totalColunas() - 1; i++){
            SimplexColuna variavelBasica = new SimplexColuna(0.0, i);

            for (SimplexLinha linha : linhas) {
                SimplexColuna coluna = linha.colunas[i];

                variavelBasica = variavelBasica.somar(coluna.abs());
            }

            if (!variavelBasica.valorProximoDe1()){
                colunas.add(new SimplexColuna(0.0, variavelBasica.indice));
            }
        }

        return colunas.toArray(new SimplexColuna[0]);
    }

    SimplexColuna valorZ() {
        double valor = linhas[0].valorTotal();

        return new SimplexColuna(valor, totalColunas() - 1);
    }
}
