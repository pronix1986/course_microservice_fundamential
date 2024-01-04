rootProject.name = "course_microservice_task1"

pluginManagement {
    plugins {
        val springBootVersion: String by settings
        val springDependencyManagementVersion: String by settings
        val lombokVersion: String by settings

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("io.freefair.lombok") version lombokVersion
    }
}
include("resource")
include("song")
include("discovery-service")
include("api-gateway")
