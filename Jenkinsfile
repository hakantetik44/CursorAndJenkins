pipeline {
    agent any

    tools {
        maven 'MAVEN3'    // Jenkins'te tanımladığımız Maven adı
        jdk 'JDK17'      // Jenkins'te tanımladığımız JDK adı
    }

    stages {
        stage('Checkout') {
            steps {
                // Git repository'den kodu çek
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                // Maven clean
                sh 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                // Maven ile testleri çalıştır
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            // Test raporlarını arşivle
            archiveArtifacts artifacts: 'target/surefire-reports/**/*.*', 
                           fingerprint: true, 
                           allowEmptyArchive: true

            // HTML test raporlarını arşivle
            archiveArtifacts artifacts: 'test-raporlari/*.html', 
                           fingerprint: true, 
                           allowEmptyArchive: true

            // JUnit test raporlarını topla
            junit '**/target/surefire-reports/TEST-*.xml'

            // Workspace'i temizle
            cleanWs()
        }

        success {
            echo 'Testler başarıyla tamamlandı!'
        }

        failure {
            echo 'Test sürecinde hatalar oluştu!'
        }
    }
} 