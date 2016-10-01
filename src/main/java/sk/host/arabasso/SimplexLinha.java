package sk.host.arabasso;

import java.util.Arrays;

/**
 * Created by arabasso on 29/09/2016.
 */
public final class SimplexLinha {
    public final int indice;
    public final SimplexColuna [] colunas;

    public SimplexLinha(SimplexColuna[] colunas, int indice) {
        this.colunas = Arrays.copyOf(colunas, colunas.length);
        this.indice = indice;
    }

    public SimplexLinha(double [] colunas, int indice) {
        this.colunas = new SimplexColuna[colunas.length];

        for(int i = 0; i < this.colunas.length; i++){
            this.colunas[i] = new SimplexColuna(colunas[i], i);
        }

        this.indice = indice;
    }

    public SimplexColuna colunaMenor() {
        double valor = Double.MAX_VALUE;
        SimplexColuna coluna = null;

        for (SimplexColuna c : colunas) {
            if (c.valor < valor) {
                coluna = c;

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

    public SimplexLinha multiplicar(SimplexColuna coluna) {
        SimplexColuna [] colunasMultiplicadas = new SimplexColuna[colunas.length];

        for(int i = 0; i < colunasMultiplicadas.length; i++){
            colunasMultiplicadas[i] =  colunas[i].multiplicar(coluna);
        }

        return new SimplexLinha(colunasMultiplicadas, indice);
    }

    public SimplexLinha somar(SimplexLinha linha) {
        SimplexColuna [] colunasSomadas = new SimplexColuna[colunas.length];

        for (int i = 0; i < colunasSomadas.length; i++){
            colunasSomadas[i] = colunas[i].somar(linha.colunas[i]);
        }

        return new SimplexLinha(colunasSomadas, indice);
    }

    public SimplexLinha dividir(SimplexColuna coluna) {
        SimplexColuna [] colunasMultiplicadas = new SimplexColuna[colunas.length];

        for(int i = 0; i < colunasMultiplicadas.length; i++){
            colunasMultiplicadas[i] =  colunas[i].dividir(coluna);
        }

        return new SimplexLinha(colunasMultiplicadas, indice);
    }

    public int totalColunas() {
        return colunas.length;
    }

    public boolean todosValoresSaoPositivos() {
        for (SimplexColuna c: colunas) {
            if (!c.valorEhPositivo()){
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "[" + indice + "] = " + Arrays.toString(colunas);
    }
}
