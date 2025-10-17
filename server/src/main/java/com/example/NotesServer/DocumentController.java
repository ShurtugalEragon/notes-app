package com.example.NotesServer;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/docs")
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentController {
	private final DocumentRepository documentRepository;
	
	public DocumentController(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}
	
	@GetMapping
	private ResponseEntity<List<DocumentSummary>> getAllDocuments() {
		List<DocumentSummary> documentsMap = documentRepository.findAll().stream()
				.map(document -> new DocumentSummary(document.getId(), document.getTitle()))
				.toList();
		return ResponseEntity.ok(documentsMap);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<Map<String, String>> getDocument(@PathVariable Long id) {
		Optional<Document> document = documentRepository.findById(id);
		
		if (document.isPresent()) {
			Map<String, String> response = new HashMap<>();
			response.put("title", document.get().getTitle());
			response.put("content", document.get().getContent());
			
			return ResponseEntity.ok(response); 
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<Void> replaceDocument(@PathVariable Long id, @RequestBody Map<String, String> body) {
		Optional<Document> document = documentRepository.findById(id);
		
		if (!document.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if (body.containsKey("content")) {
			document.get().setContent(body.get("content"));
		}
		
		documentRepository.save(document.get());
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	private ResponseEntity<DocumentSummary> createDocument(@RequestBody Map<String, String> body, UriComponentsBuilder ucb) {
		Document createdDocument = documentRepository.save(new Document(null,body.getOrDefault("title", "Document Title"), ""));
		
		URI locationOfCreatedDocument = ucb
				.path("doc/{id}")
				.buildAndExpand(createdDocument.getId())
				.toUri();
		
		DocumentSummary documentSummary = new DocumentSummary(createdDocument.getId(), createdDocument.getTitle());
		
		return ResponseEntity.created(locationOfCreatedDocument).body(documentSummary);
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
		documentRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
