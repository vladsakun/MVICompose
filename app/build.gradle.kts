import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.text.SimpleDateFormat
import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("com.apollographql.apollo")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.mvicompose"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            manifestPlaceholders["usesCleartextTraffic"] = "true"
        }
        release {
            manifestPlaceholders["usesCleartextTraffic"] = "false"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    testOptions {
        animationsDisabled = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }
    apollo {
        generateKotlinModels.set(true)
    }
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjvm-default=enable")
    }

    // Custom task
    applicationVariants.all {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formatted = simpleDateFormat.format(Date())
        val name = "MVICompose_${name}_${formatted}.apk"
        outputs.all {
            val output = this as? BaseVariantOutputImpl
            output?.outputFileName = name
        }
    }
}

gradle.taskGraph.whenReady {
    val sources = File("${project.projectDir}/src/main/java/com/example/mvicompose")
    printDirectoryTree(sources)
}

fun printDirectoryTree(folder: File?) {
    if (folder != null && folder.isDirectory && folder.listFiles() != null) {
        for (file in folder.listFiles()!!) {
            if (file.isDirectory) {
                printDirectoryTree(file)
            } else {
                if (file.name.endsWith("ViewModel.kt")) {
                    println(file.name)
                }
            }
        }
    }
}

dependencies {
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.android.coreKtx)
    implementation(libs.android.appCompat)
    implementation(libs.android.material)
    implementation(libs.biometrics)
    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.apollo)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junitAndroid)
}