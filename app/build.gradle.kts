import configs.AndroidConfig
import configs.KotlinConfig
import configs.ProguardConfig
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    buildToolsVersion(AndroidConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)

        applicationId = AndroidConfig.applicationId
        testInstrumentationRunner = AndroidConfig.instrumentationTestRunner
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.apply {
            useSupportLibrary = true
            generatedDensities(*(AndroidConfig.noGeneratedDensities))
        }
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("signing/green_cloud.keystore")
            storePassword = "greencloud"
            keyAlias = "green_cloud"
            keyPassword = "greencloud"
        }
    }

    buildTypes {
        getByName("debug") {

//            applicationIdSuffix = ".debug"
        }

        getByName("release") {
            isMinifyEnabled = true

            signingConfig = signingConfigs.getByName("release")

            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            val proguardConfig = ProguardConfig("$rootDir/proguard")
            proguardFiles(*(proguardConfig.customRules))
            proguardFiles(getDefaultProguardFile(proguardConfig.androidRules))
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = KotlinConfig.targetJVM
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    lintOptions {
        checkOnly("Interoperability")
        disable("ContentDescription")
        isAbortOnError = false
    }
}

    dependencies {
        implementation(project(":googlemapsfloatingmarkertitles"))

        implementation(Libs.kotlinStdLib)

        implementation(Libs.appcompat)
        implementation(Libs.constraintlayout)
        implementation(Libs.coreKtx)
        implementation(Libs.legacySupport)
        implementation(Libs.material)

        implementation(Libs.extensions)
        implementation(Libs.commonJava8)
        implementation(Libs.viewModel)
        implementation(Libs.liveData)
        implementation(Libs.navFragment)
        implementation(Libs.navFragmentKtx)
        implementation(Libs.navUi)
        implementation(Libs.navUiKtx)
        implementation(Libs.roomKtx)
        kapt(Libs.roomCompiler)

        implementation(Libs.location)
        implementation(Libs.maps)
        implementation(Libs.auth)

        implementation(Libs.glide)
        kapt(Libs.glideCompiler)

        implementation(Libs.retrofit)
        implementation(Libs.gson)
        implementation(Libs.gsonConverter)
//        implementation(Libs.rxJavaConverter)
        implementation(Libs.loggingInterceptor)

        implementation(Libs.rxJava)
        implementation(Libs.rxAndroid)
        implementation(Libs.rxLocation)
        implementation(Libs.rxPermission)

        implementation(Libs.timber)
        implementation(Libs.stetho)
        implementation(Libs.stethoOkhttp3)

//        implementation(Libs.kakaoLogin)
        implementation ("com.kakao.sdk", "usermgmt", "1.21.1")
        implementation(Libs.naveridlogin)
        implementation(Libs.facebook_login)

        implementation(Libs.zxing)
        implementation(Libs.mapUtil)
}

kapt {
    useBuildCache = true
}

