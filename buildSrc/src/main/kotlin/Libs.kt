object Libs {

    // ClassPath
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildGradleVersion}"
    const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val googleService = "com.google.gms:google-services:${Versions.googleServiceVersion}"
    const val sentryGradle =
            "io.sentry:sentry-android-gradle-plugin:${Versions.sentryGradleVersion}"
    const val r8 = "com.android.tools:r8:${Versions.r8Version}"
    const val androidJunit5Version =
            "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Version}"
    const val androidMavenGradle =
            "com.github.dcendents:android-maven-gradle-plugin:${Versions.androidMavenGradleVersion}"
    const val detektGradle =
            "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektGradleVersion}"
    const val navSafeArgPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navSafArgPluginVersion}"

    // Kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"

    // Coroutines
    const val coroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"
    const val coroutineCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineVersion}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineVersion}"

    // rx java
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"

    // AndroidX
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val legacySupport =
            "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    const val activity = "androidx.activity:activity:${Versions.activityVersion}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragmentVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerviewVersion}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2Version}"
    const val databindingCompiler =
            "com.android.databinding:compiler:${Versions.databindingVersion}"
    const val desugarJdk = "com.android.tools:desugar_jdk_libs:${Versions.desugarJdkVersion}"


    // Test
    const val testCore = "androidx.test:core:${Versions.testVersion}"
    const val testrunner = "androidx.test:runner:${Versions.testVersion}"
    const val testrules = "androidx.test:rules:${Versions.testVersion}"
    const val espressoCore =
            "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
    const val truth = "com.google.truth:truth:${Versions.truthVersion}"

    // Lifecycle
    const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleVersion}"
    const val extensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleVersion}"
    const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleVersion}"
    const val commonJava8 =
            "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycleVersion}"
    const val compiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycleVersion}"
    const val viewModelSavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifeCycleVersion}"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // Navigation
    const val navFragment =
            "androidx.navigation:navigation-ui:${Versions.navigationVersion}"
    const val navFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navUi = "androidx.navigation:navigation-ui:${Versions.navigationVersion}"
    const val navUiKtx =
            "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    // StartUp
    const val startUp = "androidx.startup:startup-runtime:${Versions.startupVersion}"

    // Google
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    // Junit
    const val junit4 = "junit:junit:${Versions.junitVersion}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val gsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val rxJavaConverter = "com.squareup.retrofit2:adapter-rxjava2:$${Versions.retrofitVersion}"

    // Okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3Version}"
    const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}"

    // Aws Sdk
    const val awsSdkCognito = "com.amazonaws:aws-android-sdk-cognito:${Versions.awsSdkVersion}"
    const val awsSdkS3 = "com.amazonaws:aws-android-sdk-s3:${Versions.awsSdkVersion}"

    // Debug
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val leakcanary =
            "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanaryVersion}"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stethoVersion}"
    const val stethoOkhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stethoVersion}"
    const val stethoJs = "com.facebook.stetho:stetho-js-rhino:${Versions.stethoVersion}"
    const val sentry = "io.sentry:sentry-android:${Versions.sentryVersion}"

    // File Downloader
    const val fetch2 = "androidx.tonyodev.fetch2:xfetch2:${Versions.fetch2Version}"

    // Recyclerview Delegate
    const val adapterdelegates =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.adapterdelegatesVersion}"
    const val adapterdelegatesViewBinding =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:${Versions.adapterdelegatesVersion}"

    // Klint
    const val klint = "com.pinterest:ktlint:${Versions.klintVersion}"

    // Koin
    const val koin = "org.koin:koin-android:${Versions.koinVersion}"
    const val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koinVersion}"
    const val koinScope = "org.koin:koin-android-scope:${Versions.koinVersion}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
    const val koinTest = "org.koin:koin-test:${Versions.koinVersion}"
    const val koinCoreExt = "org.koin:koin-core-ext:${Versions.koinVersion}"

    // Mockk
    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"

    // Mockito
    const val mockito = "org.mockito:mockito-inline:${Versions.mockitoVersion}"

    // Speck2
    const val juintPlatformEngine = "org.junit.platform:junit-platform-engine:${Versions.junitPlatformVersion}"
    const val junitPlatformRunner = "org.junit.platform:junit-platform-runner:${Versions.junitPlatformVersion}"
    const val spek2DslJvm = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spek2Version}"
    const val spek2Runner = "org.spekframework.spek2:spek-runner-junit5:${Versions.spek2Version}"
    const val kotlinReflection = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlinVersion}"

    // Flipper
    const val flipper = "com.facebook.flipper:flipper:${Versions.flipperVersion}"
    const val soloader = "com.facebook.soloader:soloader:${Versions.soloaderVersion}"
    const val flipperNoop = "com.facebook.flipper:flipper-noop:${Versions.flipperVersion}"

    // Colil
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    // PlayService
    const val location = "com.google.android.gms:play-services-location:${Versions.playService}"
    const val maps = "com.google.android.gms:play-services-maps:${Versions.playService}"
    const val auth = "com.google.android.gms:play-services-auth:${Versions.playService}"

    // Rx..
    const val rxLocation = "com.patloew.rxlocation:rxlocation:${Versions.rxLocationVersion}"
    const val rxPermission = "com.github.tbruyelle:rxpermissions:${Versions.rxPermissionVersion}"

    const val zxing = "com.journeyapps:zxing-android-embedded:${Versions.zxing}"

    const val naveridlogin = "com.naver.nid:naveridlogin-android-sdk:${Versions.naver}"
    const val facebook_login = "com.facebook.android:facebook-login:${Versions.facebook}"
    const val kakaoLogin = "com.kakao.sdk:v2-user:${Versions.kakao}"

    const val mapUtil = "com.google.maps.android:android-maps-utils:${Versions.mapUtilVersion}"
}
