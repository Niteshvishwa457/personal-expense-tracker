# Personal Expense Tracker - Maven Wrapper Bootstrapper
$ErrorActionPreference = "Stop"

$WrapperJarUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
$WrapperPropertiesUrl = "https://raw.githubusercontent.com/apache/maven-wrapper/master/maven-wrapper-distribution/src/main/resources/maven-wrapper.properties"
$MvnwCmdUrl = "https://raw.githubusercontent.com/apache/maven-wrapper/master/maven-wrapper-distribution/src/main/resources/mvnw.cmd"
$MvnwShUrl = "https://raw.githubusercontent.com/apache/maven-wrapper/master/maven-wrapper-distribution/src/main/resources/mvnw"

Write-Host "Creating .mvn/wrapper directory..." -ForegroundColor Cyan
New-Item -ItemType Directory -Path ".mvn/wrapper" -Force | Out-Null

Write-Host "Downloading Maven Wrapper JAR..." -ForegroundColor Cyan
curl -o .mvn/wrapper/maven-wrapper.jar $WrapperJarUrl

Write-Host "Downloading properties file..." -ForegroundColor Cyan
curl -o .mvn/wrapper/maven-wrapper.properties $WrapperPropertiesUrl

Write-Host "Downloading mvnw scripts..." -ForegroundColor Cyan
curl -o mvnw.cmd $MvnwCmdUrl
curl -o mvnw $MvnwShUrl

Write-Host "Maven Wrapper setup complete! Checking version..." -ForegroundColor Green
./mvnw.cmd --version
