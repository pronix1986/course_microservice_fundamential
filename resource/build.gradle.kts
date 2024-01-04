import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.freefair.lombok")
    application
}

group = "com.epam.sp"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")

}

dependencies {
    val tikaVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")


    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")


    implementation("org.apache.tika:tika-core:$tikaVersion")
    implementation("org.apache.tika:tika-parsers:$tikaVersion")
    implementation("org.apache.tika:tika-parser-audiovideo-module:$tikaVersion")

    implementation("org.apache.commons:commons-lang3:3.14.0")


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql:42.7.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:2.2.224")
    testImplementation("org.wiremock:wiremock-standalone:3.3.1")


}

extra["springCloudVersion"] = "2023.0.0"
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    archiveFileName = "app.jar"
}

tasks.withType<JavaCompile> {
    options.release = 17
}
