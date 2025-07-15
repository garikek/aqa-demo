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

    stage('Build & Test') {
      agent {
        docker {
          image 'maven:3.9.10-eclipse-temurin-21'
          args  '-v $HOME/.m2:/root/.m2'
          reuseNode true
        }
      }
      steps {
        checkout scm

        sh "mvn clean test -P${params.TEST_TYPE}"
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
