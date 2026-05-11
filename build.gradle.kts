plugins {
    id("fabric-loom") version "1.14.10"
}

base {
    archivesName = properties["archives_base_name"] as String
    version = libs.versions.mod.version.get()
    group = properties["maven_group"] as String
}

repositories {
    maven {
        name = "meteor-maven"
        url = uri("https://maven.meteordev.org/releases")
    }
    maven {
        name = "meteor-maven-snapshots"
        url = uri("https://maven.meteordev.org/snapshots")
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
    modImplementation(libs.meteor.client)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    processResources {
        val propertyMap = mapOf(
            "version" to project.version,
            "jdk_version" to libs.versions.jdk.get(),
        )

        inputs.properties(propertyMap)
        filesMatching("fabric.mod.json") {
            expand(propertyMap)
        }
    }

    jar {
        inputs.property("archivesName", project.base.archivesName.get())

        from("LICENSE") {
            rename { "${it}_${inputs.properties["archivesName"]}" }
        }
    }

    withType<JavaCompile>().configureEach {
        options.release.set(21)
        options.compilerArgs.addAll(
            listOf(
                "-Xlint:deprecation",
                "-Xlint:unchecked"
            )
        )
    }
}
