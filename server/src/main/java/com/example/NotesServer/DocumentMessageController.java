package com.example.NotesServer;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
class DocumentMessageController {
	
	@MessageMapping("/docs/{id}/edit")
	@SendTo("/topic/docs/{id}")
	private DocumentMessage sendMessage(DocumentMessage documentMessage) {
		return documentMessage;
	}
}
