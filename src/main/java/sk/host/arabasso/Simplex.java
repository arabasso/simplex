package sk.host.arabasso;

/**
 * Created by arabasso on 30/09/2016.
 */
public class Simplex {
    private static double[][] matriz1 = new double[][]{
            {1, -4, -3, -2, -1, 0, 0, 0, 0},
            {0,  1,  1,  1,  1, 1, 0, 0, 120},
            {0,  2,  1,  4, -1, 0, 1, 0, 260},
            {0,  1,  2,  1,  1, 0, 0, 1, 180},
    };

    private static double[][] matriz2 = new double[][]{
            {1, -2, -3, -1, 0, 0, 0, 0},
            {0,  1,  1,  1, 1, 0, 0, 40},
            {0,  2,  1, -1, 0, 1, 0, 20},
            {0,  3,  2, -1, 0, 0, 1, 30},
    };

    private static double[][] matriz3 = new double[][]{
            {1, -3, -6, -4, 0, 0, 0, 0, 0},
            {0,  1,  1,  1, 1, 0, 0, 0, 88},
            {0,  2,  3, -1, 0, 1, 0, 0, 220},
            {0,  3, -1,  2, 0, 0, 1, 0, 180},
            {0,  2,  1,  1, 0, 0, 0, 1, 120},
    };

    public final SimplexMatriz matriz;

    public Simplex(double[][] matriz) {
         this.matriz = new SimplexMatriz(matriz);
    }

    public Simplex(SimplexLinha[] linhas) {
        this.matriz = new SimplexMatriz(linhas);
    }

    public SimplexSolucao solucao() {
        return new SimplexSolucao(matriz);
    }

    public Simplex proximoAlgoritmo(){
        SimplexLinha [] novasLinhas = new SimplexLinha[matriz.linhas.length];

        SimplexLinha linhaPivo = matriz.out();
        SimplexColuna pivo = matriz.pivo();

        SimplexLinha novaLinhaPivo = linhaPivo.dividir(pivo);

        SimplexColuna[] in = matriz.in();

        for (int i = 0; i < in.length; i++) {
            if (linhaPivo.indice == i){
                novasLinhas[i] = novaLinhaPivo;

                continue;
            }

            SimplexColuna coluna = in[i];

            SimplexLinha linha = matriz.linhas[i];

            SimplexLinha novaLinha = linha.somar(novaLinhaPivo.multiplicar(coluna));

            novasLinhas[i] = novaLinha;
        }

        return new Simplex(novasLinhas);
    }

    public static void main(String [] args){
        Simplex simplex = new Simplex(matriz3);

        SimplexSolucao solucao = simplex.solucao();

        while(!solucao.otima){
            simplex = simplex.proximoAlgoritmo();

            solucao = simplex.solucao();
        }

        solucao.imprimir();
    }
}
