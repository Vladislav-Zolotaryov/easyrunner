package com.easyrunner.webapp

import com.easyrunner.webapp.fixture.DatabaseTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebAppTests : DatabaseTest() {

	@Test
	fun contextLoads() {

	}

}
