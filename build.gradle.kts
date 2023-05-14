version = "1.0.0"
group = "com.tianli.auth"
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
// todo: testings
val appName:String by extra { rootProject.name }

repositories {
	mavenCentral()
	maven {
		url = uri("https://maven.aliyun.com/repository/public")
	}
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:$mybatisVersion")
	implementation("cn.hutool:hutool-core:$hutoolVersion")
	runtimeOnly("com.mysql:mysql-connector-j:$mysqlVersion")
	compileOnly("org.projectlombok:lombok:$lombokVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

plugins {
	java
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.processResources {
	filesMatching("application.yml") {
		expand(project.properties)
	}
}
