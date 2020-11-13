package app;

public class Automata {
    private State sInitial;

    public Automata(State sInitial) {
        this.sInitial = sInitial;
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
}
