package sk.host.arabasso.printer;

import sk.host.arabasso.Simplex;
import sk.host.arabasso.SimplexSolucao;

/**
 * Created by arabasso on 31/10/2016.
 */
public interface SimplexPrinter {
    String tudo(Simplex simplex, SimplexSolucao solucao);
    String matriz(Simplex simplex, SimplexSolucao solucao);
    String solucao(Simplex simplex, SimplexSolucao solucao);
    String variaveisBasicas(Simplex simplex, SimplexSolucao solucao);
    String variaveisNaoBasicas(Simplex simplex, SimplexSolucao solucao);
    String valorZ(Simplex simplex, SimplexSolucao solucao);
}
