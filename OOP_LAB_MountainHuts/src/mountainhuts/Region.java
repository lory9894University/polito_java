package mountainhuts;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;

import sun.invoke.util.BytecodeName;

public class Region {
	
	private ArrayList<Integer> rangeUp = new ArrayList<Integer>(),rangeLw =new ArrayList<Integer>();
	private String name;
	HashMap<String,Municipality> municipalities = new HashMap <>();
	HashMap<String,MountainHut> huts = new HashMap <>();


	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name=name;

	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String range:ranges) {
			String altitudes[]=range.split("-");
			rangeLw.add(Integer.parseInt(altitudes[0]));
			rangeUp.add(Integer.parseInt(altitudes[1]));
		}

	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for (int i=0 ; i<rangeUp.size();i++) {
			if(altitude >= rangeLw.get(i) && altitude <= rangeUp.get(i))return rangeLw.get(i).toString() + "-" + rangeUp.get(i).toString();
		}
		return "0-INF";
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipalities.values();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return huts.values();
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		Municipality mun;
		if((mun = municipalities.get(name))==null) {
			mun = new Municipality(name, province, altitude);
			municipalities.put(name, mun);
		}
		return mun;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHut hut;
		if ((hut=huts.get(name))==null) {
			hut = new MountainHut(name, category, bedsNumber, municipality);
			huts.put(name, hut);
		}
		return hut;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		MountainHut hut;
		if ((hut=huts.get(name))==null) {
			hut = new MountainHut(name,category, altitude, bedsNumber, municipality);
			huts.put(name, hut);
		}
		return hut;
		}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region region= new Region(name);
		region.readData(file);
		return region;
	}

	@SuppressWarnings("unused")
	private void readData(String file) {
		List<String> lines = null;

		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			lines = in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		if (lines == null)
			return;
		lines.remove(0);
		lines.forEach(line ->{
			String sub[]= line.split(";");
			String province=sub[0],municipality=sub[1],name=sub[3],category=sub[5];
			int municipalityAltitude=Integer.parseInt(sub[2]),altitude,bedsNumber=Integer.parseInt(sub[6]);
			Municipality mun= this.createOrGetMunicipality(municipality, province, municipalityAltitude);
			if(sub[4].compareTo("")!=0) {
				altitude = Integer.parseInt(sub[4]);
				createOrGetMountainHut(name,altitude, category, bedsNumber, mun);
			}
			else 
				createOrGetMountainHut(name, category, bedsNumber, mun);
		});
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return municipalities.values().stream().collect(Collectors.groupingBy(Municipality::getProvince,Collectors.counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return huts.values().stream().map(x->x.getMunicipality()).collect(Collectors.groupingBy(Municipality::getProvince,Collectors.groupingBy(Municipality::getName,Collectors.counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		Region region= this;
		Map<String, Long> altitude = this.getMountainHuts().stream().map( (MountainHut x) -> {return x.getAltitudeReal();})
				 .collect(Collectors.groupingBy((x)->{return region.getAltitudeRange(x);},Collectors.counting()));
		 return altitude;
	}
	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return getMountainHuts().stream().collect(groupingBy((MountainHut x)->{return x.getMunicipality().getProvince();},Collectors.summingInt(MountainHut::getBedsNumber))); 
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Region region=this;
		Map<String, Optional<Integer>> streamBeds = new HashMap<>();
		getMountainHuts().stream().collect(groupingBy(x -> region.getAltitudeRange(x.getAltitudeReal()),maxBy( (MountainHut o1,MountainHut o2) ->  o1.getBedsNumber() - o2.getBedsNumber())))
		.forEach((x,y) -> streamBeds.put(x, Optional.ofNullable(y.get().getBedsNumber())));
//		getMountainHuts().stream().collect(groupingBy(x -> region.getAltitudeRange(x.getAltitudeReal()),maxBy( (MountainHut o1,MountainHut o2) ->  o1.getBedsNumber() - o2.getBedsNumber())))
//		.entrySet().stream().map((x) -> x.setValue(x.getValue().get().getBedsNumber())).forEach(System.out::println);
		return streamBeds;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return this.getMountainHuts().stream().collect(Collectors.groupingBy((MountainHut mountainHut) -> {
            return mountainHut.getMunicipality().getName();
        }, Collectors.counting())).entrySet().stream().collect(Collectors.groupingBy((Map.Entry<String, Long> entry) -> {
            return entry.getValue();
        }, Collectors.mapping((Map.Entry<String, Long> entry) -> {
            return entry.getKey();
        }, Collectors.toList())));
	}

}
