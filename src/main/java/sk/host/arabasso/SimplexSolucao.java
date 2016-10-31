package sk.host.arabasso;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

/**
 * Created by arabasso on 30/09/2016.
 *
 */
public class SimplexSolucao {
    public final boolean otima;
    public final SimplexColuna[] variaveisBasicas;
    public final SimplexColuna[] variaveisNaoBasicas;
    public final SimplexColuna valorZ;

    public SimplexSolucao(SimplexMatriz matriz) {
        this.otima = matriz.linhas[0].todosValoresSaoPositivos();
        this.variaveisBasicas = matriz.variaveisBasicas();
        this.variaveisNaoBasicas = matriz.variaveisNaoBasicas();
        this.valorZ = matriz.valorZ();
    }
}
