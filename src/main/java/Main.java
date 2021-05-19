import lab4.CreateTree;
import lab4.Parser;


public class Main {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        CreateTree.creatTree(parser.parse("src/main/resources/test.txt"), parser.getMap());
    }
}
