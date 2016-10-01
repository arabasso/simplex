package sk.host.arabasso;

/**
 * Created by arabasso on 30/09/2016.
 *
 */
public class SimplexSolucao {
    final boolean otima;
    final SimplexColuna[] variaveisBasicas;
    final SimplexColuna[] variaveisNaoBasicas;
    final SimplexColuna valorZ;

    public SimplexSolucao(SimplexMatriz matriz) {
        this.otima = matriz.linhas[0].todosValoresSaoPositivos();
        this.variaveisBasicas = matriz.variaveisBasicas();
        this.variaveisNaoBasicas = matriz.variaveisNaoBasicas();
        this.valorZ = matriz.valorZ();
    }

    void imprimir(String[] variaveis) {
        System.out.println();
        System.out.println("Variaveis basicas");
        for (SimplexColuna c : variaveisBasicas) {
            System.out.println(c.toString(variaveis));
        }

        System.out.println();
        System.out.println("Variaveis nao basicas");
        for (SimplexColuna c : variaveisNaoBasicas) {
            System.out.println(c.toString(variaveis));
        }

        System.out.println();
        System.out.println("Valor Z");
        System.out.println(valorZ.toString(variaveis));
    }
}
