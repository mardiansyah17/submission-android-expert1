import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "com.example.core"
    compileSdk = 35

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        buildConfigField("String", "SUPABASE_URL", properties.getProperty("supabaseUrl") ?: "")
        buildConfigField(
            "String",
            "SUPABASE_ANON_KEY",
            properties.getProperty("supabaseAnonKey") ?: ""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.firebase.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)

    api(libs.coil.compose)
    api(libs.material.icons.extended)
    api(libs.androidx.constraintlayout.compose)
    api(libs.lottie.compose)

    api(libs.firebase.firestore)
    api(libs.firebase.storage)

    api(libs.rxjava)
    api(libs.rxandroid)
    api(libs.adapter.rxjava3)
    api(libs.androidx.lifecycle.reactivestreams.ktx)

    api(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    api(libs.androidx.hilt.navigation.compose)

    api(platform(libs.supabase.bom))
    api(libs.postgrest.kt)
    api(libs.storage.kt)

    api(libs.ktor.client.core)
    api(libs.ktor.client.cio)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}