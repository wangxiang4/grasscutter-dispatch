import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.tianli.dispatch"
version = "1.0.0"
description = "for grasscutter authorization dispatch"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

val hutoolVersion:String by extra { "5.8.18" }
val mybatisVersion:String by extra { "3.0.0" }
val lombokVersion:String by extra { "1.18.26" }
val mysqlVersion:String by extra { "8.0.33" }
val okhttpVersion:String by extra { "4.11.0" }
val kLogVersion:String by extra { "3.0.5" }
val bcryptVersion:String by extra { "0.10.2" }
val protobufVersion:String by extra { "3.23.2" }

application {
	// define the main class for the application
	mainClass.set("com.tianli.dispatch.GrasscutterDispatchApplication")
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://maven.aliyun.com/repository/public")
	}
}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.boot:spring-boot-starter-freemarker")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:$mybatisVersion")
	implementation("cn.hutool:hutool-core:$hutoolVersion")
	implementation("cn.hutool:hutool-json:$hutoolVersion")
	runtimeOnly("com.mysql:mysql-connector-j:$mysqlVersion")
	compileOnly("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	implementation("io.github.microutils:kotlin-logging-jvm:$kLogVersion")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("at.favre.lib:bcrypt:$bcryptVersion")
	implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
}

protobuf {
	protoc {
		// The artifact spec for the Protobuf Compiler
		artifact = "com.google.protobuf:protoc:3.23.2"
	}
}

plugins {
	idea
	java
	application
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.dokka") version "1.8.10"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	id("com.google.protobuf") version "0.9.3"
}

tasks.withType<BootJar> {
	exclude("**/*.proto")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<ProcessResources> {
	filesMatching("**/*.yml") {
		expand(project.properties)
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}
