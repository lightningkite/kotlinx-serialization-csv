

buildscript {
    val kotlinVersion:String by extra
    repositories {
        maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")
        classpath("com.lightningkite:deploy-helpers:0.0.7")
    }
}
allprojects {
    group = "com.lightningkite"
    repositories {
        google()
        mavenCentral()

    }
    tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
}