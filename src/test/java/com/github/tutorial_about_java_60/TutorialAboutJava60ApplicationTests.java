package com.github.tutorial_about_java_60;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
	properties = {
		"spring.datasource.url=jdbc:h2:mem:test",
		"spring.jpa.hibernate.ddl-auto=create-drop"
	}
)
class TutorialAboutJava60ApplicationTests {

	@Test
	void contextLoads() {
	}

}
