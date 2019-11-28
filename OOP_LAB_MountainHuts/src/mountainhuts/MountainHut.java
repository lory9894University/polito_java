package mountainhuts;

import java.util.Optional;

public class MountainHut {
	
	private String name,category;
	private int bedsNumber;
	private Municipality municipality;
	private Integer altitude;
	
	public MountainHut(String name, String category, int altitude, int bedsNumber,
			Municipality municipality) {
		this.name = name;
		this.category = category;
		this.altitude = altitude;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
	}
	
	public MountainHut(String name, String category, int bedsNumber,
			Municipality municipality) {
		this.name = name;
		this.category = category;
		this.altitude = null;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
	}
	@Override
	public String toString() {
		return name;
	}
	public String getName() {
		return name;
	}

	public Optional<Integer> getAltitude() {
		return Optional.ofNullable(altitude);
	}
	public int getAltitudeReal() {
		if(this.getAltitude().isPresent())  
			return this.getAltitude().get().intValue(); 
		else 
			return this.getMunicipality().getAltitude();
	}

	public String getCategory() {
		return category;
	}

	public Integer getBedsNumber() {
		return bedsNumber;
	}

	public Municipality getMunicipality() {
		return municipality;
	}

}
