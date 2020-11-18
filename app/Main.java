package app;

public class Main {
    public static void main(String[] arg) {
        /*
        RegEx test = new RegEx("S(a|r|g)+on");
        System.out.println(test);
        */

        /*
        RegEx test1 = new RegEx("a|b(c*)");
        System.out.println(test1);

        AutomataBuilder ab = new AutomataBuilder();
        ab.parseRegExTree(test1.getRegExTree());
        System.out.println(ab);
        System.out.println(ab.getAutomatons().size());
*/
        RegExTree r1 = new RegExTree(98, null);
        RegExTree r2 = new RegExTree(97, null);
        AutomatonBuilder ab = new AutomatonBuilder();
        ab.unionAutomaton(r1, r2);
        //RegEx.exampleAhoUllman();

/*
        Automata a1 = new Automata();
        
        a1.parseRegExtree(test1.getRegExTree());
        System.out.println(a1);
*/
    }
}

/* HashMap<Integer><HashMap<String><Integer>> */