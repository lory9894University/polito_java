package it.polito.oop.elective;

import java.util.ArrayList;

public class Course {
	private int avaliableSeat;
	private ArrayList<Student> students=new ArrayList<Student>();
	private String name;
	
	public int getAvaliableSeat() {
		return avaliableSeat;
	}

	public String getName() {
		return name;
	}

	public Course(int avaliableSeat, String name) {
		this.avaliableSeat = avaliableSeat;
		this.name = name;
	}
	
	/**
	 * 	@return true if the students is added, false if it's not
	 */
	public boolean addStudent(Student student) {
		if(students.size()<avaliableSeat) {
			students.add(student);
			return true;
		}else {
			return false;
		}

	}

	public int getstudentsNumber() {
		return students.size();
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
}
