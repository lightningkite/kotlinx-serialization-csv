plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

val implementation by configurations

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":kotlinx-serialization-csv"))
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}
