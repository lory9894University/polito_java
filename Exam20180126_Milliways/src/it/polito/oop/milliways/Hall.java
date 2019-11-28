package it.polito.oop.milliways;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Hall {
	
	private HashMap<String, Attribute> facilities = new HashMap<>();
	private int id;
	private HashMap<String,Party> occupator= new HashMap<>();

	public Hall(int id) {
		super();
		this.id = id;
	}
	
	public HashMap<String,Party> getOccupied() {
		return occupator;
	}

	public void setOccupied(Party occupied) {
		this.occupator.put(occupied.toString(), occupied);
	}

	public int getId() {
		return id;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if (facilities.containsKey(facility))throw new MilliwaysException(); 
		facilities.put(facility, new Attribute(facility));
	}

	public List<String> getFacilities() {
        return facilities.values().stream().map(Attribute::getName).sorted().collect(Collectors.toList());
	}
	
	int getNumFacilities(){
        return facilities.size();
	}

	public boolean isSuitable(Party party) {;
		boolean suitable =false;
		for (String attribute : party.getRequirements()) {
			suitable=false;
			for (Attribute facility : facilities.values()) {
				if(facility.getName().equals(attribute))suitable=true;
			}
			if(!suitable)return false;
		}
		return true;
	}
}
