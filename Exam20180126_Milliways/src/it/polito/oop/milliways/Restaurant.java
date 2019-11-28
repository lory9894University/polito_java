package it.polito.oop.milliways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Restaurant {
	
	private HashMap<String, Race> races = new HashMap<>();
	private List<Party> parties=new ArrayList<Party>();
	private LinkedHashMap<Integer, Hall> halls = new LinkedHashMap<>();

    public Restaurant() {
    	
	}
	
	public Race defineRace(String name) throws MilliwaysException{
		if(races.containsKey(name)) throw new MilliwaysException();
		Race race = new Race(name);
		races.put(name, race);
	    return race;
	}
	
	public Party createParty() {
		Party party= new Party();
		parties.add(party);
	    return party;
	}
	
	public Hall defineHall(int id) throws MilliwaysException{
		if(halls.containsKey(id)) throw new MilliwaysException();
		Hall hall = new Hall(id);
		halls.put(id, hall);
	    return hall;
	}

	public List<Hall> getHallList() {
		return halls.values().stream().collect(Collectors.toList());
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
		if(hall.isSuitable(party))
			hall.setOccupied(party);
		else
			throw new MilliwaysException();
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
        Optional<Hall> possibleHall = halls.values().stream().filter(x -> x.isSuitable(party)).findFirst();
        if (!possibleHall.isPresent())throw new MilliwaysException();
        possibleHall.get().setOccupied(party);
        return possibleHall.get();
	}

	public Map<Race, Integer> statComposition() {
        return halls.values().stream().filter(x->x.getOccupied()!=null).flatMap(x->x.getOccupied().values().stream()).flatMap(x->x.getCompanions().entrySet().stream()).
                collect(Collectors.groupingBy(x->x.getKey(),Collectors.summingInt(x->x.getValue())));
	}

	public List<String> statFacility() {
		return halls.values().stream().flatMap(x->x.getFacilities().stream()).collect(Collectors.groupingBy(x->x,Collectors.counting())).entrySet()
        		.stream().sorted((x,y)->x.getKey().compareTo(y.getKey())).sorted((x,y)->-x.getValue().compareTo(y.getValue())).collect(Collectors.mapping(x->x.getKey(), Collectors.toList()));
	}
	
	public Map<Integer,List<Integer>> statHalls() {
        return halls.values().stream().collect(Collectors.groupingBy(x->x.getFacilities().size(),Collectors.mapping(x->x.getId(), Collectors.toList()))).entrySet().stream().sorted((x,y)->x.getKey().compareTo(y.getKey()))
        		.map(x->{x.getValue().sort((y,z)->y.compareTo(z)); return x;}).collect(Collectors.toMap(x->x.getKey(), x->x.getValue()));
	}

}
