package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	private Element elements[] = new Element[2];

	/**
	 * Constructor
	 *
	 * @param name
	 */
	public Split(String name) {
		super(name);
	}

	/**
	 * returns the downstream elements
	 *
	 * @return array containing the two downstream element
	 */
	public Element[] getOutputs() { return elements; }

	public void connect(Element elem, int numOutput) { elements[numOutput] = elem; }
	@Override
	public Element getOutput(){
		System.out.println("Attention, this method is not correct for elements of type Split");
		return new Split("error");
	}
	@Override
	public void inFlow(double in) {
		this.flow[0] = in;
		this.flow[1] = in / 2;
	}

	@Override
	public void notification(PrintingObserver obs) {
		obs.notifyFlow(this.getClass().getSimpleName(), this.getName(), this.getFlow()[0], this.getFlow()[1]);
		this.getOutputs()[0].inFlow(getFlow()[1]);
		this.getOutputs()[1].inFlow(getFlow()[1]);
		this.getOutputs()[0].notification(obs);
		this.getOutputs()[1].notification(obs);
	}
	@Override
	public boolean holeCheck(){
		return (this.getOutputs()[0] != null && this.getOutputs()[0].holeCheck()) && (this.getOutputs()[1] != null && this.getOutputs()[1].holeCheck());
	}
}
