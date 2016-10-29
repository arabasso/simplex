package sk.host.arabasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by arabasso on 29/09/2016.
 *
 */
public final class SimplexLinha {
    final int indice;
    final SimplexColuna [] colunas;

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

    public SimplexLinha(String colunas, int indice) {
        Scanner scanner = new Scanner(colunas);

        ArrayList<Double> lista = new ArrayList<>();

        while (scanner.hasNext()) {
            lista.add(scanner.nextDouble());
        }

        this.colunas = new SimplexColuna[lista.size()];

        for(int i = 0; i < this.colunas.length; i++){
            this.colunas[i] = new SimplexColuna(lista.get(i), i);
        }

        this.indice = indice;
    }

    SimplexColuna colunaMenor() {
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

    double valorTotal() {
        return colunas[colunas.length - 1].valor;
    }

    double valorTotalPeloPivo(SimplexColuna pivo) {
        return valorTotal() / pivo.valor;
    }

    double valorTotalPeloIndicePivo(SimplexColuna pivo) {
        return valorTotal() / colunas[pivo.indice].valor;
    }

    SimplexLinha multiplicar(SimplexColuna coluna) {
        SimplexColuna [] colunasMultiplicadas = new SimplexColuna[colunas.length];

        for(int i = 0; i < colunasMultiplicadas.length; i++){
            colunasMultiplicadas[i] =  colunas[i].multiplicar(coluna);
        }

        return new SimplexLinha(colunasMultiplicadas, indice);
    }

    SimplexLinha somar(SimplexLinha linha) {
        SimplexColuna [] colunasSomadas = new SimplexColuna[colunas.length];

        for (int i = 0; i < colunasSomadas.length; i++){
            colunasSomadas[i] = colunas[i].somar(linha.colunas[i]);
        }

        return new SimplexLinha(colunasSomadas, indice);
    }

    SimplexLinha dividir(SimplexColuna coluna) {
        SimplexColuna [] novasColunas = Arrays
                .stream(colunas)
                .map(m -> m.dividir(coluna))
                .toArray(SimplexColuna[]::new);

        return new SimplexLinha(novasColunas, indice);
    }

    int totalColunas() {
        return colunas.length;
    }

    boolean todosValoresSaoPositivos() {
        return Arrays
                .stream(colunas)
                .allMatch(SimplexColuna::valorEhPositivo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplexLinha that = (SimplexLinha) o;

        if (indice != that.indice) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(colunas, that.colunas);

    }

    @Override
    public int hashCode() {
        int result = indice;
        result = 31 * result + Arrays.hashCode(colunas);
        return result;
    }

    @Override
    public String toString() {
        return "[" + indice + "] = " + Arrays.toString(colunas);
    }
}
