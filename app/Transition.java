package app;

public class Transition {

    private String label;
    private State next; // vers quel etat cette transition pointe

    public Transition(String label, State next) {
        this.label = label;
        this.next = next;
    }

    public Transition(Transition t) {
        this(t.label, new State(t.next));
    }

    public String getLabel() {
        return this.label;
    }

    public State getNextState() {
        return this.next;
    }

    public void setNextState(State s) {
        this.next = s;
    }

    public String toString() {
        return String.format(" -%s-> ", this.label);
    }
}
