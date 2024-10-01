import org.springframework.boot.gradle.tasks.run.BootRun
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.github.node-gradle.node") version "7.0.2"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.allopen") version "1.9.25"
}

group = "at.rspiegl"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("io.github.wimdeblauwe:error-handling-spring-boot-starter:4.4.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

node {
    download.set(true)
    version.set("20.17.0")
}

val npmRunBuild = tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmRunBuild") {
    args.set(listOf("run", "build"))

    dependsOn(tasks.npmInstall)

    inputs.files(fileTree("node_modules"))
    inputs.files(fileTree("src/main/webapp"))
    inputs.file("angular.json")
    inputs.file("package.json")
    inputs.file("tsconfig.json")
    inputs.file("tsconfig.app.json")
    outputs.dir(layout.buildDirectory.dir("resources/main/static"))
}

tasks.processResources {
    dependsOn(npmRunBuild)
}

tasks.getByName<BootRun>("bootRun") {
    environment.put("SPRING_PROFILES_ACTIVE", environment.get("SPRING_PROFILES_ACTIVE") ?: "local")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
