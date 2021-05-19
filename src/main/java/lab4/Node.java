package lab4;

public class Node {

    private Token token;
    private boolean isProgram;
    private Node left;
    private Node right;
    private boolean flag;

    public Node() {
    }

    public Node(Token token) {
        this.token = token;
    }

    public Node(boolean isProgram) {
        this.isProgram = isProgram;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isProgram() {
        return isProgram;
    }

    public void setProgram(boolean program) {
        isProgram = program;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
