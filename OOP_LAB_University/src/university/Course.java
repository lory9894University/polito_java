package university;
import java.lang.StringBuffer;
class Course {
	
	String courseName, teacherName; //prima ho usato getter, stavolta uso attributi pubblici
	int idCode;
	
	public Course(String course, String teacher, int id) {
		this.courseName=course;
		this.teacherName=teacher;
		this.idCode=id;
	}
	
	Student attendants[]=new Student[100];
	int attendantsNr;
	
	public void addAttendant(Student student) {
		/*if(this.isFull() || this.alreadyAttendeant(student) || student.isFull() || student.alreadyAttendeant(this))
			return;*/ //non necessario. aggiungere se serve 
		this.attendants[attendantsNr]=student;
		attendantsNr++;
	}
	
	public String listAttendant() {
		StringBuffer list= new StringBuffer();
		for (int i=0; i<this.attendantsNr ; i++) {
			list.append(attendants[i].id + " " + attendants[i].getName() + " " + attendants[i].getSurname() + "\n" );
		}
		return list.toString();
	}
	
	/**
	 * check if the courses has more than 25 courses
	 * @return true if it has more than 100 students, false otherwise
	 */
	public boolean isFull() {
		if(attendantsNr>100) {
			System.out.println("error, too many students\n");
			return true;
		}
		else
			return false;
	}
	/**
	 * check if the student is already attending the course
	 * @param student the student whom is trying to join
	 * @return true if the student is already attending
	 */
	public boolean alreadyAttendeant(Student student) {
		for (int i=0; i<this.attendantsNr ; i++) {
			if(student == attendants[i]) {
				System.out.println("error, the student is already an attendant\n");
				return true;
			}
		}
		return false;
	}
}
