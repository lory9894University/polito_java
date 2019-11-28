package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Book {
	
	private TreeMap<String,Topic> topics = new TreeMap<String,Topic>();
	private TreeMap<String,Question> questions = new TreeMap<String,Question>();
	private TreeMap<String,TheoryChapter> theory = new TreeMap<String,TheoryChapter>();
	private TreeMap<String,ExerciseChapter> exercise = new TreeMap<String,ExerciseChapter>();

    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
		if(keyword==null || keyword.equals("")) throw new BookException();
		Topic topic = topics.get(keyword);
		if(topic == null) {
			topic= new Topic(keyword);
			topics.put(keyword,topic);
		}
	    return topic;
	}

	public Question createQuestion(String question, Topic mainTopic) {
		Question questionq = new Question(question, mainTopic);
		questions.put(question, questionq);
        return questionq;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
		TheoryChapter chap = new TheoryChapter(title,text,numPages); 
		theory.put(title, chap);
        return chap;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter chap = new ExerciseChapter(title, numPages);
		exercise.put(title, chap);
        return chap;
	}

	public List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		exercise.values().stream().map( x->x.getTopics()).forEach(x-> topics.addAll(x));
		theory.values().stream().map( x->x.getTopics()).forEach(x-> topics.addAll(x));	
        return topics.stream().distinct().sorted((x,y)->x.getKeyword().compareTo(y.getKeyword())).collect(Collectors.toList());
	}

	public boolean checkTopics() {
		List<Topic> exercises = new ArrayList<Topic>();
		exercise.values().stream().map( x->x.getTopics()).distinct().forEach(x-> exercises.addAll(x));
		for (TheoryChapter th : theory.values()) {
			List<Topic> topics = th.getTopics();
			if(topics.containsAll(exercises))return true;
		}
        return false;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        return new Assignment(ID, chapter);
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return exercise.values().stream().flatMap(x->x.getQuestions().stream())
        		.collect(Collectors.groupingBy(x->x.numAnswers(),Collectors.mapping(x->x, Collectors.toList())));
    }
}
