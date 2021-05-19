import lab1.Grammar;
import org.junit.Assert;
import org.junit.Test;

public class GrammarTest {

    @Test
    public void test1(){
        Assert.assertTrue(Grammar.check("В"));
    }

    @Test
    public void test2(){
        Assert.assertTrue(Grammar.check("БВВ"));
    }

    @Test
    public void test3(){
        Assert.assertTrue(Grammar.check("АББВВВ"));
    }

    @Test
    public void test4(){
        Assert.assertTrue(Grammar.check("ААБББВВВВ"));
    }

    @Test
    public void test5(){
        Assert.assertTrue(Grammar.check("АААББББВВВВВ"));
    }

    @Test
    public void test6(){
        Assert.assertFalse(Grammar.check("АААББББВВВВ"));
    }

    @Test
    public void test7(){
        Assert.assertFalse(Grammar.check("АББББВВВВВ"));
    }

    @Test
    public void test8(){
        Assert.assertFalse(Grammar.check("ААББВВ"));
    }
}
