plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	id("jacoco")
	id("io.gitlab.arturbosch.detekt") version ("1.23.1")
	kotlin("jvm") version "2.0.10"
	kotlin("plugin.spring") version "1.8.22"
	id("org.springdoc.openapi-gradle-plugin") version "1.7.0"
	id("com.x3t.gradle.plugins.openapi.openapi_diff") version "1.0"
	id("info.solidsoft.pitest") version "1.15.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.1.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	testImplementation("io.mockk:mockk:1.13.8")
	testImplementation("io.kotest:kotest-assertions-core:5.9.1")
	testImplementation("io.kotest:kotest-property:5.9.1")
	testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
	testImplementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.15.0")
	testImplementation("io.kotest.extensions:kotest-extensions-pitest:1.2.0")

	implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.21")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

jacoco {
	toolVersion = "0.8.12"
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

pitest {
	threads = 4
	timestampedReports = false
	targetClasses = listOf("com.example.demo.domain.model.*", "com.example.demo.domain.usecase.*")
}