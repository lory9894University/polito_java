package c.java.inheritance;

public class Manager extends Employee{
	String managedUnit;
	void changeUnit() {}
	
	@Override
	public void print() {
		System.out.println(name);
		System.out.println(managedUnit);
	}
}
