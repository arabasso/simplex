package sk.host.arabasso;

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
}
