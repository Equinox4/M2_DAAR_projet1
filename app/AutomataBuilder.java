package app;

import java.util.ArrayList;

public class AutomataBuilder {
    private ArrayList<Automata> automatons;
    private int currentState;

    public AutomataBuilder() {
        this.automatons = new ArrayList<>();
        this.currentState = 0;
    }

    public void parseRegExTree(RegExTree ret) {
        if (ret.subTrees.isEmpty()) {
            String rootValue = Character.toString((char) ret.root);

            State sInitial = new State(this.currentState);
            this.currentState++;

            State sFinal = new State(this.currentState);
            sFinal.setFinal(true);
            Transition t = new Transition(rootValue, sFinal);
            sInitial.addTransition(t);

            Automata a = new Automata(sInitial);
            this.automatons.add(a);
        } 
        else {
            /*
            if (ret.root == Operand.CONCAT.getValue()) {
                ArrayList<Automata> automata_to_link = new ArrayList<>();
                for (RegExTree t : ret.subTrees) {
                    automata_to_link.add(this.parseRegExTree(t));
                }
                State sInitial = new State(this.currentState);
                currentState++;
                Transition t = new Transition("ε", automata_to_link.get(0));
                sInitial.addTransition(t);
                State sFinal = new State(this.currentState);
            }
        
            for (RegExTree t : ret.subTrees) {
                this.parseRegExTree(t);
            }
            */
        }
    }

    /*
    public void parseRegExTree(RegExTree ret) {
        Automata a = new Automata();
        String rootValue = Character.toString((char) ret.root);
        if (ret.subTrees.isEmpty()) {
            ArrayList<Transition> retTransitions = new ArrayList<>();
            Transition t = new Transition(rootValue);
            t.addState(1);
            retTransitions.add(t);

            a.addState(retTransitions);
            a.addState(null);

            this.automatons.add(a);
        } 
        else {
            for (RegExTree t : ret.subTrees) {
                parseRegExTree(t);
            }
        }
    }
    */

    public String toString() {
        return this.automatons.toString();
    }
}
