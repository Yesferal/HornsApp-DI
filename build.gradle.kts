plugins {
    kotlin("multiplatform") version "1.5.10"
    id("maven-publish")
}

group = "com.yesferal.hornsapp.core"
version = "1.2.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    sourceSets {
        sourceSets["jvmTest"].dependencies {
            implementation("junit:junit:4.13.2")
        }
    }
}
