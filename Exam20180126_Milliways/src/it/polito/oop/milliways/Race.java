package it.polito.oop.milliways;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Race {
	
	private HashMap<String , Attribute> requirements=new HashMap<>();
	private String name;
    
	public Race(String name) {
		super();
		this.name = name;
	}

	public void addRequirement(String requirement) throws MilliwaysException {
		if (requirements.containsKey(requirement))throw new MilliwaysException(); 
		requirements.put(requirement, new Attribute(requirement));
	}
	
	public List<String> getRequirements() {
        return requirements.values().stream().map(Attribute::getName).sorted().collect(Collectors.toList());
	}
	
	public String getName() {
        return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
