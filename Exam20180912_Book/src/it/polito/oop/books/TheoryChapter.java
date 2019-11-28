package it.polito.oop.books;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class TheoryChapter {
	private String title,text;
	private int numPages;
	private TreeMap<String,Topic> topics = new TreeMap<String,Topic>();
	
	
    public TheoryChapter(String title, String text, int numPages) {
		super();
		this.title = title;
		this.text = text;
		this.numPages = numPages;
	}

	public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text=newText;
    }


	public List<Topic> getTopics() {
        return topics.values().stream().collect(Collectors.toList());
	}

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
    	this.numPages=newPages;
    }
    
    public void addTopic(Topic topic) {
    	topic.getSubTopicsRecursive().forEach(x-> topics.put(x.getKeyword(), x));
    }
    
}
