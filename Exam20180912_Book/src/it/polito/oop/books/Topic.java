package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Topic {
	private String keyword;
	private TreeMap<String,Topic> subtopics = new TreeMap<String,Topic>();
	
	public Topic(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}

	public boolean addSubTopic(Topic topic) {
		boolean added = subtopics.containsKey(topic.getKeyword()) ? false:true; 
		if(added)subtopics.put(topic.getKeyword(), topic);
        return added;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return subtopics.values().stream().collect(Collectors.toList());
	}
	public List<Topic>getSubTopicsRecursive(){
		ArrayList<Topic> topics = new ArrayList<>();
		topics.add(this);
		for (Topic topic : subtopics.values()) {
			topics.addAll(topic.getSubTopicsRecursive());
		}
		return topics;
	}
}
