package sk.host.arabasso;

import java.text.NumberFormat;

/**
 * Created by arabasso on 29/09/2016.
 */
public final class SimplexColuna {
    public final static double EPSILON = 0.00001;

    public final int indice;
    public final double valor;

    public SimplexColuna(double valor, int indice) {
        this.indice = indice;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "[" + indice + "] = " + NumberFormat.getCurrencyInstance().format(valor);
    }

    public SimplexColuna somar(SimplexColuna c2) {
        return new SimplexColuna(valor + c2.valor, indice);
    }

    public SimplexColuna multiplicar(SimplexColuna c2) {
        return new SimplexColuna(valor * -c2.valor, indice);
    }

    public SimplexColuna inverterSinal() {
        return new SimplexColuna(-valor, indice);
    }

    public SimplexColuna abs() {
        return new SimplexColuna(Math.abs(valor), indice);
    }

    public SimplexColuna dividir(SimplexColuna c2) {
        return new SimplexColuna(valor / c2.valor, indice);
    }

    public boolean valorProximoDe1() {
        return Math.abs(1.0 - valor) < EPSILON;
    }

    public boolean valorEhPositivo() {
        return valor >= 0.0;
    }

    public String toString(String[] variaveis) {
        return variaveis[indice] + " = " + NumberFormat.getCurrencyInstance().format(valor);
    }
}
