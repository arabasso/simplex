package sk.host.arabasso;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

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

    public String variaveisBasicasTexto(String[] variaveis){
        StringBuilder sb = new StringBuilder();

        for (SimplexColuna c : variaveisBasicas) {
            sb.append(c.toString(variaveis));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String variaveisNaoBasicasTexto(String[] variaveis){
        StringBuilder sb = new StringBuilder();

        for (SimplexColuna c : variaveisNaoBasicas) {
            sb.append(c.toString(variaveis));
            sb.append("\n");
        }

        return sb.toString();
    }

    public String valorZTexto(String[] variaveis){
        StringBuilder sb = new StringBuilder();

        sb.append(valorZ.toString(variaveis));
        sb.append("\n");

        return sb.toString();
    }

    public String valorZTexto(){
        StringBuilder sb = new StringBuilder();

        sb.append(NumberFormat.getCurrencyInstance().format(valorZ.valor));
        sb.append("\n");

        return sb.toString();
    }

    public String texto(String[] variaveis){
        StringBuilder sb = new StringBuilder();

        sb.append("Variáveis básicas\n");
        sb.append(variaveisBasicasTexto(variaveis));
        sb.append("\n");
        sb.append("Variáveis não básicas\n");
        sb.append(variaveisNaoBasicasTexto(variaveis));
        sb.append("\n");
        sb.append("Valor Z\n");
        sb.append(valorZTexto());

        return sb.toString();
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
