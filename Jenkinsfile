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
            description: 'Hangi branch üzerinde çalışılacak?',
            branchFilter: 'origin/(.*)',
            selectedValue: 'DEFAULT',
            sortMode: 'DESCENDING_SMART',
            useRepository: 'https://github.com/hakantetik44/CursorAndJenkins.git'
        )

        choice(
            name: 'TEST_ENV',
            choices: ['QA', 'STAGING', 'PROD'],
            description: 'Test ortamını seçin'
        )
        
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Test suite seçin'
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
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${params.BRANCH_TO_BUILD}"]],
                        userRemoteConfigs: [[url: 'https://github.com/hakantetik44/CursorAndJenkins.git']]
                    ])
                    
                    env.BRANCH_NAME = params.BRANCH_TO_BUILD
                    env.ALL_BRANCHES = sh(
                        script: '''
                            git fetch --all
                            git branch -r | grep -v HEAD | sed 's/origin\\///' | tr '\\n' ' '
                        ''',
                        returnStdout: true
                    ).trim()

                    echo """
                    🔍 Branch Bilgileri:
                    🌿 Seçilen Branch: ${env.BRANCH_NAME}
                    📋 Mevcut Branch'ler: ${env.ALL_BRANCHES}
                    """
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
                            -Dsuite=${params.TEST_SUITE.toLowerCase()}
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
                    // Allure raporu oluştur
                    sh 'mvn allure:report'
                    
                    // Raporları topla
                    sh """
                        mkdir -p consolidated-reports
                        cp -r target/site/allure-maven-plugin consolidated-reports/allure-report || true
                        cp -r target/surefire-reports consolidated-reports/ || true
                        cp -r target/cucumber-reports consolidated-reports/ || true
                        zip -r consolidated-test-results.zip consolidated-reports/
                    """

                    // Eğer allure branch'indeyse, raporları GitHub'a gönder
                    if (env.BRANCH_NAME == 'allure') {
                        sh """
                            git config user.email 'jenkins@example.com'
                            git config user.name 'Jenkins'
                            
                            # Allure raporları için klasör oluştur
                            mkdir -p docs/allure-reports/${env.BUILD_NUMBER}
                            cp -r target/site/allure-maven-plugin/* docs/allure-reports/${env.BUILD_NUMBER}/
                            
                            # Değişiklikleri commit'le
                            git add docs/allure-reports
                            git commit -m "Add Allure report for build ${env.BUILD_NUMBER}"
                            git push origin allure
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'consolidated-test-results.zip', fingerprint: true
            
            allure([
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            junit(
                testResults: '**/target/surefire-reports/TEST-*.xml',
                skipPublishingChecks: true,
                skipMarkingBuildUnstable: true
            )

            cleanWs()
        }

        success {
            script {
                echo """
                ✅ Test Sonuçları:
                🌿 Branch: ${env.BRANCH_NAME}
                📋 Mevcut Branch'ler: ${env.ALL_BRANCHES}
                🎯 Test Suite: ${params.TEST_SUITE}
                🌍 Environment: ${params.TEST_ENV}
                ✨ Status: Başarılı
                """
            }
        }

        failure {
            script {
                echo """
                ❌ Test Sonuçları:
                🌿 Branch: ${env.BRANCH_NAME}
                📋 Mevcut Branch'ler: ${env.ALL_BRANCHES}
                🎯 Test Suite: ${params.TEST_SUITE}
                🌍 Environment: ${params.TEST_ENV}
                ⚠️ Status: Başarısız
                """
            }
        }
    }
}