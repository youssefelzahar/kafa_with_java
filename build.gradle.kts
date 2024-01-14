plugins {
    id("java")
}

group = "io.conduktor.demos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.apache.kafka:kafka-clients:3.6.1")
    implementation ("org.slf4j:slf4j-api:2.0.10")
    implementation ("org.slf4j:slf4j-simple:2.0.10")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    implementation ("com.launchdarkly:okhttp-eventsource:2.5.0")
}

tasks.test {
    useJUnitPlatform()
}