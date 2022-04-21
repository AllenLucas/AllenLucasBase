//plugins {
//    id 'com.android.library'
//    id 'kotlin-android'
//    id 'kotlin-kapt'
//    id 'maven-publish'
//    id 'com.allenlucas.version'
//}
//
//android {
//    compileSdk AndroidBuildSetParam.compileSdk
//
//    defaultConfig {
//        minSdk AndroidBuildSetParam.minSdk
//        targetSdk AndroidBuildSetParam.targetSdk
//        versionCode AndroidBuildSetParam.versionCode
//        versionName AndroidBuildSetParam.versionName
//
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles "consumer-rules.pro"
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = '1.8'
//    }
//
//    viewBinding {
//        enabled = true
//    }
//}

//afterEvaluate {
//    publishing {
//        publications {
//            // Creates a Maven publication called "release".
//            release(MavenPublication) {
//                // Applies the component for the release build variant.
//                from components.release
//
//                // You can then customize attributes of the publication as shown below.
//                groupId = Publish.baseGroupId
//                artifactId = Publish.baseId
//                version = Publish.baseVersion
//            }
//            // Creates a Maven publication called “debug”.
////            debug(MavenPublication) {
////                // Applies the component for the debug build variant.
////                from components.debug
////
////                groupId = 'com.example.MyLibrary'
////                artifactId = 'final-debug'
////                version = '1.0'
////            }
//        }
//    }
//}

//dependencies {

//    def nav_version = "2.3.5" // navigation版本号

//    api 'androidx.core:core-ktx:1.6.0'
//    api 'androidx.appcompat:appcompat:1.3.1'
//    api 'com.google.android.material:material:1.4.0'
//    api 'androidx.constraintlayout:constraintlayout:2.1.1'

//    testImplementation Test.junit
//    androidTestImplementation Test.ext_junit
//    androidTestImplementation Test.espresso_core

    // 协程
//    api Google.coroutines_android
//    api Google.coroutines_core

//}

dependencies {
    api(AndroidX.coreKtx)
    api(AndroidX.appcompat)
    api(AndroidX.constraintlayout)
    api(Google.material)
    api(AndroidX.Fragment.fragmentKtx)

    api(ThirdParty.glide)
    kapt(ThirdParty.glideCompiler)

    api(AndroidX.Navigation.fragmentKtx)
    api(AndroidX.Navigation.uiKtx)
    api(AndroidX.Navigation.dynamic)
    androidTestApi(AndroidX.Navigation.testing)

    api(ThirdParty.retrofit)
    api(ThirdParty.converterGson)
}