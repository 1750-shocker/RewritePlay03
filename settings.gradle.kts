pluginManagement {
    repositories {
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
    val MAVENUSER = providers.gradleProperty( "MAVENUSER")
    val MAVENPASSWORD = providers.gradleProperty("MAVENPASSWORD")
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("http://172.16.15.158:8081/artifactory/list/gradle-release/")
            isAllowInsecureProtocol = true
            credentials {
                username = MAVENUSER.get()
                password = MAVENPASSWORD.get()
            }
        }
    }
}

rootProject.name = "RewritePlay03"
include(":app")
include(":core")
include(":model")
include(":network")
