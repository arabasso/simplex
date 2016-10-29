package sk.host.arabasso;

import java.text.NumberFormat;

/**
 * Created by arabasso on 29/09/2016.
 *
 */
public final class SimplexColuna {
    final static double EPSILON = 0.00001;

    final int indice;
    final double valor;

    public SimplexColuna(double valor, int indice) {
        this.indice = indice;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "[" + indice + "] = " + NumberFormat.getNumberInstance().format(valor);
    }

    SimplexColuna somar(SimplexColuna c2) {
        return new SimplexColuna(valor + c2.valor, indice);
    }

    SimplexColuna multiplicar(SimplexColuna c2) {
        return new SimplexColuna(valor * -c2.valor, indice);
    }

    SimplexColuna inverterSinal() {
        return new SimplexColuna(-valor, indice);
    }

    SimplexColuna abs() {
        return new SimplexColuna(Math.abs(valor), indice);
    }

    SimplexColuna dividir(SimplexColuna c2) {
        return new SimplexColuna(valor / c2.valor, indice);
    }

    boolean valorProximoDe1() {
        return Math.abs(1.0 - valor) < EPSILON;
    }

    boolean valorEhPositivo() {
        return valor >= 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplexColuna that = (SimplexColuna) o;

        if (indice != that.indice) return false;
        return Double.compare(that.valor, valor) < EPSILON;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = indice;
        temp = Double.doubleToLongBits(valor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    String toString(String[] variaveis) {
        return variaveis[indice] + " = " + NumberFormat.getNumberInstance().format(valor);
    }
}
