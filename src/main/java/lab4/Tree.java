package lab4;

import java.util.HashMap;

public class Tree {
    private Node root;

    public Tree() {
        root = new Node();
        root.setProgram(true);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void print(HashMap<Integer, String> map){
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", this.root, map);
        System.out.println(sb.toString());
    }

    public void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node, HashMap<Integer, String> map) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getToken() == null ? "Prog" : (node.getToken().getTokenType() == TokenType.NAME ? map.get(node.getToken().getReference()) : node.getToken().getTokenType().getName()));
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.getRight() != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.getLeft(), map);
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.getRight(), map);
        }
    }
}
