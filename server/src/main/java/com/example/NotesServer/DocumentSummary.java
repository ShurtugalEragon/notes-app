package com.example.NotesServer;

class DocumentSummary {
	private final Long id;
	private final String title;
	
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	DocumentSummary(Long id, String title) {
		this.id = id;
		this.title = title;
	}
}
