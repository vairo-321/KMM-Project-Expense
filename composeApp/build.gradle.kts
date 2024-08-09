import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.sqlDelight)
    kotlin("plugin.serialization") version "1.9.22"
}

//SqlDelight config
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.expenseApp.db")
        }
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant{
            sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies{
                androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:1.5.4")
                debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation (libs.accompanist.systemuicontroller)

            //koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.android)

            //sqlDelight
            implementation(libs.sqlDelight.android)

            //Ktor-Client
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            // Ojo con la palabra reservada api y dependency y sus diferencias
            api(compose.foundation)
            api(compose.material)
            api(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //PreCompose, Viewmodel, navigation
            api(libs.precompose)
            api(libs.precompose.viewmodel)
            //api("moe.tlaster:precompose-navigation:$precompose_version")  <-- asi seria sin indice de version de libreria

            //Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            api(libs.precompose.koin)

            //Ktor-Client
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.serialization)
        }

        iosMain.dependencies {
            //SQLDelight probably need one more dependency
            implementation(libs.sqlDelight.ios)
            implementation(libs.stately.common)

            //Ktor-Client
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            //implementation(libs.kotlin.test)
            implementation(kotlin("test"))

            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

