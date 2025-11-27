plugins {
    alias(libs.plugins.moneymong.android.library.compose)
    alias(libs.plugins.moneymong.android.feature)
    alias(libs.plugins.moneymong.android.hilt)
}

android {
    namespace = "com.moneymong.moneymong.report"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designSystem)
    implementation(projects.core.ui)
    implementation(projects.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    implementation(libs.orbit.core)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewModel)
}