plugins {
    `java-library`
}

repositories {
    jcenter()
}


configurations.all {
    incoming.beforeResolve {
        throw IllegalStateException("You shouldn't resolve configurations during configuration phase!")
    }
}
// tag::config-logic[]
dependencies {
    implementation("log4j:log4j:1.2.17")
}

task("printArtifactNames") {
    // always executed
    val libraryNames = configurations["compileClasspath"].map { it.name }

    doLast {
        logger.quiet(libraryNames.toString())
    }
}
// end::config-logic[]
