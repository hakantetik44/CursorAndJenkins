pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK17'
    }

    parameters {
        gitParameter(
            name: 'BRANCH_TO_BUILD',
            type: 'PT_BRANCH',
            defaultValue: 'main',
            description: 'Select branch to run tests:',
            branchFilter: 'origin/(.*)',
            selectedValue: 'DEFAULT',
            sortMode: 'DESCENDING_SMART',
            useRepository: 'https://github.com/hakantetik44/CursorAndJenkins.git'
        )

        choice(
            name: 'TEST_ENV',
            choices: ['QA', 'STAGING', 'PROD'],
            description: 'Select test environment'
        )
        
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Select test suite'
        )
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Branch Detection') {
            steps {
                script {
                    // Clean workspace before checkout
                    cleanWs()
                    
                    // Checkout selected branch
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${params.BRANCH_TO_BUILD}"]],
                        userRemoteConfigs: [[url: 'https://github.com/hakantetik44/CursorAndJenkins.git']]
                    ])
                    
                    env.BRANCH_NAME = params.BRANCH_TO_BUILD
                    env.GIT_BRANCH = params.BRANCH_TO_BUILD
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    try {
                        sh """
                            mvn clean test \
                            -Denv=${params.TEST_ENV.toLowerCase()} \
                            -Dsuite=${params.TEST_SUITE.toLowerCase()} \
                            -Dcucumber.options="--plugin json:target/cucumber-reports/cucumber.json"
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Generate Reports') {
            steps {
                script {
                    // Create consolidated reports directory
                    sh """
                        mkdir -p test-reports
                        
                        # Copy all reports
                        cp -r target/surefire-reports test-reports/ || true
                        cp -r target/cucumber-reports test-reports/ || true
                        cp -r target/allure-results test-reports/ || true
                        
                        # Create single zip file
                        zip -r test-reports.zip test-reports/
                    """
                }
            }
        }
    }

    post {
        always {
            // Archive test reports zip
            archiveArtifacts artifacts: 'test-reports.zip', fingerprint: true
            
            // Generate and publish Allure report
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            // Publish Cucumber reports
            cucumber buildStatus: 'UNSTABLE',
                     failedFeaturesNumber: 1,
                     failedScenariosNumber: 1,
                     skippedStepsNumber: 1,
                     failedStepsNumber: 1,
                     classifications: [
                         [key: 'Branch', value: env.BRANCH_NAME],
                         [key: 'Environment', value: params.TEST_ENV],
                         [key: 'Test Suite', value: params.TEST_SUITE]
                     ],
                     fileIncludePattern: '**/cucumber.json',
                     jsonReportDirectory: 'target/cucumber-reports'

            // Process JUnit results
            junit(
                testResults: '**/target/surefire-reports/TEST-*.xml',
                skipPublishingChecks: true,
                skipMarkingBuildUnstable: true
            )

            // Clean workspace
            cleanWs()
        }

        success {
            echo """
            ✅ Test Results:
            🌿 Branch: ${env.BRANCH_NAME}
            🎯 Test Suite: ${params.TEST_SUITE}
            🌍 Environment: ${params.TEST_ENV}
            ✨ Status: Success
            """
        }

        failure {
            echo """
            ❌ Test Results:
            🌿 Branch: ${env.BRANCH_NAME}
            🎯 Test Suite: ${params.TEST_SUITE}
            🌍 Environment: ${params.TEST_ENV}
            ⚠️ Status: Failed
            """
        }
    }
}