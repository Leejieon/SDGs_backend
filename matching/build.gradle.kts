plugins {
	java
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.2"
}

group = "com.learning_coordinator"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven {
		name = "clojars.org"
		url = uri("https://repo.clojars.org")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.apache.commons:commons-lang3:3.13.0")
	implementation("org.springframework:spring-oxm:5.3.10")
	implementation("net.clojars.suuft:libretranslate-java:1.0.5")
	implementation("com.googlecode.json-simple:json-simple:1.1.1")
	implementation("dev.langchain4j:langchain4j-embeddings-all-minilm-l6-v2:0.21.0")
	implementation(files("libs/objectdb.jar"))
	implementation(files("libs/langchain4j_enhanced.jar"))
	compileOnly("org.projectlombok:lombok:1.18.28")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok:1.18.28")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
