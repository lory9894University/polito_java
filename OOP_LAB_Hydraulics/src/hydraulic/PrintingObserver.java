package hydraulic;

import static hydraulic.SimulationObserver.exists;

/**
 * Sample implementation of {@link SimulationObserver}.
 * This simulation observer simply prints out the notification info
 * and keeps count of how many notification it receive.
 * 
 * This class can be used for debugging purposes.
 *  
 */
public class PrintingObserver implements SimulationObserver {
	private int countNotifications = 0;
	
	@Override
	public void notifyFlow(String type, String name, double inFlow, double outFlow) {
		if(exists(inFlow)) System.out.print(inFlow + " -> ");
		System.out.print("[" + type + "] " + name + " ");
		if(exists(outFlow)) System.out.print("-> " + outFlow);
		System.out.print("\n");
		countNotifications++;
	}
	
	public int getCount() {
		return countNotifications;
	}
}
