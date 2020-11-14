package app;

import java.util.List;

public class RegExTree {

    protected int root;
    protected List<RegExTree> subTrees;

    public RegExTree(int root, List<RegExTree> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
    }

    // FROM TREE TO PARENTHESIS
    public String toString() {
        if (subTrees.isEmpty())
            return rootToString();
        String result = rootToString() + "(" + subTrees.get(0).toString();
        for (int i = 1; i < subTrees.size(); i++)
            result += "," + subTrees.get(i).toString();
        return result + ")";
    }

    private String rootToString() {
        if (root == Operand.CONCAT.getValue())
            return ".";
        if (root == Operand.ETOILE.getValue())
            return "*";
        if (root == Operand.ALTERN.getValue())
            return "|";
        if (root == Operand.DOT.getValue())
            return ".";
        
        return Character.toString((char) root);
    }


}
