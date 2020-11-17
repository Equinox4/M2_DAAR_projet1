package app;

import java.util.ArrayList;
import java.util.List;

public class State {
    private int id;
    private ArrayList<Transition> transitions; // quelles sont les transitions qui partent de cet etat
    private boolean isFinal;

    public State() {
        this.id = 0;
        this.isFinal = true;
        this.transitions = new ArrayList<>();
    }

    public State(int id) {
        this();
        this.id = id;
    }

    public State(State s) {
        this(s.id);
        for (Transition t : s.getTransitions()) {
            this.transitions.add(new Transition(t));
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public void addTransition (Transition t) {
        this.transitions.add(t);
        this.isFinal = false;
    }

    public List<Transition> getTransitions() {
        return this.transitions;
    }

    public String toString() {
        return String.valueOf(this.id);
    }
}
