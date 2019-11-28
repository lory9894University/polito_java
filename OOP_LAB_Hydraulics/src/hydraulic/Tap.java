package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	
	private boolean open;
	public Tap(String name) {
		super(name);
	}
	/**
	 * set the tap to open/close
	 * @param open true if the tap is open
	 */
	public void setOpen(boolean open){
		this.open=open;
	}
	/**
	 * getter for the variable open
	 * @return true if the tap is open
	 */
	public boolean getOpen() {
		return this.open;
	}
	@Override
	public void inFlow(double in) {
		this.flow[0]=in;
		if (open)
			this.flow[1]=in;
		else
			this.flow[1]=0;
	}
	public void notification(PrintingObserver obs) {
		obs.notifyFlow(this.getClass().getName(), this.getName(), this.getFlow()[0], this.open ? this.getFlow()[1] : obs.NO_FLOW);
		this.getOutput().inFlow(getFlow()[1]);
		this.getOutput().notification(obs);
	}
	@Override
	public boolean holeCheck(){
		return  open ? super.holeCheck() : true;
	}
}
