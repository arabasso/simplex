package sk.host.arabasso;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by arabasso on 29/09/2016.
 */
public class SimplexColunaTests {
    private static final double PRECISAO = 0.00001;

    @Test
    public void somarValoresDeveRetornar3(){
        SimplexColuna c1 = new SimplexColuna(1, 0);
        SimplexColuna c2 = new SimplexColuna(2, 0);

        SimplexColuna c3 = c1.somar(c2);

        assertThat(c3.valor, is(closeTo(3, PRECISAO)));
    }

    @Test
    public void multiplicarValoresDeveRetornar2(){
        SimplexColuna c1 = new SimplexColuna(1, 0);
        SimplexColuna c2 = new SimplexColuna(2, 0);

        SimplexColuna c3 = c1.multiplicar(c2);

        assertThat(c3.valor, is(closeTo(2, PRECISAO)));
    }
}
