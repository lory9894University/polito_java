package it.polito.oop.books;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Question {
	private String text;
	private Topic topic;
	private HashMap<String,Boolean> answers = new HashMap<String,Boolean>();
	
	
	public Question(String text, Topic topic) {
		super();
		this.text = text;
		this.topic = topic;
	}

	public String getQuestion() {
		return text;
	}
	
	public Topic getMainTopic() {
		return topic;
	}

	public void addAnswer(String answer, boolean correct) {
		answers.put(answer, correct);
	}
	
    @Override
    public String toString() {
        return text + " (" + topic.getKeyword()+")";
    }

	public long numAnswers() {
	    return answers.size();
	}

	public Set<String> getCorrectAnswers() {
		return answers.entrySet().stream().filter(x->x.getValue()).map(x->x.getKey()).collect(Collectors.toSet());
	}

	public Set<String> getIncorrectAnswers() {
        return answers.entrySet().stream().filter(x->!x.getValue()).map(x->x.getKey()).collect(Collectors.toSet());
	}
}
