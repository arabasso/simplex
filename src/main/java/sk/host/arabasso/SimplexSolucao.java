package sk.host.arabasso;

/**
 * Created by arabasso on 30/09/2016.
 */
public class SimplexSolucao {
    public final SimplexColuna[] variaveisBasicas;
    public final SimplexColuna[] variaveisNaoBasicas;

    public SimplexSolucao(SimplexMatriz matriz) {
        this.variaveisBasicas = matriz.variaveisBasicas();
        this.variaveisNaoBasicas = matriz.variaveisNaoBasicas();
    }
}
