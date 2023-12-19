plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.freefair.lombok")
    application
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    val tikaVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")


    implementation("org.apache.tika:tika-core:$tikaVersion")
    implementation("org.apache.tika:tika-parsers:$tikaVersion")
    implementation("org.apache.tika:tika-parser-audiovideo-module:$tikaVersion")

    implementation("org.apache.commons:commons-lang3:3.14.0")


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql:42.7.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.test {
    useJUnitPlatform()
}