@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.moneymong.android.library)
    alias(libs.plugins.moneymong.android.library.compose)
}

android {
    namespace = "com.moneymong.moneymong.core.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
