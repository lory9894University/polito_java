package it.polito.oop.elective;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {
	
	private HashMap<String,Course> courses = new HashMap<String,Course>();
	private HashMap<String,Student> students = new HashMap<String,Student>();
	private List<Notifier> listeners=new LinkedList<Notifier>();

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
    	courses.put(name, new Course(availablePositions,name));
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
    	TreeSet<String> ordered=new TreeSet<String>();
    	courses.values().forEach(x -> ordered.add(x.getName()));
        return ordered;
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, 
                                  double gradeAverage){
    	if(students.containsKey(id)) {
    		students.get(id).setAverageMark(gradeAverage);
    	}else {
    		students.put(id, new Student(id, gradeAverage));
    	}
        
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return students.entrySet().stream().collect(Collectors.toMap(x->x.getValue(), x->x.getKey())).values();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return students.entrySet().stream().filter(x -> x.getValue().getAverageMark() >= inf && x.getValue().getAverageMark() <= sup).collect(Collectors.toMap(x->x.getValue(), x->x.getKey())).values();
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
    	Student student;
    	if ((student=students.get(id))==null || courses.size()<1 || courses.size()>3)
    		throw new ElectiveException();
    	for (String course : courses) {
    		if(!this.courses.containsKey(course)) throw new ElectiveException();
    		student.addCourseRequest(this.courses.get(course));
    	}
    	for (Notifier listener : listeners)
    		listener.requestReceived(id);
        return courses.size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
    	HashMap<String,List<Long>> request = new HashMap<String, List<Long>>();
    	for (Course course : courses.values()) {
    		Long[] choices = {(long) 0,(long) 0,(long) 0};
    		students.values().forEach(x -> {
    			ArrayList<Course> coursesRequested=x.getCourseRequest();
    			for (int i = 0; i < coursesRequested.size(); i++) {
					if(coursesRequested.get(i).getName().equals(course.getName())) choices[i]++;
				}
    			});
    		request.put(course.getName(),Arrays.asList(choices));
		}
        return request;
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
        students.values().stream().sorted((x,y)->Double.compare(x.getAverageMark(), y.getAverageMark())).forEach(x->
        {
        	for (int i = 0; i < x.getCourseRequest().size(); i++) {
        		if (courses.get(x.getCourseRequest().get(i).getName()).addStudent(x)) {
            		x.setAssigned(true);
            		x.setCourse(x.getCourseRequest().get(i));
            		for (Notifier listener : listeners)
                		listener.assignedToCourse(x.getId(),x.getCourseRequest().get(i).getName());
            		break;
            	}
			}
        }
        		);
        return students.values().stream().filter(x->!x.isAssigned()).count();
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
    	return courses.values().stream().collect(toMap(Course::getName,x->{ return x.getStudents().stream().sorted((z,y)->Double.compare(z.getAverageMark(), y.getAverageMark())).map(y->y.getId()).collect(toList());}));
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
    	this.listeners.add(listener);    
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        return (double)students.values().stream().filter(Student::isAssigned).
        		filter(x -> x.getCourseRequest().get(choice).getName().compareTo(x.getCourse().getName())==0).
        		count() / (double) students.values().size();
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return students.values().stream().filter(x->!x.isAssigned()).map(x->x.getId()).collect(toList());
    }
    
    
}
