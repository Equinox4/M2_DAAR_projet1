package app;

public class Automata {
    private State sInitial;
//  private HashMap<State, HashMap<String, Transition>> transitions; // experimental
    // y a peut etre moyen d'utiliser un TreeSet<State>

/*
    public Automata() {
        this.transitions = new HashMap<>();
    }
*/
    public Automata(State sInitial) {
        this.sInitial = sInitial;
    }

    public Automata(Automata a) {
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
        if (s.getTransitions().isEmpty()) {
            return s;
        }
        for (Transition t : s.getTransitions()) {
            this.getFinalState(t.next);
        }
        return null;
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
    public State getInitialState() {
        ArrayList<State> states = new ArrayList<>();
        states.addAll(this.transitions.keySet());
        ArrayList<State> statesIn = new ArrayList<>();
        Collection<HashMap<String, Transition>> transitionsColl1 = this.transitions.values();
        for (HashMap<String, Transition> st : transitionsColl1) {
            Collection<Transition> transitionsColl2 = st.values();
            for (Transition t : transitionsColl2) {
                statesIn.add(t.getNextState());
            }
        }
    }
*/
/*
    public State getFinalState() {
        for (Map.Entry<State, HashMap<String, Transition>> m : this.transitions.entrySet()) {
            if (m.getValue().isEmpty()) {
                return m.getKey();
            }
        }
        return null;
    }
*/
/*
    public int getNbState() {
        return this.transitions.keySet().size();
    }
*/
/*
    // Experimental
    public HashMap<State, HashMap<String, Transition>> getTransitions() {
        return this.transitions;
    }
*/
}
