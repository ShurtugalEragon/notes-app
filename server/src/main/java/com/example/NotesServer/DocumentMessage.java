package com.example.NotesServer;

class DocumentMessage {
	private String content;
	private String clientId;
	
	public String getContent() {
		return content;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	DocumentMessage(String content, String clientId) {
		this.content = content;
		this.clientId = clientId;
	}
}
