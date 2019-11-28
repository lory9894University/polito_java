package it.polito.oop.elective;

import java.util.ArrayList;

public class Student {
	private double averageMark;
	private String id;
	private ArrayList<Course> coursesRequested=new ArrayList<>();
	private boolean assigned=false;
	private Course course;

	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public boolean isAssigned() {
		return assigned;
	}
	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
	public Student(String id, double averageMark) {
		super();
		this.id = id;
		this.averageMark = averageMark;
	}
	public double getAverageMark() {
		return averageMark;
	}

	public void setAverageMark(double averageMark) {
		this.averageMark = averageMark;
	}

	public String getId() {
		return id;
	}
	public ArrayList<Course> getCourseRequest() {
		return coursesRequested;
	}
	public void addCourseRequest(Course coursesRequested) {
		this.coursesRequested.add(coursesRequested);
	}

}
