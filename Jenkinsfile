pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK17'
    }

    parameters {
        choice(
            name: 'BRANCH_NAME',
            choices: ['main', 'development', 'feature/*', 'bugfix/*', 'release/*'],
            description: 'Hangi branch üzerinde test çalıştırılacak?'
        )
        
        choice(
            name: 'TEST_SUITE',
            choices: ['smoke', 'regression', 'all'],
            description: 'Hangi test suite çalıştırılacak?'
        )

        booleanParam(
            name: 'SKIP_TESTS',
            defaultValue: false,
            description: 'Testler atlanacak mı?'
        )

        string(
            name: 'CUCUMBER_TAGS',
            defaultValue: '@smoke',
            description: 'Çalıştırılacak Cucumber tag\'leri (örn: @smoke or @regression)'
        )
    }

    environment {
        ALLURE_RESULTS_DIR = 'target/allure-results'
        CUCUMBER_REPORTS_DIR = 'target/cucumber-reports'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 1, unit: 'HOURS')
        ansiColor('xterm')
        timestamps()
    }

    stages {
        stage('🔄 Branch Checkout') {
            steps {
                echo "🔄 ${params.BRANCH_NAME} branch'i checkout ediliyor..."
                git branch: "${params.BRANCH_NAME}", 
                    url: 'https://github.com/hakantetik44/CursorAndJenkins.git'
            }
        }

        stage('🔍 Validate') {
            steps {
                echo '🔎 Proje yapısı kontrol ediliyor...'
                sh 'mvn validate'
            }
        }

        stage('🧹 Clean') {
            steps {
                echo '🧹 Eski build dosyaları temizleniyor...'
                sh 'mvn clean'
            }
        }

        stage('🏗️ Compile') {
            steps {
                echo '🏗️ Proje derleniyor...'
                sh 'mvn compile'
            }
        }

        stage('🧪 Run Tests') {
            when {
                expression { return !params.SKIP_TESTS }
            }
            steps {
                echo "🧪 ${params.TEST_SUITE} testleri çalıştırılıyor..."
                script {
                    try {
                        sh """
                            mvn test \
                            -Dcucumber.filter.tags="${params.CUCUMBER_TAGS}" \
                            -Dcucumber.plugin="pretty,html:${CUCUMBER_REPORTS_DIR}/cucumber-reports.html" \
                            -Dcucumber.plugin="json:${CUCUMBER_REPORTS_DIR}/CucumberTestReport.json" \
                            -Dcucumber.plugin="junit:${CUCUMBER_REPORTS_DIR}/cucumber.xml" \
                            -Dtest.suite=${params.TEST_SUITE}
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error "Test execution failed: ${e.message}"
                    }
                }
            }
        }

        stage('📊 Generate Reports') {
            when {
                expression { return !params.SKIP_TESTS }
            }
            steps {
                echo '📊 Test raporları oluşturuluyor...'
                
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }

                cucumber([
                    fileIncludePattern: '**/CucumberTestReport.json',
                    jsonReportDirectory: 'target/cucumber-reports',
                    sortingMethod: 'ALPHABETICAL'
                ])
            }
        }
    }

    post {
        always {
            echo '📝 Test sonuçları arşivleniyor...'
            
            archiveArtifacts artifacts: """
                target/surefire-reports/**/*.*,
                ${CUCUMBER_REPORTS_DIR}/**/*.*,
                ${ALLURE_RESULTS_DIR}/**/*.*,
                test-raporlari/*.html
            """, fingerprint: true, allowEmptyArchive: true

            junit '**/target/surefire-reports/TEST-*.xml'

            script {
                def testSummary = """
                    *Test Sonuçları:*
                    🌿 Branch: ${params.BRANCH_NAME}
                    🎯 Test Suite: ${params.TEST_SUITE}
                    🏷️ Tags: ${params.CUCUMBER_TAGS}
                    ✅ Başarılı: ${currentBuild.resultIsBetterOrEqualTo('SUCCESS')}
                    ❌ Başarısız: ${currentBuild.resultIsWorseOrEqualTo('FAILURE')}
                    🕒 Süre: ${currentBuild.durationString}
                """
                echo testSummary
            }

            cleanWs()
        }

        success {
            echo '✅ Testler başarıyla tamamlandı!'
        }

        failure {
            echo '❌ Test sürecinde hatalar oluştu!'
        }

        unstable {
            echo '⚠️ Test süreci kararsız durumda!'
        }
    }
} 