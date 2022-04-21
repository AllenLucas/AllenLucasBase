// gradle 插件版本需要去官网查询
//https://plugins.gradle.org/plugin/org.jetbrains.kotlin.android

buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    }
}
plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    compileOnly("com.android.tools.build:gradle:7.0.3")
}
kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

gradlePlugin {
    plugins {
        create("version") {
            id = "com.allenlucas.version"
            implementationClass = "VersionConfigPlugin"
        }
    }
}