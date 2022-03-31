object Versions {
    const val hilt = "2.40"
    const val coroutines = "1.5.2"
    const val okhttp = "4.9.1"
    const val retrofit = "2.9.0"
}

object Dependencies {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterScalars =
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
}

object TestVersions {
    const val junit = "4.13.2"
}

object TestDependencies {
    const val junit = "junit:junit:${TestVersions.junit}"
}

object SdkVersion {
    const val compileSdkVersion = 32
    const val minSdkVersion = 23
    const val targetSdkVersion = 32
}