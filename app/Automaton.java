package app;

public class Automaton {
    private State sInitial;

    public Automaton(State sInitial) {
        this.sInitial = sInitial;
    }

    public Automaton(Automaton a) {
        this(new State(a.getInitialState()));
    }

    public void setInitialState(State s) {
        this.sInitial = s;
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
