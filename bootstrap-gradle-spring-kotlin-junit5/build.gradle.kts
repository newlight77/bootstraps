import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	idea
	`java-library`
	id("org.springframework.boot") version "2.3.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.3.70"
	kotlin("plugin.spring") version "1.3.70"
	kotlin("plugin.jpa") version "1.3.70"
	checkstyle
	jacoco
}

apply { plugin("java") }

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
	main {
		java.srcDir("src/main/java")
	}
}

configurations {
	implementation {
		resolutionStrategy.failOnVersionConflict()
	}
}

group = "com.newlight77"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.5")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(group = "junit", module = "junit")
	}
	testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("io.rest-assured:spring-mock-mvc:4.3.0") {
        exclude("com.sun.xml.bind:jaxb-osgi")
    }
}

application {
	mainClassName = "io.github.newlight77.bootstrap.ApplicationKt"
}

tasks.jacocoTestReport {
	reports {
		xml.isEnabled = false
		csv.isEnabled = false
		html.isEnabled = true
		html.destination = file("$buildDir/jacocoHtml")
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.5".toBigDecimal()
			}
		}

		rule {
			enabled = false
			element = "CLASS"
			includes = listOf("io.tricefal.*")

			limit {
				counter = "LINE"
				value = "TOTALCOUNT"
				maximum = "0.3".toBigDecimal()
			}
		}
	}
	classDirectories.setFrom(
			sourceSets.main.get().output.asFileTree.matching {
				// exclude main()
				exclude("io/tricefal/core/TricefalApplicationKt.class")
			}
	)
}

tasks.checkstyleMain { group = "verification" }
tasks.checkstyleTest { group = "verification" }

val testCoverage by tasks.registering {
	group = "verification"
	description = "Runs the unit tests with coverage."

	dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
	val jacocoTestReport = tasks.findByName("jacocoTestReport")
	jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
	tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
}

jacoco {
	toolVersion = "0.8.5"
	reportsDir = file("$buildDir/jacoco")
}

tasks.withType<Test> {
		useJUnitPlatform()
		testLogging {
			events = mutableSetOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED, org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
			exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
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

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}
