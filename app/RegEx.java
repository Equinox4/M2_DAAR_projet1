package app;

import java.util.ArrayList;

public class RegEx {

    private String regex;
    private RegExTree regexTree;
    protected ArrayList<String> alphabet;

    // CONSTRUCTOR
    public RegEx(String regEx) {
        this.regex = regEx;
        try {
            this.regexTree = RegEx.parse(this);
            this.alphabet = alphabet(this.regexTree, new ArrayList<>());
            this.alphabet.add("epsilon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static RegExTree parse(RegEx regex) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<>();
        for (int i = 0; i < regex.getRegEx().length(); i++)
            result.add(new RegExTree(charToRoot(regex.getRegEx().charAt(i)), new ArrayList<>()));

        return parse(result);
    }

    private String getRegEx() {
        return this.regex;
    }

    public RegExTree getRegExTree() {
        return this.regexTree;
    }

    /*
    // FROM REGEX TO SYNTAX TREE
    private static RegExTree parse() throws Exception {
        ArrayList<RegExTree> result = new ArrayList<>();
        for (int i = 0; i < regEx.length(); i++)
            result.add(new RegExTree(charToRoot(RegEx.charAt(i)), new ArrayList<>()));

        return parse(result);
    }
    */

    private static int charToRoot(char c) {
        switch (c) {
            case '.': return Operand.CONCAT.getValue();
            case '*': return Operand.ETOILE.getValue();
            case '|': return Operand.ALTERN.getValue();
            case '(': return Operand.PARENTHESEOUVRANT.getValue();
            case ')': return Operand.PARENTHESEFERMANT.getValue();
            default: return (int) c;
        }
    }

    private static RegExTree parse(ArrayList<RegExTree> result) throws Exception {
        while (containParenthese(result))
            result = processParenthese(result);
        while (containEtoile(result))
            result = processEtoile(result);
        while (containConcat(result))
            result = processConcat(result);
        while (containAltern(result))
            result = processAltern(result);

        if (result.size() > 1)
            throw new Exception();

        return removeProtection(result.get(0));
    }

    private static boolean containParenthese(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.root == Operand.PARENTHESEFERMANT.getValue() || t.root == Operand.PARENTHESEOUVRANT.getValue())
                return true;
        return false;
    }

    // TODO: refactor
    private static ArrayList<RegExTree> processParenthese(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.root == Operand.PARENTHESEFERMANT.getValue()) {
                boolean done = false;
                ArrayList<RegExTree> content = new ArrayList<>();
                while (!done && !result.isEmpty())
                    if (result.get(result.size() - 1).root == Operand.PARENTHESEOUVRANT.getValue()) {
                        done = true;
                        result.remove(result.size() - 1);
                    } else
                        content.add(0, result.remove(result.size() - 1));
                if (!done)
                    throw new Exception();
                found = true;
                ArrayList<RegExTree> subTrees = new ArrayList<>();
                subTrees.add(parse(content));
                result.add(new RegExTree(Operand.PROTECTION.getValue(), subTrees));
            } else {
                result.add(t);
            }
        }
        if (!found)
            throw new Exception();
        return result;
    }

    private static boolean containEtoile(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.root == Operand.ETOILE.getValue() && t.subTrees.isEmpty())
                return true;
        return false;
    }

    private static ArrayList<RegExTree> processEtoile(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<>();
        boolean found = false;
        for (RegExTree t : trees) {
            if (!found && t.root == Operand.ETOILE.getValue() && t.subTrees.isEmpty()) {
                if (result.isEmpty())
                    throw new Exception();
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<>();
                subTrees.add(last);
                result.add(new RegExTree(Operand.ETOILE.getValue(), subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private static boolean containConcat(ArrayList<RegExTree> trees) {
        boolean firstFound = false;
        for (RegExTree t : trees) {
            if (!firstFound && t.root != Operand.ALTERN.getValue()) {
                firstFound = true;
                continue;
            }
            if (firstFound) {
                if (t.root != Operand.ALTERN.getValue()) {
                    return true;
                }
                else {
                    firstFound = false;
                }
            }
        }
        return false;
    }

    private static ArrayList<RegExTree> processConcat(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<>();
        boolean found = false;
        boolean firstFound = false;
        for (RegExTree t : trees) {
            if (!found && !firstFound && t.root != Operand.ALTERN.getValue()) {
                firstFound = true;
                result.add(t);
            }
            else if (!found && firstFound && t.root == Operand.ALTERN.getValue()) {
                firstFound = false;
                result.add(t);
            }
            else if (!found && firstFound && t.root != Operand.ALTERN.getValue()) {
                found = true;
                RegExTree last = result.remove(result.size() - 1);
                ArrayList<RegExTree> subTrees = new ArrayList<>();
                subTrees.add(last);
                subTrees.add(t);
                result.add(new RegExTree(Operand.CONCAT.getValue(), subTrees));
            }
            else {
                result.add(t);
            }
        }
        return result;
    }

    private static boolean containAltern(ArrayList<RegExTree> trees) {
        for (RegExTree t : trees)
            if (t.root == Operand.ALTERN.getValue() && t.subTrees.isEmpty())
                return true;
        return false;
    }

    private static ArrayList<RegExTree> processAltern(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList<>();
        boolean found = false;
        RegExTree gauche = null;
        boolean done = false;
        for (RegExTree t : trees) {
            if (!found && t.root == Operand.ALTERN.getValue() && t.subTrees.isEmpty()) {
                if (result.isEmpty())
                    throw new Exception();
                found = true;
                gauche = result.remove(result.size() - 1);
                continue;
            }
            if (found && !done) {
                if (gauche == null)
                    throw new Exception();
                done = true;
                ArrayList<RegExTree> subTrees = new ArrayList<>();
                subTrees.add(gauche);
                subTrees.add(t);
                result.add(new RegExTree(Operand.ALTERN.getValue(), subTrees));
            } else {
                result.add(t);
            }
        }
        return result;
    }

    private static RegExTree removeProtection(RegExTree tree) throws Exception {
        if (tree.root == Operand.PROTECTION.getValue() && tree.subTrees.size() != 1)
            throw new Exception();
        if (tree.subTrees.isEmpty())
            return tree;
        if (tree.root == Operand.PROTECTION.getValue())
            return removeProtection(tree.subTrees.get(0));

        ArrayList<RegExTree> subTrees = new ArrayList<>();
        for (RegExTree t : tree.subTrees)
            subTrees.add(removeProtection(t));
        return new RegExTree(tree.root, subTrees);
    }

    // EXAMPLE
    // --> RegEx from Aho-Ullman book Chap.10 Example 10.25
    public static RegExTree exampleAhoUllman() {
        RegExTree a = new RegExTree((int) 'a', new ArrayList<>());
        RegExTree b = new RegExTree((int) 'b', new ArrayList<>());
        RegExTree c = new RegExTree((int) 'c', new ArrayList<>());
        ArrayList<RegExTree> subTrees = new ArrayList<>();
        subTrees.add(c);
        RegExTree cEtoile = new RegExTree(Operand.ETOILE.getValue(), subTrees);
        subTrees = new ArrayList<>();
        subTrees.add(b);
        subTrees.add(cEtoile);
        RegExTree dotBCEtoile = new RegExTree(Operand.CONCAT.getValue(), subTrees);
        subTrees = new ArrayList<>();
        subTrees.add(a);
        subTrees.add(dotBCEtoile);
        return new RegExTree(Operand.ALTERN.getValue(), subTrees);
    }

    public static ArrayList<String> alphabet(RegExTree origin, ArrayList<String> alphabet) {

        String letterToConsider = Character.toString((char) origin.root);
        if (origin.subTrees.isEmpty()) {
            if (!alphabet.contains(letterToConsider) && !letterToConsider.equals("+")) {
                alphabet.add(Character.toString((char) origin.root));
            }
            return alphabet;
        }

        if (origin.root >= 65 && origin.root <= 122 && !alphabet.contains(letterToConsider)) {
            alphabet.add(letterToConsider);
            for (RegExTree subtree : origin.subTrees) {
                alphabet.addAll(alphabet(subtree, alphabet));
            }
            return alphabet;
        }
        else {
            for (RegExTree subtree : origin.subTrees) {
                ArrayList<String> tmp = alphabet(subtree, alphabet);
                if (!alphabet.containsAll(tmp)) {
                    ArrayList<String> copy = new ArrayList<>(alphabet);
                    copy.retainAll(tmp);
                    alphabet.addAll(copy);
                }
            }
        }
        return alphabet;
    }

    public String toString() {
        return this.regexTree.toString();
    }
}

