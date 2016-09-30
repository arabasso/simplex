package sk.host.arabasso;

/**
 * Created by arabasso on 30/09/2016.
 */
public class Simplex {
    private SimplexMatriz matriz;

    public Simplex(double[][] matriz) {
         this.matriz = new SimplexMatriz(matriz);
    }

    public SimplexSolucao solucao() {
        return new SimplexSolucao(matriz);
    }
}
