pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK17'
    }

    environment {
        ALLURE_RESULTS_DIR = 'target/allure-results'
        CUCUMBER_REPORTS_DIR = 'target/cucumber-reports'
    }

    options {
        // Build geçmişini tut
        buildDiscarder(logRotator(numToKeepStr: '10'))
        // Zaman aşımı
        timeout(time: 1, unit: 'HOURS')
        // Ansi renk desteği
        ansiColor('xterm')
    }

    stages {
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
            steps {
                echo '🧪 Testler çalıştırılıyor...'
                script {
                    try {
                        sh """
                            mvn test \
                            -Dcucumber.plugin="pretty,html:${CUCUMBER_REPORTS_DIR}/cucumber-reports.html" \
                            -Dcucumber.plugin="json:${CUCUMBER_REPORTS_DIR}/cucumber.json" \
                            -Dcucumber.plugin="junit:${CUCUMBER_REPORTS_DIR}/cucumber.xml"
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error "Test execution failed: ${e.message}"
                    }
                }
            }
        }

        stage('📊 Generate Reports') {
            steps {
                echo '📊 Test raporları oluşturuluyor...'
                // Allure raporu oluştur
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: "${ALLURE_RESULTS_DIR}"]]
                ])

                // Cucumber raporu oluştur
                cucumber([
                    fileIncludePattern: '**/cucumber.json',
                    jsonReportDirectory: "${CUCUMBER_REPORTS_DIR}",
                    sortingMethod: 'ALPHABETICAL'
                ])
            }
        }
    }

    post {
        always {
            echo '📝 Test sonuçları arşivleniyor...'
            
            // Test raporlarını arşivle
            archiveArtifacts artifacts: """
                target/surefire-reports/**/*.*,
                ${CUCUMBER_REPORTS_DIR}/**/*.*,
                ${ALLURE_RESULTS_DIR}/**/*.*,
                test-raporlari/*.html
            """, fingerprint: true, allowEmptyArchive: true

            // JUnit raporlarını topla
            junit '**/target/surefire-reports/TEST-*.xml'

            // Test sonuçlarını Slack'e gönder
            script {
                def testSummary = """
                    *Test Sonuçları:*
                    ✅ Başarılı: ${currentBuild.resultIsBetterOrEqualTo('SUCCESS')}
                    ❌ Başarısız: ${currentBuild.resultIsWorseOrEqualTo('FAILURE')}
                    🕒 Süre: ${currentBuild.durationString}
                """
                echo testSummary
            }

            // Workspace'i temizle
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