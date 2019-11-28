package it.polito.oop.milliways;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Party {

	private HashMap <Race,Integer> companions = new HashMap<>();
	private int totCompanions;
	
	public Party() {
	}

    public void addCompanions(Race race, int num) {
    	if (companions.containsKey(race))
    		companions.put(race,companions.get(race)+num);
    	companions.put(race, num);
    	totCompanions+=num;
	}

	public int getNum() {
        return totCompanions;
	}

	public int getNum(Race race) {
	    return companions.get(race);
	}

	public List<String> getRequirements() {
		List<String>collectiveReq=new LinkedList<>();
		companions.keySet().stream().map(x->x.getRequirements()).forEach(x->x.forEach(y-> collectiveReq.add(y)));
        return collectiveReq.stream().distinct().sorted().collect(Collectors.toList());
        
	}

    public Map<String,Integer> getDescription(){
            return companions.entrySet().stream().collect(Collectors.toMap(x->x.getKey().getName(), x->x.getValue()));
    }

	public HashMap<Race, Integer> getCompanions() {
		return companions;
	}

	public void setCompanions(HashMap<Race, Integer> companions) {
		this.companions = companions;
	}

	public int getTotCompanions() {
		return totCompanions;
	}

	public void setTotCompanions(int totCompanions) {
		this.totCompanions = totCompanions;
	}
    

}
