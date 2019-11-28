package s.java.streams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Example {

	public static void main(String[] args) {
		Collection<Student> oopClass = new ArrayList<>();
		// TODO Auto-generated method stub
		Stream.of("Hello","World").sorted().limit(3).forEach(System.out::println);
		Student maria = new Student(0,"Maria","female");
		Student john = new Student(1,"John","male");
		oopClass.add(maria);
		oopClass.add(john);
		
		//filtering with functional interface
		oopClass.stream().filter(Student::isFemale).forEach(System.out::println);
		
		//filtering with lambda exp
		oopClass.stream().filter(s->s.getFirst().equals("John")).forEach(System.out::println);
		
		//mapping to length of first name
		oopClass.stream().map(Student::getFirst).map(String::length).forEach(System.out::println);
		
		Course oop = new Course("OOP");
		Course sclero = new Course("Metodi matematici per l'ingegneria");
		maria.enrollIn(oop);
		john.enrollIn(oop).enrollIn(sclero);
		
		/*example of flat-mapping (print the title of all the courses attended by the students)*/
		oopClass.stream().map(Student::enrolledIn)	//Stream<Student> --> Stream<List<Course>>
		.flatMap(Collection::stream)				//Stream<List<Course>> --> Stream<Course>
		.distinct().map(Course::getTitle).forEach(System.out::println);
		
		//Numeric streams (faster as no boxing/unboxing is required)
		IntStream seq = IntStream.generate(()->(int)(Math.random()*100));
		int max = seq.limit(10).max().getAsInt();
		System.out.println(max);
		
		//Terminal operation: reduce()
		int maxNameLength = oopClass.stream().map(Student::getFirst).map(String::length).reduce(0, Math::max);
		System.out.println(maxNameLength);
		
		//Terminal operation: collect() takes a Collector as an argument
		
		//When defining a Collector<T (element), A (accumulator), R (result)>
		//it must implements supplier(), accumulator(), combiner(), finisher()
		//a lot of useful collectors can be found in java.util.stream.Collectors
		
		//Collectors are usually split in categories:
		//--Summarizing --Accumulating --Grouping
		//groupingBy(Function<T,K> classifier) returns a Map<K, List<T>>
		
		/*example of grouping words by length*/
		String[] txta = {"Torchi","fammi","passare","sta","materia","please","voglio","andare","a","mare"};
		Map<Integer, List<String>> wordsByLength = Stream.of(txta).distinct()
				.collect(Collectors.groupingBy(String::length));
		System.out.println(wordsByLength);
		
		/*similar to previous but in descending order*/
		Map<Integer, List<String>> wordsByLengthDesc = Stream.of(txta).distinct()
				.collect(Collectors.groupingBy(String::length, ()->new TreeMap<>(Collections.reverseOrder()), Collectors.toList()));
		System.out.println(wordsByLengthDesc);
		
		/*re-opening the map entry-set to work on it*/
		//prints the longest words (top 3 lengths)
		List<String> longestWords= wordsByLengthDesc.entrySet().stream().limit(3).flatMap(e->e.getValue().stream()).collect(Collectors.toList());
		System.out.println(longestWords);
	}
}
