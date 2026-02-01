plugins {
    id("java")
    id("io.freefair.lombok") version "9.2.0"
}

group = "com.uce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:7.1.10.Final")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        output.setResourcesDir(output.classesDirs.files.first())
    }
}