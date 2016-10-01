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
        return "[" + indice + "] = " + NumberFormat.getCurrencyInstance().format(valor);
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

    String toString(String[] variaveis) {
        return variaveis[indice] + " = " + NumberFormat.getCurrencyInstance().format(valor);
    }
}
