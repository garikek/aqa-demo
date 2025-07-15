pipeline {
  agent any

//   1) Define a choice parameter to select test type
  parameters {
    choice(
      name: 'TEST_TYPE',
      choices: ['api', 'ui'],
      description: 'Which tests to run?'
    )
  }

  environment {
//     2) Base directory for Allure results
    ALLURE_RESULTS = 'target/allure-results'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
//         3) Run Maven with the selected profile
        sh "mvn clean test -P${params.TEST_TYPE}"
      }
    }

    stage('Publish Allure Report') {
      steps {
//         4) Publish results using the Allure plugin
        allure([
          results: [[path: env.ALLURE_RESULTS]],
          includeProperties: false
        ])
      }
    }
  }

  post {
    always {
//       5) Archive test logs and artifacts if needed
      archiveArtifacts artifacts: '**/target/*.log', allowEmptyArchive: true
    }
    failure {
//       6) Optionally send notifications on failure
      echo "Build failed! Investigate the Allure report."
    }
  }
}