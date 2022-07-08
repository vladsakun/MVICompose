import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("com.apollographql.apollo:apollo-gradle-plugin:2.5.4")
    }
}
plugins {
    alias(libs.plugins.android.application) apply (false)
    alias(libs.plugins.android.library) apply (false)
    alias(libs.plugins.kotlin.android) apply (false)
    alias(libs.plugins.kotlin.kapt) apply (false)
    alias(libs.plugins.toml.checker)
    alias(libs.plugins.toml.updater)
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

versionCatalogUpdate {
    pin {
        // keep has the same options as pin to keep specific entries
        // note that for versions it will ONLY keep the specified version, not all
        // entries that reference it.
        versions.add("kotlin")
//        versions.add("coreKtx")
//        libraries.add(libs.android.material)
//        plugins.add(libs.plugins.toml.checker)
    }
}

fun isNonStable(version: String): Boolean {
    return listOf("-BETA", "-ALPHA", "-RC").any { version.toUpperCase().contains(it) }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

// task to log all view models
// task graph - cached if source has not been changed