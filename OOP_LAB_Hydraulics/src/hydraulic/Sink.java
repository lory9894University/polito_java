package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
	}
	/**
	 * override method, you can't connect a sink to anything else
	 */
	@Override 
	public void connect(Element elem){
		System.out.println("error, can't connect a sink to another piece");
	}
	@Override
	public void inFlow(double in) {
		this.flow[0]=in;
		this.flow[1]=0;
	}
	@Override
	public void notification(PrintingObserver obs) {
		obs.notifyFlow(this.getClass().getName(), this.getName(), this.getFlow()[0], obs.NO_FLOW);
	}
	@Override
	public boolean holeCheck(){return true;}
}
