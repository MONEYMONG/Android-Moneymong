// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        verbose.set(true)
        outputToConsole.set(true)
        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
        }
    }

    tasks.register<Copy>("copyGitHooks") {
        from("${rootDir}/.github/hooks") {
            include("**/*")
            rename("(.*)", "$1")
        }
        into("${rootDir}/.git/hooks")
    }

    tasks.register<Exec>("installGitHooks") {
        group = "git hooks"
        workingDir = rootDir
        commandLine("chmod")
        args("-R", "+x", ".git/hooks/")
        dependsOn("copyGitHooks")
    }
}
