package com.example.NotesServer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
	    "spring.datasource.url=jdbc:h2:mem:notes",
	    "spring.datasource.driver-class-name=org.h2.Driver",
	    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
	    "spring.jpa.defer-datasource-initialization=true",
	    "spring.sql.init.mode=always",
	    "spring.jpa.hibernate.ddl-auto=create-drop"
	})
@AutoConfigureRestTestClient
class NotesServerIntegrationTests {
	@Autowired
	private RestTestClient restTestClient;
	
	@Test
	void shouldGetDocument() {
		restTestClient
			.get().uri("/docs/1")
			.exchange()
			.expectStatus().isOk();
	}
	
	@Test
	void shouldGetDocumentList() {
		restTestClient
			.get().uri("/docs")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.length()").isEqualTo(2);
	}
	
	@Test
	void shouldCreateDocument() {
		Document document = new Document(null, "Document 3", "");
		
		restTestClient
			.post().uri("/docs")
			.body(document)
			.exchange()
			.expectStatus().isCreated()
			.expectBody();
	}
	
	@Test
	void shouldDeleteDocument() {
		restTestClient
			.delete().uri("docs/1")
			.exchange()
			.expectStatus().isNoContent();
		
		restTestClient
			.get().uri("docs/1")
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	void shouldUpdateDocument() {
		String updatedContent = "This is the updated content";
		Document updatedDocument = new Document(null, null, updatedContent);
		
		restTestClient
			.put().uri("docs/2")
			.body(updatedDocument)
			.exchange()
			.expectStatus().isNoContent();
		
		restTestClient
			.get().uri("docs/2")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.content").isEqualTo(updatedContent);
	}
}
