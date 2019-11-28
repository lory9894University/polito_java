package clinic;

import java.util.HashMap;

public class Doctor extends Person {
	private String specialization;
	private int badge;
	private HashMap<String, Person> patientList = new HashMap<>();
	
	public String getSpecialization() {
		return specialization;
	}
	public int getBadge() {
		return badge;
	}
	public HashMap<String, Person> getPatients() {
		return patientList;
	}
	public Doctor(String first, String last, String SSN, String specialization, int badge) {
		super(first, last, SSN);
		this.specialization = specialization;
		this.badge = badge;
	}
	@Override
	public String getInfo() {
		return super.getInfo() + " [" +  Integer.toString(badge) + "] " + specialization;
	}
	public void newPatient(Person patient) {
		patientList.put(patient.getSSN(), patient);
	}
	
}
