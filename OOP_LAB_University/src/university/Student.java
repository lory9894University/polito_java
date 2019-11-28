package university;
/*
 * this object represent a single student
 */
class Student {
	private String name, surname;
	/**
	 * getter
	 * @return student's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * getter
	 * @return student's Surname
	 */
	public String getSurname() {
		return surname;
	}
	
	int id;
	Course courses[]=new Course[25];
	int coursesNr=0;
	/**
	 * constructor
	 * 
	 * @param name student's name
	 * @param surname student's surname
	 * @param id identification number (matricola)
	 */
	public Student(String name,String surname,int id){
		this.name=name;
		this.surname=surname;
		this.id=id;
	}
	
	public void addCourse(Course corso){
		/*if(this.isFull() || this.alreadyAttendeant(corso) || corso.isFull() || corso.alreadyAttendeant(this))
			return;*/
		this.courses[coursesNr]=corso;
		coursesNr++;
		corso.addAttendant(this);
	}
	
	public String listCourse() {
		StringBuffer list= new StringBuffer();
		for (int i=0; i<this.coursesNr ; i++) {
			list.append(courses[i].idCode + " " + courses[i].courseName + " " + courses[i].teacherName + "\n" );
		}
		return list.toString();
	}
	/**
	 * check if the students has more than 25 courses
	 * @return true if he has more than 25 courses, false otherwise
	 */
	public boolean isFull() {
		if(coursesNr>25) {
			System.out.println("error, too many courses\n");
			return true;
		}
		else
			return false;
	}
	/**
	 * check if the student is already attending the course
	 * @param course the course the student is trying to join
	 * @return true if the student is already attending
	 */
	public boolean alreadyAttendeant(Course course) {
		for (int i=0; i<this.coursesNr ; i++) {
			if(course == courses[i]) {
				System.out.println("error, the student is already an attendant\n");
				return true;
			}
		}
		return false;
	}
}
