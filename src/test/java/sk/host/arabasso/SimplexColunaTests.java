package sk.host.arabasso;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by arabasso on 29/09/2016.
 */
public class SimplexColunaTests {
    @Test
    public void inverterSinalValor(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);

        SimplexColuna c2 = c1.inverterSinal();

        assertThat(c2.valor, is(closeTo(-1.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void valorDeveSerPositivo(){
        SimplexColuna c1 = new SimplexColuna(-1.0, 0);

        SimplexColuna c2 = c1.abs();

        assertThat(c2.valor, is(closeTo(1.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void valorEhProximoDe1(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);

        assertThat(c1.valorProximoDe1(), is(equalTo(true)));
    }

    @Test
    public void valorNaoEhProximoDe1(){
        SimplexColuna c1 = new SimplexColuna(0.99, 0);

        assertThat(c1.valorProximoDe1(), is(equalTo(false)));
    }

    @Test
    public void inverterSinalIndiceDeveSerIgual(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);

        SimplexColuna c2 = c1.inverterSinal();

        assertThat(c2.indice, is(equalTo(c1.indice)));
    }

    @Test
    public void dividirColunasValorDeveSerMeio(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);
        SimplexColuna c2 = new SimplexColuna(2.0, 0);

        SimplexColuna c3 = c1.dividir(c2);

        assertThat(c3.valor, is(closeTo(0.5, SimplexColuna.EPSILON)));
    }

    @Test
    public void dividirColunasIndiceDeveSer0(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);
        SimplexColuna c2 = new SimplexColuna(2.0, 1);

        SimplexColuna c3 = c1.dividir(c2);

        assertThat(c3.indice, is(equalTo(0)));
    }

    @Test
    public void somarColunasValorDeveSer3(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);
        SimplexColuna c2 = new SimplexColuna(2.0, 0);

        SimplexColuna c3 = c1.somar(c2);

        assertThat(c3.valor, is(closeTo(3.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void somarColunasIndiceDeveSer0(){
        SimplexColuna c1 = new SimplexColuna(1, 0);
        SimplexColuna c2 = new SimplexColuna(2, 1);

        SimplexColuna c3 = c1.somar(c2);

        assertThat(c3.indice, is(equalTo(0)));
    }

    @Test
    public void multiplicarColunasValorDeveSer2(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);
        SimplexColuna c2 = new SimplexColuna(2.0, 0);

        SimplexColuna c3 = c1.multiplicar(c2);

        assertThat(c3.valor, is(closeTo(-2.0, SimplexColuna.EPSILON)));
    }

    @Test
    public void multiplicarColunasIndiceDeveSer0(){
        SimplexColuna c1 = new SimplexColuna(1.0, 0);
        SimplexColuna c2 = new SimplexColuna(2.0, 1);

        SimplexColuna c3 = c1.multiplicar(c2);

        assertThat(c3.indice, is(equalTo(0)));
    }
}
