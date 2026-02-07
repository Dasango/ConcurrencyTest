plugins {
    id("java")
}

group = "com.uce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")
}

tasks.test {
    useJUnitPlatform()
}