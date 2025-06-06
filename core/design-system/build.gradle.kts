@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.moneymong.android.library)
    alias(libs.plugins.moneymong.android.library.compose)
}

android {
    namespace = "com.moneymong.moneymong.design_system"
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.androidx.core.ktx)
}
