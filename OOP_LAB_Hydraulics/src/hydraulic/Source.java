package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {
	
	public Source(String name) {
		super(name);
	}

	public void setFlow(double flow){
		this.inFlow(flow);
	}
	@Override
	public void inFlow(double in) {
		this.flow[0]=0;
		this.flow[1]=in;
	}
	@Override
	public void notification(PrintingObserver obs) {
		obs.notifyFlow(this.getClass().getSimpleName(), this.getName(), obs.NO_FLOW, this.getFlow()[1]);
		this.getOutput().inFlow(getFlow()[1]);
		this.getOutput().notification(obs);
	}
}
