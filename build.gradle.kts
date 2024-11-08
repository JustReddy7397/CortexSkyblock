plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.7.4"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "dev.cortex"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_22

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.infernalsuite.com/repository/maven-snapshots/")
    maven("https://repo.rapture.pw/repository/maven-releases/")

}

dependencies {
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
    implementation("com.github.stefvanschie.inventoryframework:IF:0.10.17")
    implementation("com.jeff-media:MorePersistentDataTypes:2.4.0")

    implementation("com.infernalsuite.aswm:loaders:3.0.0-SNAPSHOT")
    compileOnly("com.infernalsuite.aswm:api:3.0.0-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("io.github.revxrsal:lamp.common:4.0.0-beta.19")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-beta.19")
}

tasks {
    shadowJar {
        relocate("com.github.stefvanschie.inventoryframework", "dev.cortex.IF")
        relocate("com.jeff_media.morepersistentdatatypes", "dev.cortex.morepersistentdatatypes")
        relocate("io.github.revxrsal.lamp", "dev.cortex.lamp")
    }

    build {
        // dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(22)
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}

