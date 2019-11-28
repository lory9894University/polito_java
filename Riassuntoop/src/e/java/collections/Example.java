package e.java.collections;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

public class Example {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Collection<Person> persons = new LinkedList<Person>();
		persons.add( new Person("Alice") );
		System.out.println( persons.size() );
		Collection<Person> copy = new TreeSet<Person>();
		copy.addAll(persons);//equivalente a//new TreeSet(persons)
		
		//Person[] array = copy.toArray();
		//System.out.println( array[0] );
	}
}

/* COLLECTION */
//Group of references to objects
//Not specified if Ordered/Unordered, Duplicated/!Duplicated
//Implements Iterable

/* LIST */
//Can contain duplicates
//Insertion order is preserved
//User can define insertion point
//Elements can be accessed by position

/* SET */
//Contains only methods inherited from Collection
//No duplicates allowed

/* SORTED SET */
//Using Iterator, the elements are traversed in natural ordering
//When creating a TreeSet it is possible to specify a Comparator
//by writing TreeSet(Comparator o)

/* ASSOCIATIVE CONTAINERS (MAPS) */
//Keys must be unique
//It is possible to specify a comparator for keys
