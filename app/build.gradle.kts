plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))

    implementation(project(":library-android"))
    implementation(project(":library-kotlin"))

    implementation(SupportLibs.ANDROIDX_APPCOMPAT)
    implementation(SupportLibs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(SupportLibs.ANDROIDX_CORE_KTX)
    implementation(SupportLibs.ANDROID_NAV_ARCH_FRG_KTX)
    implementation(SupportLibs.ANDROID_NAV_ARCH_UI_KTX)
    implementation(SupportLibs.ANDROID_NAV_LEGACY_SUPP)
    implementation("com.github.fondesa:kpermissions:3.2.1")
    implementation("com.github.Zhuinden:fragmentviewbindingdelegate-kt:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.airbnb.android:lottie:4.1.0")
    implementation("com.pixplicity.easyprefs:EasyPrefs:1.10.0")

    implementation("com.google.firebase:firebase-core:19.0.2")
    implementation("com.google.firebase:firebase-messaging:22.0.0")
    implementation("com.google.firebase:firebase-config:21.0.1")
    implementation("com.google.firebase:firebase-analytics:19.0.2")
    implementation("com.google.firebase:firebase-crashlytics:18.2.3")

    testImplementation(TestingLib.JUNIT)

    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT_KTX)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_RULES)
    androidTestImplementation(AndroidTestingLib.ESPRESSO_CORE)
}
