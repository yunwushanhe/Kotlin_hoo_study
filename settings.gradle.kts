pluginManagement {
    repositories {
        maven {
            url = uri("https://mirrors.huaweicloud.com/repository/maven/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://mirrors.huaweicloud.com/repository/maven/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Kotlin_hoo_study"
include(":app")
 