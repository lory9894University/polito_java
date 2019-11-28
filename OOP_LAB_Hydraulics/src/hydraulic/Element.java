package hydraulic;

import java.util.Observer;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public abstract class Element {
	
	private String name;
	private Element connected;
	protected double flow[] = new double[2];
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	public Element(String name){
		this.name=name;
	}

	/**
	 * getter method
	 * @return the name of the element
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem){
		this.connected=elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		return connected;
	}
	/**
	 * getter metod for inner and outer flow
	 * @return array of double, the first one is the in flow, the second id the out flow
	 */
	public double[] getFlow() {
		return this.flow;
	}
	
	/**
	 * set the input flow and automaticaly calculate the out flow
	 * flow can be obtained via {@link #getFlow getFlow()}
	 * @param in a double that rappresent the input flow
	 **/
	public abstract void inFlow(double in);
	
	public void notification(PrintingObserver obs) { //TODO: si potrà fare un override rimuovendo l'ultima riga di questo metodo?
		obs.notifyFlow(this.getClass().getSimpleName(), this.getName(), this.getFlow()[0], this.getFlow()[1]);
		this.getOutput().inFlow(getFlow()[1]);
		this.getOutput().notification(obs);
	}
	/**
	 * check recursively if the system has a hole (a pipe not connected to a sink)
	 * @return true if is connected to anything
	 */
	public boolean holeCheck(){
		return getOutput() != null && this.getOutput().holeCheck();
	}
}
