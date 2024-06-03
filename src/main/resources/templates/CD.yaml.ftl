name: CI for Service

on:
  push:
    branches: [ "main" ]

jobs:
  build:

    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: 17
        distribution: 'temurin'
    - name: Make gradlew executable
      run: chmod +x ./gradlew
    - name: Build
      run: ./gradlew clean build
    - name: 'Login via Azure CLI'
      uses: azure/login@v1
      with:
        creds: ${r"${{ secrets.AZURE_CREDENTIALS }}"}
    - name: Azure WebApp
      uses: Azure/webapps-deploy@v3
      with:
        app-name: ${projectName}
        package: ${r"build/libs/*-SNAPSHOT.jar"}