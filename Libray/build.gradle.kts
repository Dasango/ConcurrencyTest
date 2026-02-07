plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"
}

group = "com.uce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")
    implementation("org.hibernate:hibernate-core:7.1.10.Final")
    implementation("org.postgresql:postgresql:42.7.8")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main{
        output.setResourcesDir(output.classesDirs.files.first())
    }
}