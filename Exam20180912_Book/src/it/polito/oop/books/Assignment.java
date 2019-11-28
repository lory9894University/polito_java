package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;

public class Assignment {
	private String ID;
	private ExerciseChapter chapter;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private double totalscore=0;

    public Assignment(String iD, ExerciseChapter chapter) {
		super();
		ID = iD;
		this.chapter = chapter;
		questions.addAll(chapter.getQuestions());
	}

	public String getID() {
        return ID;
    }

    public ExerciseChapter getChapter() {
        return chapter;
    }

    public double addResponse(Question q,List<String> answers) {
    	long n = q.numAnswers();
    	double fp=0,fn=0;
    	for (String a1 : q.getIncorrectAnswers()) {
			if(answers.contains(a1))fp++;
		}
    	for (String a2 : q.getCorrectAnswers()) {
    		if(!answers.contains(a2))fn++;
		}
    	totalscore+=((double)n-fp-fn)/(double)n;
        return ((double)n-fp-fn)/(double)n;
    }
    
    public double totalScore() {
        return this.totalscore;
    }

}
