package university;
/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	private String uniName;
	private String rectorName;
	private Student students[] = new Student[1000];
	private int enrolledStudents=0;
	private final int ID_START_STUDENT = 10000;
	private final int ID_START_COURSES = 10;
	
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.uniName=name;
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return this.uniName;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last){
		this.rectorName = first + " " + last;
		}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name and surname of the rector in a single string
	 */
	public String getRector(){
		return this.rectorName;
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return id code of the new students
	 */
	public int enroll(String first, String last){
		students[enrolledStudents] = new Student(first,last,indexConverterStudents(enrolledStudents));
		enrolledStudents++;
		return students[enrolledStudents-1].id; //molto brutto ma non aveva senso aggiungere un getter
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		if (!controlStudent(id)) {
			return "error";
		}
		int vectPos = indexConverterStudents(id);
		return id + " " + students[vectPos].getName() + " " + students[vectPos].getSurname();
	}
	private Course courses[] = new Course[50];
	private int activatedCourses=0;
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		if(activatedCourses > 50) {
			System.out.println("too many courses for this univeristy, can't activate a new one");
			return 0;
		}
		courses[activatedCourses]=new Course(title,teacher,activatedCourses + ID_START_COURSES);
		activatedCourses++;
		return courses[activatedCourses-1].idCode;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		if (!controlCourse(code)) {
			return "error";
		}
		return code + ": " +courses[code - ID_START_COURSES].courseName + ", " + courses[code -ID_START_COURSES].teacherName;
	}

	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		if(!(controlCourse(courseCode) & controlStudent(studentID))) {
			return;
		}
		if(students[indexConverterStudents(studentID)].isFull() || students[indexConverterStudents(studentID)].alreadyAttendeant(courses[courseCode-ID_START_COURSES])
				|| courses[courseCode-ID_START_COURSES].isFull() || courses[courseCode-ID_START_COURSES].alreadyAttendeant(students[indexConverterStudents(studentID)]))
			return;
		students[indexConverterStudents(studentID)].addCourse(courses[courseCode-ID_START_COURSES]);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		if (controlCourse(courseCode))
			return courses[courseCode - ID_START_COURSES].listAttendant();
		else 
			return "error";
	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		if(controlStudent(studentID))
			return students[indexConverterStudents(studentID)].listCourse();
		else
			return "error";
	}
	private boolean controlCourse(int id) {
		if(id>60 || id<ID_START_COURSES) {
			System.out.println("id out of range");
			return false;
		}
		return true;
	}
	/**
	 * controls if the id is correct
	 * @param id student id
	 * @return true id the student id is in range
	 */
	private boolean controlStudent(int id) {
		if(id>11000 || id<ID_START_STUDENT) {
			System.out.println("id out of range");
			return false;
		}
		return true;
	}
	
	private int indexConverterStudents(int id) {
		if(id>=ID_START_STUDENT) {
			return id-ID_START_STUDENT;
		}else {
			return id+ID_START_STUDENT;
		} 
		
	}
	
}
