plugins {
    id("java")
}

group = "io.conduktor.demos"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.apache.kafka:kafka-clients:3.6.1")
    implementation ("org.slf4j:slf4j-api:2.0.10")
    implementation ("org.slf4j:slf4j-simple:2.0.10")


}

tasks.test {
    useJUnitPlatform();
}