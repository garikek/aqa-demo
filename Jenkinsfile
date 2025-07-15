pipeline {
  agent any

  tools {
    allure 'Allure'
  }

  options {
    skipDefaultCheckout()

    buildDiscarder(logRotator(
      numToKeepStr: '10',
      daysToKeepStr: '30',
      artifactNumToKeepStr: '10'
    ))
  }

  parameters {
    choice(
      name: 'TEST_TYPE',
      choices: ['api', 'ui'],
      description: 'Which tests to run?'
    )
  }

  environment {
    ALLURE_RESULTS = 'target/allure-results'
  }

  stages {
    stage('Checkout') {
      agent any
      steps {
        deleteDir()
        checkout scm
      }
    }

    stage('Start Selenoid') {
      steps {
        sh '''
          docker run -d --name selenoid \
            -v /var/run/docker.sock:/var/run/docker.sock \
            -v $PWD/selenoid/config/:/etc/selenoid/:ro \
            -p 4444:4444 \
            aerokube/selenoid:latest-release
        '''
      }
    }

    stage('Build & Test') {
      agent {
        docker {
          image 'maven:3.9.10-eclipse-temurin-21'
          args  '-v $HOME/.m2:/root/.m2 --link selenoid:selenoid'
          reuseNode true
        }
      }
      environment {
        SELENOID_URL = 'http://selenoid:4444/wd/hub'
      }
      steps {
        checkout scm

        sh '''
          mvn clean test -P${TEST_TYPE} \
            -Dselenide.remote=${SELENOID_URL} \
            -Dselenide.browser=chrome \
            -Dselenide.browser.version=128.0 \
            -Dselenide.browserSize=1920x1080 \
            -Dselenide.headless=false
        '''
        stash name: 'allure-results', includes: 'target/allure-results/**'
      }
    }

    stage('Publish Allure Report') {
      agent any
      steps {
        deleteDir()
        unstash 'allure-results'
        allure([
          results: [[path: 'target/allure-results']],
          report: 'allure-report',
          reportBuildPolicy: 'ALWAYS'
        ])
        stash name: 'allure-html', includes: 'allure-report/**'
      }
    }
  }

  post {
    always {
      deleteDir()
      unstash 'allure-html'

      archiveArtifacts artifacts: 'allure-report/**/*', fingerprint: true

      publishHTML([
            reportName: 'Allure HTML',
            reportDir: 'allure-report',
            reportFiles: 'index.html',
            allowMissing: false,
            keepAll: true,
            alwaysLinkToLastBuild: true
      ])
    }
    failure {
      echo "Build failed! Investigate the Allure report."
    }
  }
}
