plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"
}

group = "com.uce"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.postgresql:postgresql:42.7.8")
    implementation("org.hibernate:hibernate-core:7.1.10.Final")

    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")
}

tasks.test {
    useJUnitPlatform()
}