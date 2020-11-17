package app;

public class Automaton {
    private int stateNumber;
    private State sInitial;

    public Automaton() {
        this.stateNumber = 0;
        this.sInitial = new State(this.stateNumber);
    }

    public Automaton(Automaton a) {
        this.sInitial = a.getInitialState();
        this.stateNumber = a.getStateNumber();
    }

    public State makeState() {
        this.stateNumber++;
        return new State(this.stateNumber);
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

    public int getStateNumber() {
        return this.stateNumber;
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
