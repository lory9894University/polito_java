package e.java.collections;

public class Person implements Comparable<Person>{
	private String name;
	public Person(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	@Override
	public int compareTo(Person b) {
		// TODO Auto-generated method stub
		return this.name.compareTo(b.getName());
	}
}