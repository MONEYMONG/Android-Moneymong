@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.moneymong.android.library)
    alias(libs.plugins.moneymong.android.hilt)
    alias(libs.plugins.secretsGradlePlugin)

}

android {
    namespace = "com.moneymong.moneymong.network"

    buildTypes {
        debug {
            buildConfigField("String", "MONEYMONG_BASE_URL", "\"https://dev.moneymong.site/\"")
        }
        release {
            buildConfigField("String", "MONEYMONG_BASE_URL", "\"https://prod.moneymong.site/\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    debugApi(libs.chucker.debug)
    releaseApi(libs.chucker.release)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}