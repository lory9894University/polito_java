package it.polito.oop.books;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class ExerciseChapter {
	private String title;
	private int numPages;
	private TreeMap<String,Question> questions = new TreeMap<String,Question>();

	
    public ExerciseChapter(String title, int numPages) {
		super();
		this.title = title;
		this.numPages = numPages;
	}

	public List<Topic> getTopics() {
        return questions.values().stream().map(x->x.getMainTopic()).distinct().collect(Collectors.toList());
	};
	
    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
    	this.title=newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages= newPages;
    }
    

	public void addQuestion(Question question) {
		questions.put(question.getQuestion(), question);
	}	
	
	public List<Question> getQuestions(){
		return questions.values().stream().collect(Collectors.toList());
	}
}
