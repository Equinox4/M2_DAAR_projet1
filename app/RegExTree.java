package app;

import java.util.ArrayList;
import static app.Constants.*;

public class RegExTree {

    protected int root;
    protected ArrayList<RegExTree> subTrees;

    public RegExTree(int root, ArrayList<RegExTree> subTrees) {
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
        if (root == CONCAT)
            return ".";
        if (root == ETOILE)
            return "*";
        if (root == ALTERN)
            return "|";
        if (root == DOT)
            return ".";
        return Character.toString((char) root);
    }

}
