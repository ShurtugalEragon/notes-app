package com.example.NotesServer;

class DocumentSummary {
	private int id;
	private String title;
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	DocumentSummary(int id, String title) {
		this.id = id;
		this.title = title;
	}
}
