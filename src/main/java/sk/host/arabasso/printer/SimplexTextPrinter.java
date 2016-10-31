package sk.host.arabasso.printer;

import sk.host.arabasso.Simplex;
import sk.host.arabasso.SimplexColuna;
import sk.host.arabasso.SimplexSolucao;

import java.text.NumberFormat;

/**
 * Created by arabasso on 31/10/2016.
 *
 */
public class SimplexTextPrinter implements SimplexPrinter {
    @Override
    public String tudo(Simplex simplex, SimplexSolucao solucao) {
        return solucao(simplex, solucao);
    }

    @Override
    public String matriz(Simplex simplex, SimplexSolucao solucao) {
        return null;
    }

    @Override
    public String solucao(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        sb.append(variaveisBasicas(simplex, solucao));
        sb.append(variaveisNaoBasicas(simplex, solucao));
        sb.append(valorZ(simplex, solucao));

        return sb.toString();
    }

    @Override
    public String variaveisBasicas(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        for (SimplexColuna c : solucao.variaveisBasicas) {
            sb.append(c.toString(simplex.variaveis));
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String variaveisNaoBasicas(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        for (SimplexColuna c : solucao.variaveisNaoBasicas) {
            sb.append(c.toString(simplex.variaveis));
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String valorZ(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        sb.append(NumberFormat.getCurrencyInstance().format(solucao.valorZ.valor));
        sb.append("\n");

        return sb.toString();
    }
}
