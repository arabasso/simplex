package sk.host.arabasso.printer;

import sk.host.arabasso.Simplex;
import sk.host.arabasso.SimplexColuna;
import sk.host.arabasso.SimplexLinha;
import sk.host.arabasso.SimplexSolucao;

import java.text.NumberFormat;

/**
 * Created by arabasso on 31/10/2016.
 *
 */
public class SimplexHtmlPrinter implements SimplexPrinter {
    @Override
    public String tudo(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        sb.append(matriz(simplex, solucao));
        sb.append(solucao(simplex, solucao));

        return sb.toString();
    }

    @Override
    public String matriz(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        SimplexLinha[] linhas = simplex.matriz.linhas;

        sb.append("<table>");

        sb.append("<thead>");
        sb.append("<tr>");

        String [] variaveis = simplex.variaveis;

        for (int i = 0; i < variaveis.length; i++) {
            sb.append("<th>");
            sb.append(variaveis[i]);
            sb.append("</th>");
        }

        sb.append("</tr>");
        sb.append("</thead>");

        sb.append("<tbody>");
        for (int i = 0; i < linhas.length; i++) {
            sb.append("<tr>");

            for (int j = 0; j < linhas[i].colunas.length; j++) {
                sb.append("<td>");
                sb.append(linhas[i].colunas[j].toSimpleString());
                sb.append("</td>");
            }

            sb.append("</tr>");
        }

        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }

    @Override
    public String solucao(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        sb.append("<table>");
        sb.append("<caption>");
        sb.append("Solução");
        sb.append("</caption>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append(variaveisBasicas(simplex, solucao));
        sb.append("</td>");
        sb.append("<td>");
        sb.append(variaveisNaoBasicas(simplex, solucao));
        sb.append("</td>");
        sb.append("<td>");
        sb.append(valorZ(simplex, solucao));
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }

    @Override
    public String variaveisBasicas(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        for (SimplexColuna c : solucao.variaveisBasicas) {
            sb.append("<div>");
            sb.append(c.toString(simplex.variaveis));
            sb.append("</div>");
        }

        return sb.toString();
    }

    @Override
    public String variaveisNaoBasicas(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        for (SimplexColuna c : solucao.variaveisNaoBasicas) {
            sb.append("<div>");
            sb.append(c.toString(simplex.variaveis));
            sb.append("</div>");
        }

        return sb.toString();
    }

    @Override
    public String valorZ(Simplex simplex, SimplexSolucao solucao) {
        StringBuilder sb = new StringBuilder();

        sb.append("<div>");
        sb.append(NumberFormat.getCurrencyInstance().format(solucao.valorZ.valor));
        sb.append("</div>");

        return sb.toString();
    }
}
