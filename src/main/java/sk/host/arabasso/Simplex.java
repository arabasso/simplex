package sk.host.arabasso;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by arabasso on 30/09/2016.
 *
 */
public class Simplex {
    public final String [] variaveis;
    public final SimplexMatriz matriz;

    public Simplex(double[][] matriz) {
        this.matriz = new SimplexMatriz(matriz);
        this.variaveis = new String[this.matriz.totalColunas()];
    }

    public Simplex(double[][] matriz, String [] variaveis) {
        this.matriz = new SimplexMatriz(matriz);
        this.variaveis = Arrays.copyOf(variaveis, variaveis.length);
    }

    public Simplex(SimplexLinha[] linhas) {
        this.matriz = new SimplexMatriz(linhas);
        this.variaveis = new String[this.matriz.totalColunas()];
    }

    public Simplex(SimplexLinha[] linhas, String [] variaveis) {
        this.matriz = new SimplexMatriz(linhas);
        this.variaveis = Arrays.copyOf(variaveis, variaveis.length);
    }

    public Simplex(String arg) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(arg))) {
            ArrayList<String> variaveis = new ArrayList<>();
            ArrayList<SimplexLinha> linhas = new ArrayList<>();

            AtomicInteger indice = new AtomicInteger();

            stream.forEach(linha ->{
                int i = indice.getAndIncrement();

                if (i > 0){
                    linhas.add(new SimplexLinha(linha, i - 1));
                }

                else{
                    StringTokenizer st = new StringTokenizer(linha);

                    while(st.hasMoreTokens()){
                        variaveis.add(st.nextToken());
                    }
                }
            });

            this.variaveis = variaveis.toArray(new String[0]);
            this.matriz = new SimplexMatriz(linhas.toArray(new SimplexLinha[0]));
        }
    }

    SimplexSolucao solucao() {
        return new SimplexSolucao(matriz);
    }

    private SimplexLinha novaLinha(SimplexColuna coluna,
                                   SimplexLinha novaLinhaPivo,
                                   SimplexLinha linha){
        return linha.somar(novaLinhaPivo.multiplicar(coluna));
    }

    Simplex proximoAlgoritmo(){
        SimplexLinha [] novasLinhas = new SimplexLinha[matriz.linhas.length];

        SimplexLinha linhaPivo = matriz.out();
        SimplexColuna pivo = matriz.pivo();

        SimplexLinha novaLinhaPivo = linhaPivo.dividir(pivo);

        SimplexColuna[] colunaPivo = matriz.in();

        for (int i = 0; i < colunaPivo.length; i++) {
            if (linhaPivo.indice == i){
                novasLinhas[i] = novaLinhaPivo;

                continue;
            }

            novasLinhas[i] = novaLinha(colunaPivo[i], novaLinhaPivo, matriz.linhas[i]);
        }

        Simplex s = new Simplex(novasLinhas, this.variaveis);

        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Simplex simplex = (Simplex) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(variaveis, simplex.variaveis)) return false;
        return matriz.equals(simplex.matriz);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(variaveis);
        result = 31 * result + matriz.hashCode();
        return result;
    }

    private static void aumentarFonte(float escala){
        UIDefaults uidef = UIManager.getLookAndFeelDefaults();
        for (Map.Entry<Object,Object> e : uidef.entrySet()) {
            Object val = e.getValue();
            if (val != null && val instanceof FontUIResource) {
                FontUIResource fui = (FontUIResource)val;
                uidef.put(e.getKey(), fui.deriveFont(fui.getSize2D() * escala));
            }
        }
    }

    public static void main(String [] args){
        if (args.length < 1){


            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (Exception e) {
            }

            aumentarFonte(2.0f);

            SimplexJanela janela = new SimplexJanela("Simplex");

            janela.setVisible(true);

            return;
        }

        if (args.length != 1){
            System.out.println("uso: java -jar simplex.jar arquivo");

            return;
        }

        try {
            Simplex simplex = new Simplex(args[0]);

            SimplexSolucao solucao = simplex.solucao();

            while(!solucao.otima){
                simplex = simplex.proximoAlgoritmo();

                solucao = simplex.solucao();
            }

            solucao.imprimir(simplex.variaveis);
        } catch (IOException e) {
            System.out.println("Aquivo inexistente: " + e.getMessage());
        }
    }
}
