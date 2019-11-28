package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {
    
    /**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic };
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed }
    
    public int getId(){
        return -1;
    }

    public String getDescription(){
        return null;
    }
    
    public Severity getSeverity() {
        return null;
    }

    public String getAuthor(){
        return null;
    }
    
    public String getComponent(){
        return null;
    }
    
    public State getState(){
        return null;
    }
    
    public String getSolutionDescription() throws TicketException {
        return null;
    }
}
