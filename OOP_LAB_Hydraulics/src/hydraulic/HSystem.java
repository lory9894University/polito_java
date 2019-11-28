package hydraulic;

import java.util.ArrayList;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	private ArrayList<Element> elements = new ArrayList<Element>();
	private StringBuffer layout = new StringBuffer(); //OMMIODIO UNA VARIABILE GLOBALE
	private boolean systemChecked = false;
	/**
	 * Adds a new element to the system
	 * @param elem the elements that need to be added
	 */
	public void addElement(Element elem){
		this.systemChecked =false;
		elements.add(elem);
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to the number of added elements
	 */
	public Element[] getElements(){
		if (elements.isEmpty()) {
			System.out.println("error, no elements added\n");
			return new Element[0];
		}
	Element [] returnArray = new Element[elements.size()];
	returnArray = elements.toArray(returnArray);
		return returnArray;
	}
	
	private boolean noSplit=true;
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		System.out.println("system layout:");
		if (elements.isEmpty()) 
			System.out.println("error, no elements");
		for(Element element:elements) {
			if(element instanceof Source) {
				noSplit=true;
				printFrom(element);
				layout.append("\n");
			}
		}
		return layout.toString();
	}
	/**
	 * funzione ricorsiva che se incontra uno split si richiama sull'altra uscita, altrimenti prosegue la catena
	 */
	private void printFrom(Element elem) {
		if(!systemIsFine()) return;
		this.layout.append("["+elem.getName()+"]"+elem.getClass().getSimpleName());
		if (elem instanceof Sink) {
			this.layout.append(" |");
			return;
		}else if(elem instanceof Split){
			Split split = (Split) elem;
			int spaces;
			if(noSplit) {
				spaces = layout.length();
			} else { 
				int lineindex = layout.lastIndexOf("\n");
				spaces = layout.substring(lineindex).length();
			}
			layout.append(" +-> ");
			printFrom(split.getOutputs()[0]);
			layout.append("\n ");
			noSplit = false;
			for(int i=0; i<spaces; i++) {
				layout.append(" ");
			}
			layout.append("|");
			layout.append("\n");
			for(int i=0; i<spaces; i++) {
				layout.append(" ");
			}
			layout.append(" +-> ");
			printFrom(split.getOutputs()[1]);
		}else {
			this.layout.append(" -> ");
			printFrom(elem.getOutput());
		}
	}
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		if(!systemIsFine()) return;
		for(Element element : elements) {
			if(element instanceof Source) element.notification((PrintingObserver) observer);
		}
		System.out.println();
	}
	private boolean systemIsFine(){
		if (systemChecked) {
			return true;
		}
		systemChecked = true;
		for (Element element:elements) {
			if (element instanceof Source){
				if(element.holeCheck()){
					systemChecked = true;
					return true;
				}
				else{
					System.out.println("the system has to end with a sink or a closed Tap");
					return false;
				}
			}
		}
		return true;
	}

}
