package com.example.NotesServer;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/docs")
@CrossOrigin(origins = "http://localhost:5173")
class DocumentController {
	private Map<Integer, String> map = new HashMap<>(Map.of(1, "This is a sample text."));
	
	@GetMapping("/{id}")
	private ResponseEntity<Map<String, String>> getDocument(@PathVariable int id) {
		if (map.containsKey(id)) {
			return ResponseEntity.ok(Map.of("content", map.get(id)));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<Void> replaceDocument(@PathVariable int id, @RequestBody Map<String, String> body) {
		map.put(id, body.get("content"));
		return ResponseEntity.noContent().build();
	}
}
