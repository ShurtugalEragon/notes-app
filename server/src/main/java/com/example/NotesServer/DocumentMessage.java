package com.example.NotesServer;

class DocumentMessage {
	private String content;
	private String clientId;
	private boolean delete;
	
	public String getContent() {
		return content;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public boolean getDelete() {
		return delete;
	}
	
	DocumentMessage(String content, String clientId, boolean delete) {
		this.content = content;
		this.clientId = clientId;
		this.delete = delete;
	}
}
