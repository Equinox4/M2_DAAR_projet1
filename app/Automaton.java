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

    public void setInitialState(State s) {
        this.sInitial = s;
        this.numberize();
    }

    public void fusionInitialState(State s) {
        this.sInitial.getTransitions().addAll(s.getTransitions());
        this.numberize();
    }

    public void numberize() {
        this.stateNumber = 0;
        State current = this.sInitial;
        for (int i = 0; i < current.getTransitions().size(); i++) {
            Transition t = current.getTransitions().get(i);
            t.getNextState().setId(this.stateNumber);
            this.stateNumber++;
            if (i == current.getTransitions().size() - 1) {
                current = t.getNextState();
            }
        }
    }

    public State getFinalState() {
        State current = this.sInitial;
        for (int i = 0; i < current.getTransitions().size(); i++) {
            Transition t = current.getTransitions().get(i);
            if (t.getNextState().isFinal()) {
                return t.getNextState();
            }
            if (i == current.getTransitions().size() - 1) {
                current = t.getNextState();
            }
        }

        return null;
    }

    public int getStateNumber() {
        return this.stateNumber;
    }

    // TODO: faire une fonction pour re-numéroter les états

    public String toString() {
        State current = this.sInitial;
        String res = "";

        for (int i = 0; i < current.getTransitions().size(); i++) {
            Transition t = current.getTransitions().get(i);
            res += current.toString();
            res += t.toString();
            res += t.getNextState().toString();
            res += " ";
            if (i == current.getTransitions().size() - 1) {
                current = t.getNextState();
            }
        }

        return res;
    }
}
