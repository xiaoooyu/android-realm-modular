# android-realm-modular
Demostrate how to load dependencies from local folder as a repository and upload modular aar into the folder.

## Step 1. Add local folder as Maven repository
Code below shows how to use folder `m2repository` under project root as Maven repository.
```
allprojects {
    repositories {
        maven {
            url "${rootProject.projectDir}/m2repository"
        }
        jcenter()
    }
}
```

## Step 2. Install existing dependencies AAR/JAR in local folder
First Maven need to be installed
```
brew install maven
```
Then use code below to install existing AAR/JAR file into local folder
```
mvn install:install-file -Dfile=<path-to-file> -DpomFile=<path-to-pomfile> 
```
A related pom file can be designed by parameter(-DpomFile) which declares AAR/JAR's own dependencies.

## Step 3. Build source code and upload artifact(AAR) in local folder
If we own the source or we can build code by our own, thing can be easier.

First, apply Maven plugin in the gradle build file.
```
apply plugin: 'maven'
```

Then, adding task below 
```
uploadArchives {
    repositories.mavenDeployer {
        def deployPath = file("${rootProject.projectDir}/${LOCAL_REPOSITORY}")
        repository(url: "file://${deployPath.absolutePath}")
        pom.project {
            groupId LIBRARY_GROUP_ID
            artifactId LIBRARY_ARTIFACT_ID
            version LIBRARY_VERSION
        }
    }
}
```

Finally, execute gradle task, bigo, everything is ready in the specified folder in the format of Maven repository.
