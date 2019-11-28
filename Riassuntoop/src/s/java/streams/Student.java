package s.java.streams;

import java.util.ArrayList;
import java.util.Collection;

public class Student {
	private int id;
	private String name, sex;
	private ArrayList<Course> courses = new ArrayList<>();
	
	public Student(int id, String n, String s) {
		this.id=id;
		this.name=n;
		this.sex=s;
	}
	public int getId(){
		return id;
	}
	public String getFirst() {
		return name;
	}
	public boolean isFemale() {
		return sex.equals("female");
	}
	public Student enrollIn(Course c) {
		courses.add(c);
		return this;
	}
	public Collection<Course> enrolledIn(){
		return courses;
	}
	@Override
	public String toString() {
		return "Student "+id+": "+name+" is a "+sex;
	}
}