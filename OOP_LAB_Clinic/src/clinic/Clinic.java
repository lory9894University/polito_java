package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	private HashMap<String, Person> patientList = new HashMap<>();
	private HashMap<Integer, Doctor> doctorList = new HashMap<>();


	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patientList.put(ssn, new Person(first, last, ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		Person patient=patientList.get(ssn);
		if (patient==null) throw new NoSuchPatient();
		return patient.getInfo();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctorList.put(docID, new Doctor(first, last, ssn, specialization, docID));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		Doctor doctor=doctorList.get(docID);
		if(doctor==null) throw new NoSuchDoctor();
		return doctor.getInfo();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Doctor doctor=doctorList.get(docID);
		if(doctor==null) throw new NoSuchDoctor();
		Person patient=patientList.get(ssn);
		if (patient==null) throw new NoSuchPatient();
		doctor.newPatient(patient);
		patient.setDoctor(doctor);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		return patientList.get(ssn).getDoctor().getBadge();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		return doctorList.get(id).getPatients().values().stream().map(x->x.getSSN()).collect(Collectors.toList());
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		Pattern p = Pattern.compile("(P;.[^;]+;.[^;]+;.[^;]+$)|(M;\\d+;.[^;]+;.[^;]+;.[^;]+;.[^;]+$)",Pattern.CASE_INSENSITIVE);
		BufferedReader buffered = new BufferedReader(reader);
		List<String> lines=buffered.lines().collect(Collectors.toList());
		for (String line : lines) {
			line.replaceAll(" ", "");
			if(p.matcher(line).matches()) {
				String[] data = line.split(";");
				if(data[0].compareTo("P")==0) 
					addPatient(data[1], data[2], data[3]); 
				else 
					addDoctor(data[2], data[3], data[4], Integer.parseInt(data[1]), data[5]);
			}
		}
	}




	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return doctorList.values().stream().filter(x -> x.getPatients().isEmpty()).sorted((x,y)->x.getLast().compareTo(y.getLast()))
		.sorted((x,y)->x.getFirst().compareTo(y.getFirst())).map(x->x.getBadge()).collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		float averagePatients=doctorList.values().stream().map(x->(int)x.getPatients().size()).reduce(Integer::sum).get() / doctorList.size();
		return doctorList.values().stream().filter(x-> x.getPatients().size()>averagePatients).collect(Collectors.mapping(x->x.getBadge(), Collectors.toList()));
		//Collectors.averagingdouble
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return doctorList.values().stream().collect(Collectors.toMap(
				x->x.getPatients().size(), x -> x.getBadge() + " " + x.getLast() + " " + x.getFirst())).entrySet().stream().
				sorted((x,y)->x.getKey().compareTo(y.getKey())).
				map(x -> String.format("%3d",x.getKey()) + " : " + x.getValue()).collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
//		return patientList.values().stream().collect(Collectors.groupingBy(x->x.getDoctor().getSpecialization(),Collectors.counting())).entrySet()
//				.stream().sorted((Map.Entry<String,Long> z,Map.Entry<String,Long> y) -> z.getValue().compareTo(y.getValue()))
//				.sorted((Map.Entry<String,Long> o1,Map.Entry<String,Long> o2)-> o1.getKey().compareTo(o2.getKey()))
//				.map(x -> x.getValue().toString() + " - " + x.getKey()).collect(Collectors.toList());
		return doctorList.values().stream().collect(Collectors.groupingBy(x->x.getSpecialization(),Collectors.summingInt(x -> x.getPatients().size())))
		.entrySet().stream().map(x -> String.format("%3d", x.getValue()) + " - " + x.getKey()).collect(Collectors.toList());
	
	}
	
}
