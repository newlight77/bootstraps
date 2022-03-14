package io.github.newlight77.bootstrap

import org.slf4j.LoggerFactory

// Create a logger using SLF4J (Simple Logging Facade). SLF4J is designed to allow changing the logging implementation
// at runtime while maintaining the logging interface throughout the code.
val logger = LoggerFactory.getLogger("application")

fun main(args: Array<String>) {
    logger.debug("Hello World!")
}
