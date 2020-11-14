package app;

public class Main {
    public static void main(String[] arg) {
        /*
        RegEx test = new RegEx("S(a|r|g)+on");
        System.out.println(test);
        */

        RegEx test1 = new RegEx("a|b(c*)");
        System.out.println(test1);

        AutomataBuilder ab = new AutomataBuilder();
        ab.parseRegExTree(test1.getRegExTree());
        System.out.println(ab);
        

        //RegEx.exampleAhoUllman();

/*
        Automata a1 = new Automata();
        
        a1.parseRegExtree(test1.getRegExTree());
        System.out.println(a1);
*/
    }
}

/* HashMap<Integer><HashMap<String><Integer>> */