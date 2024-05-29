name: PR CI for service

on:
  push:
    branches: [ "main" ]
  pull_request:
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