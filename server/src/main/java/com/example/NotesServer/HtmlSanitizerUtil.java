package com.example.NotesServer;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

class HtmlSanitizerUtil {
	private static final PolicyFactory policy = Sanitizers.FORMATTING;
	
	static String sanitize(String unsafe) {
		return policy.sanitize(unsafe);
	}
}