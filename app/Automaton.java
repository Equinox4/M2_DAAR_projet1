package app;

import java.util.concurrent.atomic.AtomicInteger;

public class Automaton {
    private static final AtomicInteger stateNumber = new AtomicInteger(0); 
    private final int id;
    private State sInitial;

    public Automaton() {
        this.id = Automaton.stateNumber.getAndIncrement();
        this.sInitial = new State(this.id);
    }

    public Automaton(Automaton a) {
        this.sInitial = a.getInitialState();
        this.id = a.id;
    }

    public State getInitialState() {
        return this.sInitial;
    }

    public State getFinalState() {
        return this.getFinalState(this.sInitial);
    }

    private State getFinalState(State s) {
        for (Transition t : s.getTransitions()) {
            this.getFinalState(t.next);
        }
        return s;
    }

    public int getCurrentid() {
        return this.id;
    }

    public String toString() {
        State current = this.sInitial;
        String res = current.toString();

        for (Transition t : current.getTransitions()) {
            res += t.toString();
            res += t.next.toString();
            current = t.next;
        }
        return res;
    }
/*
    public int getNbState() {
        return this.transitions.keySet().size();
    }
*/
}
