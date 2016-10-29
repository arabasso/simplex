package sk.host.arabasso;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by arabasso on 24/10/2016.
 *
 */
public class SimplexConstrutor {
    private static final String PADRAO = "(\\s*(([+-]?)\\s*(\\d*(\\.\\d+)?))(X\\d+))";
    private static final String REGEX_EXPRESSAO_RESTRICAO = "(.+?)\\s*(<|<=|=)\\s*(\\d+(\\.\\d+)?)";

    public Simplex construir(String fo, List<String> restricoes) {
        Map<String, Double> vfo = interpretarFO(fo);

        List<Map<String, Double>> vrestricoes = new ArrayList<>();

        AtomicInteger i = new AtomicInteger();

        restricoes.stream().forEachOrdered(f ->{
            vrestricoes.add(interpretarRestricao(f, i.incrementAndGet()));
        });

        String [] variaveis = extrairVariaveis(vfo, vrestricoes);

        double [][] matriz = new double[vrestricoes.size() + 1][variaveis.length];

        for (int j = 0; j < variaveis.length; j++) {
            matriz[0][j] = vfo.getOrDefault(variaveis[j], 0.0);

            for (int k = 0; k < vrestricoes.size(); k++) {
                Map<String, Double> r = vrestricoes.get(k);

                matriz[k + 1][j] = r.getOrDefault(variaveis[j], 0.0);
            }
        }

        return new Simplex(matriz, variaveis);
    }

    public Map<String, Double> interpretarExpressao(String expressao) {
        Map<String, Double> v = new LinkedHashMap<>();

        Matcher matcher = Pattern.compile(PADRAO).matcher(expressao);

        while (matcher.find()){
            String valor = matcher.group(2).replace(" ", "");

            if (valor.equals("") || valor.equals("+") || valor.equals("-")){
                valor += "1.0";
            }

            v.put(matcher.group(6), Double.parseDouble(valor));
        }

        return v;
    }

    public boolean validarExpressao(String expressao) {
        return Pattern.matches(PADRAO + "+", expressao);
    }

    public Map<String, Double> interpretarFO(String expressao) {
        Map<String, Double> variaveis = new LinkedHashMap<>();

        variaveis.put("Z", 1.0);

        Map<String, Double> v = interpretarExpressao(expressao);

        v.entrySet().stream().forEach(f ->{
            f.setValue(-f.getValue());
        });

        variaveis.putAll(v);

        return variaveis;
    }

    public Map<String, Double> interpretarRestricao(String expressao, int folga) {
        Pattern p = Pattern.compile(REGEX_EXPRESSAO_RESTRICAO);

        Matcher m = p.matcher(expressao);

        if (!m.find()){
            throw new IllegalArgumentException("Expressão de restrição invalida");
        }

        Map<String, Double> variaveis = interpretarExpressao(m.group(1));

        variaveis.put("XF" + folga, 1.0);
        variaveis.put("b", Double.parseDouble(m.group(3).trim()));

        return variaveis;
    }

    public boolean validarExpressaoRestricao(String expressao) {
        return Pattern.matches(REGEX_EXPRESSAO_RESTRICAO, expressao);
    }

    public String [] extrairVariaveis(Map<String, Double> fo, List<Map<String, Double>> restricoes) {
        Set<String> variaveis = new LinkedHashSet<>();

        fo.entrySet().stream().forEach(f ->{
            variaveis.add(f.getKey());
        });

        AtomicInteger i = new AtomicInteger();

        restricoes.stream().forEachOrdered(f ->{
            f.entrySet().stream().limit(f.size() - 1).forEachOrdered(e ->{
                variaveis.add(e.getKey());
            });

            variaveis.add("XF" + i.incrementAndGet());
        });

        variaveis.add("b");

        return variaveis.toArray(new String[0]);
    }
}
