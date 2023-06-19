plugins {
    java
    antlr
    application
}

version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("com.google.guava:guava:24.0-jre")
    antlr("org.antlr:antlr4:4.11.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-no-listener", "-long-messages", "-package", "fr.univorleans.mssl.Parser")
    outputDirectory = file("src/main/java/fr/univorleans/mssl/Parser")
}

val run: JavaExec by tasks
run.standardInput = System.`in`
run.enableAssertions = true

application {
    mainClass.set("fr.univorleans.mssl.MSSL.Main")
}
