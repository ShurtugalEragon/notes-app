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
}
