import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    application
    `java-library`
    kotlin("jvm") version "1.3.61"
    id("com.diffplug.gradle.spotless") version "3.27.2"
    id("org.jmailen.kotlinter") version "2.3.2"
    checkstyle
    idea
}

buildscript {
    configurations.classpath
        .resolutionStrategy.force("com.github.pinterest:ktlint:0.36.0")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.checkstyleMain { group = "verification" }
tasks.checkstyleTest { group = "verification" }

spotless {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target(fileTree(projectDir).apply {
            include("*.gradle.kts")
        } + fileTree("src").apply {
            include("**/*.gradle.kts")
        })
        ktlint()
    }
}

kotlinter {
    ignoreFailures = false
    indentSize = 4
    reporters = arrayOf("checkstyle", "plain")
    experimentalRules = false
    disabledRules = emptyArray<String>()
    fileBatchSize = 30
}

group = "io.github.newlight77"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:1.7.5")
    
    runtimeOnly("org.apache.logging.log4j:log4j-api:2.11.1")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.11.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.11.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

	testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("io.mockk:mockk:1.9.3")
}

configurations {                            
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

sourceSets {                                
    main {                                  
        java.srcDir("src/main/java")
    }
}

java {                                      
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


application {
    mainClassName = "io.github.newlight77.bootstrap.HelloWorldKt"
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "11"
        javaParameters = true
    }
}

// custom linting
tasks {
    "lintKotlinMain"(LintTask::class) {
        exclude("**/*Generated.kt")
    }
}

tasks.withType<Test> {
	useJUnitPlatform()
    testLogging {
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }

    val failedTests = mutableListOf<TestDescriptor>()
    val skippedTests = mutableListOf<TestDescriptor>()

    // See https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    // See https://github.com/gradle/kotlin-dsl/issues/836
    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
            when (result.resultType) {
                TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                else -> Unit
            }
        }

        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // root suite
                logger.lifecycle("----")
                logger.lifecycle("Test result: ${result.resultType}")
                logger.lifecycle(
                        "Test summary: ${result.testCount} tests, " +
                        "${result.successfulTestCount} succeeded, " +
                        "${result.failedTestCount} failed, " +
                        "${result.skippedTestCount} skipped")
                failedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tFailed Tests")
                skippedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tSkipped Tests:")
            }
        }

        private infix fun List<TestDescriptor>.prefixedSummary(subject: String) {
                logger.lifecycle(subject)
                forEach { test -> logger.lifecycle("\t\t${test.displayName()}") }
        }

        private fun TestDescriptor.displayName() = parent?.let { "${it.name} - $name" } ?: "$name"
    })
}

// cleanTest task doesn't have an accessor on tasks (when this blog post was written)
tasks.named("cleanTest") { group = "verification" }