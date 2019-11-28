package c.java.inheritance;

public class Employee {
	protected String name;
	protected double wage;
	
	public void incrementWage() {
		
	}
	public void print() {
		System.out.println(name);
	}
	public String whatIsThis() {
		if(this instanceof Employee) {
			return "Employee";
		}
		else if (this instanceof Manager) {
			return "Manager";
		}
		else
			return "Ma che ne so io";
	}
}
