package app;

import java.util.ArrayList;
import java.util.List;

public class State {
    private int id;
    private ArrayList<Transition> transitions; // quelles sont les transitions qui partent de cet etat
    private boolean isFinal = true;

    public State() {
        this.transitions = new ArrayList<>();
    }
// TODO: gérer correctement les ids
    public State(int id) {
        this();
        this.id = id;
    }

    public State(State s) {
        this(s.id);
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
