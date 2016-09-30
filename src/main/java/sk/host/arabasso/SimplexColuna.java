package sk.host.arabasso;

/**
 * Created by arabasso on 29/09/2016.
 */
public final class SimplexColuna {
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
        return new SimplexColuna(valor * c2.valor, indice);
    }

    public SimplexColuna inverterSinal() {
        return new SimplexColuna(-valor, indice);
    }
}
