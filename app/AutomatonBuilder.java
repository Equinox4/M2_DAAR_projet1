package app;

import java.util.ArrayList;
import java.util.List;

public class AutomatonBuilder {
    private ArrayList<Automaton> automatons;
    private int currentState;
    private static final String EPSILON = "epsilon";
    private boolean concat = false;
    private boolean altern = false;
    private boolean closure = false;

    public AutomatonBuilder() {
        this.automatons = new ArrayList<>();
        this.currentState = 0;
    }

    public List<Automaton> getAutomatons() {
        return this.automatons;
    }

    public Automaton makeXAutomaton(RegExTree ret) {
        Automaton a = new Automaton();
        State fs = a.makeState();
        Transition t = new Transition(ret.getRootString(), fs);

        a.getInitialState().addTransition(t);

        return a;
    }

    public String toString() {
        return this.automatons.toString();
    }
}
