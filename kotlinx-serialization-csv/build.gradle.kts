import com.lightningkite.deployhelpers.developer
import com.lightningkite.deployhelpers.github
import com.lightningkite.deployhelpers.standardPublishing

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("org.jetbrains.dokka")
    id("signing")
    `maven-publish`
}

val serializationVersion = "1.0.0"

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
}

standardPublishing {
    name.set("kotlinx-serialization-csv")
    description.set("Library to easily use Kotlin Serialization to serialize to/from CSV.")
    github("lightningkite", "kotlinx-serialization-csv")
    licenses {
        license {
            name.set("Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }

    developers {
        developer(
            id = "LightningKiteJoseph",
            name = "Joseph Ivie",
            email = "joseph@lightningkite.com",
        )
        developer(
            id = "brudaswen",
            name = "Sven Obser",
            email = "dev@brudaswen.de",
        )
    }
}

//java {
//    withSourcesJar()
//
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}
//
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//    kotlinOptions.freeCompilerArgs = listOf(
//        "-Xuse-experimental=kotlin.Experimental"
//    )
//}
//
//tasks.named<Test>("test") {
//    useJUnitPlatform()
//}
//
//tasks.withType<GenerateModuleMetadata> {
//    enabled = !isSnapshot()
//}
//
//val dokkaJavadocJar by tasks.creating(Jar::class) {
//    group = JavaBasePlugin.DOCUMENTATION_GROUP
//    description = "Assembles Kotlin docs with Dokka"
//    archiveClassifier.set("javadoc")
//    from(tasks.dokkaJavadoc)
//}
//
//val publishRelease = tasks.create("publishRelease") {
//    description = "Publish to Maven Central (iff this is a release version)."
//}
//
//val publishSnapshot = tasks.create("publishSnapshot") {
//    description = "Publish to Maven Central (iff this is a snapshot version)."
//}
//
//tasks.whenTaskAdded {
//    if (name == "publishToSonatype") {
//        val publishToSonatype = this
//        if (!isSnapshot()) {
//            publishRelease.dependsOn(publishToSonatype)
//
//            val closeAndReleaseRepository = rootProject.tasks.getByName("closeAndReleaseRepository")
//            closeAndReleaseRepository.mustRunAfter(publishToSonatype)
//            publishRelease.dependsOn(closeAndReleaseRepository)
//        } else {
//            publishSnapshot.dependsOn(publishToSonatype)
//        }
//    }
//}
//
//publishing {
//    publications {
//        create<MavenPublication>("library") {
//            artifactId = "kotlinx-serialization-csv"
//
//            pom {
//                name.set("kotlinx-serialization-csv")
//                description.set("Library to easily use Kotlin Serialization to serialize to/from CSV.")
//                url.set("https://github.com/brudaswen/serialization-csv/")
//
//                licenses {
//                    license {
//                        name.set("Apache License, Version 2.0")
//                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("LightningKiteJoseph")
//                        name.set("Joseph Ivie")
//                        email.set("joseph@lightningkite.com")
//                        organization.set("Lightning Kite")
//                        organizationUrl.set("https://lightningkite.com")
//                    }
//                    developer {
//                        id.set("brudaswen")
//                        name.set("Sven Obser")
//                        email.set("dev@brudaswen.de")
//                    }
//                }
//                scm {
//                    connection.set("scm:git:git://github.com/lightningkite/kotlinx-serialization-csv.git")
//                    developerConnection.set("scm:git:ssh://git@github.com:lightningkite/kotlinx-serialization-csv.git")
//                    url.set("https://github.com/lightningkite/kotlinx-serialization-csv/")
//                }
//                issueManagement {
//                    system.set("GitHub Issues")
//                    url.set("https://github.com/lightningkite/kotlinx-serialization-csv/issues/")
//                }
//            }
//
//            from(components["java"])
//            artifact(dokkaJavadocJar)
//        }
//    }
//}
//
//nexusPublishing {
//    repositories {
//        sonatype() {
//            this.nexusUrl.set(URI("https://s01.oss.sonatype.org/content/repositories/releases/"))
//            this.snapshotRepositoryUrl.set(URI("https://s01.oss.sonatype.org/content/repositories/snapshots"))
//        }
//    }
//
//    clientTimeout.set(Duration.ofMinutes(30))
//    val useSnapshot: String? by project
//    if (useSnapshot != null) {
//        useStaging.set(useSnapshot?.toBoolean()?.not())
//    }
//}
//
//signing {
//    setRequired { !isSnapshot() }
//
//    val signingKey: String? by project
//    val signingPassword: String? by project
//    useInMemoryPgpKeys(signingKey, signingPassword)
//
//    sign(publishing.publications["library"])
//}
//
//tasks.jacocoTestReport {
//    reports {
//        xml.isEnabled = true
//        html.isEnabled = false
//    }
//}
//
//tasks.check {
//    dependsOn(tasks.jacocoTestReport)
//}
//
//fun isSnapshot() = version.toString().endsWith("-SNAPSHOT")
//
//fun url(path: String) = uri(path).toURL()
