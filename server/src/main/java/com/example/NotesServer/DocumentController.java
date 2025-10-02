package com.example.NotesServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/docs")
@CrossOrigin(origins = "http://localhost:5173")
class DocumentController {
	private Map<Integer, Document> map = new HashMap<>(Map.of(1, new Document(1, "Sample Document", "This is a sample text."), 2, new Document(2, "Another sample document", "This is another sample text")));
	
	@GetMapping
	private ResponseEntity<List<DocumentSummary>> getAllDocuments() {
		List<DocumentSummary> documentsMap = map.values().stream()
				.map(document -> new DocumentSummary(document.getId(), document.getTitle()))
				.toList();
		return ResponseEntity.ok(documentsMap);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<Map<String, String>> getDocument(@PathVariable int id) {
		if (map.containsKey(id)) {
			return ResponseEntity.ok(Map.of("content", map.get(id).getContent()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<Void> replaceDocument(@PathVariable int id, @RequestBody Map<String, String> body) {
		String title = map.get(id).getTitle();
		map.put(id, new Document(id, title, body.get("content")));
		return ResponseEntity.noContent().build();
	}
}
