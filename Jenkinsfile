pipeline {
    agent any

    tools {
        jdk     'jdk-21'
        maven   'maven-3.9.6'
        allure  'allure-2.34.1'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    parameters {
        choice(
            name: 'TEST_TYPE',
            choices: ['api', 'ui'],
            description: 'Which tests to run?'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git(
                    url:   'https://github.com/garikek/aqa-demo',
                    branch: 'dev'
                )
            }
        }

        stage('Run tests') {
            steps {
                echo "Running ${params.TEST_TYPE} tests..."
                bat "mvn clean test -P${params.TEST_TYPE}"
            }
            post {
                always {
                    junit 'target/surefire-reports/TEST-*.xml'

                    archiveArtifacts(
                        allowEmptyArchive: true,
                        artifacts: 'target/allure-results/**'
                    )

                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed!'
        }
    }
}