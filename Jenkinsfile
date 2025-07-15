pipeline {
  agent none

  tools {
    allure 'Allure'
  }

  options {
    skipDefaultCheckout()
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
        sh "mvn clean test -P${params.TEST_TYPE}"
      }
    }

    stage('Publish Allure Report') {
      agent any
      steps {
        allure([
          results: [[path: env.ALLURE_RESULTS]],
          includeProperties: false
        ])
      }
    }
  }

  post {
    always {
      node {
        archiveArtifacts artifacts: '**/target/*.log', allowEmptyArchive: true
      }
    }
    failure {
      echo "Build failed! Investigate the Allure report."
    }
  }
}
