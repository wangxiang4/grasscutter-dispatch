import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.tianli.auth"
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
val druidVersion:String by extra { "1.2.18" }

application {
	// define the main class for the application
	mainClass.set("com.tianli.auth.grasscutterauth.GrasscutterAuthApplication")
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
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	implementation("com.alibaba:druid-spring-boot-starter:$druidVersion")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:$mybatisVersion")
	implementation("cn.hutool:hutool-core:$hutoolVersion")
	runtimeOnly("com.mysql:mysql-connector-j:$mysqlVersion")
	compileOnly("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

plugins {
	java
	application
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
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
