package clinic;

public class Person {
	
	private String first,last,SSN;
	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	private Doctor doctor;

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getSSN() {
		return SSN;
	}

	public Person(String first, String last, String SSN) {
		this.first = first;
		this.last = last;
		this.SSN = SSN;
	}
	
	String getInfo() {
		return last + " " + first + " (" + SSN + ")";
	}

}
