package app;

import java.util.ArrayList;
import java.util.List;

public class AutomatonBuilder {
    private ArrayList<Automaton> automatons;
    private static final String EPSILON = "Ɛ";
    private boolean concat = false;
    private boolean altern = false;
    private boolean closure = false;

    public AutomatonBuilder() {
        this.automatons = new ArrayList<>();
    }

    public List<Automaton> getAutomatons() {
        return this.automatons;
    }

    public void parseRegExTree(RegExTree ret) {
        if (ret.subTrees.isEmpty()) {
            String rootValue = Character.toString((char) ret.root);
            State sInitial = new State();
            State sFinal = new State();

            Transition t = new Transition(rootValue, sFinal);
            sInitial.addTransition(t);

            Automaton a = new Automaton();
            a.setInitialState(sInitial);
            this.automatons.add(a);
        }
        else {
            for (RegExTree t : ret.subTrees) {
                if (this.concat) {
                    Automaton a = this.concatenationAutomaton(t.subTrees.get(0), t.subTrees.get(1));
                    this.automatons.add(a);
                }
                if (ret.root == Operand.CONCAT.getValue() && !concat) {
                    this.concat = true;
                }
                if (this.altern) {
                    Automaton a = this.unionAutomaton(t.subTrees.get(0), t.subTrees.get(1));
                    this.automatons.add(a);
                }
                if (ret.root == Operand.ALTERN.getValue() && !altern) {
                    this.altern = true;
                }
                if (this.closure) {
                    Automaton a = this.closureAutomaton(t.subTrees.get(0), t.subTrees.get(1));
                    this.automatons.add(a);
                }
                if (ret.root == Operand.ETOILE.getValue() && !closure) {
                    this.closure = true;
                }
                this.parseRegExTree(t);
            }
        }
    }

    public Automaton unionAutomaton(RegExTree r1, RegExTree r2) {
        Automaton eps1 = this.makeEpsilonAutomaton();
        Automaton eps2 = this.makeEpsilonAutomaton();
        State finalState = new State();
        Transition t1 = new Transition(AutomatonBuilder.EPSILON, finalState);
        Transition t2 = new Transition(AutomatonBuilder.EPSILON, finalState);
        Automaton a1 = this.makeXAutomaton(r1);
        Automaton a2 = this.makeXAutomaton(r2);

        eps1.setInitialState(eps2.getInitialState());
        a1.setInitialState(eps1.getFinalState());
        a2.setInitialState(eps2.getFinalState());
        a1.getFinalState().addTransition(t1);
        a2.getFinalState().addTransition(t2);

        return eps1;
    }

    public Automaton concatenationAutomaton(RegExTree r1, RegExTree r2) {
        Automaton a1 = this.makeXAutomaton(r1);
        Automaton a2 = this.makeXAutomaton(r2);
        Automaton eps = this.makeEpsilonAutomaton();

        Transition t = new Transition(EPSILON, eps.getInitialState());
        a1.getFinalState().addTransition(t);
        a2.setInitialState(eps.getFinalState());

        return a1;
    }

    public Automaton closureAutomaton(RegExTree r) {
        Automaton a = this.makeXAutomaton(r);
        Automaton eps1 = this.makeEpsilonAutomaton();
        Automaton eps2 = this.makeEpsilonAutomaton();

        eps2.setInitialState(eps2.getInitialState());
        a.setInitialState(eps1.getFinalState());
        State finalState = new State();
        Transition t1 = new Transition(AutomatonBuilder.EPSILON, finalState);
        a.getFinalState().addTransition(t1);
        Transition t2 = new Transition(AutomatonBuilder.EPSILON, a.getInitialState());
        a.getFinalState().addTransition(t2);

        return a;
    }

    public Automaton makeXAutomaton(RegExTree ret) {
        Automaton a = new Automaton();
        State fs = a.makeState();
        Transition t = new Transition(ret.getRootString(), fs);

        a.getInitialState().addTransition(t);

        return a;
    }

    public Automaton makeEpsilonAutomaton() {
        Automaton a = new Automaton();

        State fs = a.makeState();
        Transition t = new Transition(AutomatonBuilder.EPSILON, fs);

        a.getInitialState().addTransition(t);

        return a;
    }

    public Automaton makeEmptyAutomaton() {
        return new Automaton();
    }

    public String toString() {
        return this.automatons.toString();
    }
}
