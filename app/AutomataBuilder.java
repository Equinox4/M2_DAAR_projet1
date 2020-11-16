package app;

import java.util.ArrayList;

public class AutomataBuilder {
    private ArrayList<Automata> automatons;
    private int currentState;
    private static final String EPSILON = "epsilon";
    private boolean shouldConcat = false;

    public AutomataBuilder() {
        this.automatons = new ArrayList<>();
        this.currentState = 0;
    }

    public ArrayList<Automata> getAutomatons() {
        return this.automatons;
    }

    // la methode finale doit retourner un automate de type NDFA
    // ensuite on passera cet automate dans une methode "determinize"
    // genre: a11 = a1.determinize() 
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
            for (RegExTree t : ret.subTrees) {
                if (shouldConcat) {
                    Automata left = this.automatons.get(this.automatons.size() - 1);
                    this.automatons.remove(this.automatons.size() - 1);
                    Automata right = this.automatons.get(this.automatons.size() - 1);
                    this.automatons.remove(this.automatons.size() - 1);

                    this.automatons.add(this.concatenationAutomata(left, right));
                }
                if (ret.root == Operand.CONCAT.getValue() && !shouldConcat) {
                    this.shouldConcat = true;
                }

                if (ret.root == Operand.ALTERN.getValue()) {

                }
                if (ret.root == Operand.ETOILE.getValue()) {

                }

                this.parseRegExTree(t);
            }
        }
    }

/*
    public RegExTree parseRegExTreeRec(RegExTree ret) {
        if (ret.root == Operand.CONCAT.getValue()) {
            ArrayList<Automata> automataToLink = new ArrayList<>();
            for (RegExTree t : ret.subTrees) {
                automataToLink.add(this.parseRegExTreeRec(t));
            }
            State sInitial = new State(this.currentState);
            currentState++;
            Transition t = new Transition("ε", automataToLink.get(0));
            sInitial.addTransition(t);
            State sFinal = new State(this.currentState);
        } 
    }
*/

    public Automata unionAutomata(Automata a1, Automata a2) {
        Automata a1Copy = new Automata(a1);
        Automata a2Copy = new Automata(a2);

        State unionInitialState = new State();
        State unionFinalState = new State();

        Transition t1 = new Transition(EPSILON, a1Copy.getInitialState());
        Transition t2 = new Transition(EPSILON, a2Copy.getInitialState());
        Transition t3 = new Transition(EPSILON, unionFinalState);
        Transition t4 = new Transition(EPSILON, unionFinalState);

        unionInitialState.addTransition(t1);
        unionInitialState.addTransition(t2);
        a1Copy.getFinalState().addTransition(t3);
        a2Copy.getFinalState().addTransition(t4);

        return new Automata(unionInitialState);
    }

    public Automata concatenationAutomata(Automata a1, Automata a2) {
        Automata a1Copy = new Automata(a1);
        Automata a2Copy = new Automata(a2);

        State currentFinalState = a1Copy.getFinalState();
        Transition t = new Transition(EPSILON, a2Copy.getInitialState());

        currentFinalState.addTransition(t);

        return a1Copy;
    }

    public Automata closureAutomata(Automata a1) {
        Automata a1Copy = new Automata(a1);

        State currentFinalState = a1Copy.getFinalState();
        State closureInitialState = new State();
        State closureFinalState = new State();

        Transition t1 = new Transition(EPSILON, a1Copy.getInitialState());
        Transition t2 = new Transition(EPSILON, a1Copy.getInitialState());
        Transition t3 = new Transition(EPSILON, closureFinalState);
        Transition t4 = new Transition(EPSILON, closureFinalState);

        closureInitialState.addTransition(t1);
        closureInitialState.addTransition(t4);
        currentFinalState.addTransition(t2);
        currentFinalState.addTransition(t3);

        return new Automata(closureInitialState);
    }

    public String toString() {
        return this.automatons.toString();
    }
}
