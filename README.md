# 🌟 Advanced Test Automation Framework
<div align="center">

[![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)](https://cucumber.io/)
[![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)](https://www.jenkins.io/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/)

<img src="https://raw.githubusercontent.com/Kodluyoruz/taskforce/main/git/figures/git.png" alt="Project Banner" width="400"/>

### 🎯 Enterprise-Grade Test Automation Solution
_Powered by Selenium WebDriver & Cucumber BDD_

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Code Coverage](https://img.shields.io/badge/coverage-95%25-brightgreen.svg)](https://github.com)

</div>

---

## 🎨 Features

<div align="center">

| 🚀 Feature | 📝 Description |
|------------|---------------|
| 🌐 Multi-Environment | QA, Staging, Production support |
| 🧪 Test Suites | Smoke & Regression testing |
| 📊 Rich Reporting | Allure reports with screenshots |
| 🔄 CI/CD Ready | Jenkins pipeline integration |
| 🛡️ Reliable | Stable and maintainable tests |
| 📱 Cross-Browser | Chrome, Firefox, Safari support |

</div>

## 🎯 Overview
This state-of-the-art test automation framework combines the power of Selenium WebDriver, Cucumber BDD, and Java to deliver a robust testing solution. With Jenkins CI/CD integration and comprehensive reporting via Allure, it provides enterprise-level quality assurance capabilities.

## ⚡ Quick Start

<details>
<summary>🔧 Prerequisites</summary>

- ☕ JDK 17
- 📦 Maven
- 🔄 Jenkins
- 🌿 Git

</details>

### 🚀 Installation

```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd CursorAndJenkinsFile

# Install dependencies
mvn clean install
```

## 🏗️ Architecture

```mermaid
graph TD
    A[Test Suites] -->|Cucumber| B[Step Definitions]
    B --> C[Page Objects]
    C --> D[Selenium WebDriver]
    D --> E[Web Application]
    F[Jenkins Pipeline] -->|Triggers| A
    G[Allure Reports] -->|Generated from| A
```

## 🛠️ Tech Stack

<div align="center">

| 🔧 Technology | 💫 Purpose |
|--------------|-----------|
| ![Java](https://img.shields.io/badge/Java-17-orange) | Core Language |
| ![Selenium](https://img.shields.io/badge/Selenium-4.16.1-green) | Web Automation |
| ![Cucumber](https://img.shields.io/badge/Cucumber-7.14.0-brightgreen) | BDD Framework |
| ![Jenkins](https://img.shields.io/badge/Jenkins-Latest-red) | CI/CD Pipeline |
| ![Allure](https://img.shields.io/badge/Allure-2.24.0-yellow) | Test Reporting |
| ![Maven](https://img.shields.io/badge/Maven-3.9-blue) | Build Tool |

</div>

## 📊 Project Structure

```ascii
📦 Test Framework
 ┣ 📂 src
 ┃ ┣ 📂 main/java
 ┃ ┃ ┣ 📂 pages        # Page Objects
 ┃ ┃ ┣ 📂 utils        # Utilities
 ┃ ┃ ┗ 📂 config       # Configurations
 ┃ ┗ 📂 test/java
 ┃   ┣ 📂 steps        # Step Definitions
 ┃   ┣ 📂 runners      # Test Runners
 ┃   ┗ 📂 hooks        # Test Hooks
 ┣ 📂 test-raporlari   # Test Reports
 ┣ 📜 Jenkinsfile      # Pipeline Config
 ┣ 📜 pom.xml          # Dependencies
 ┗ 📜 README.md        # Documentation
```

## 🔄 Jenkins Pipeline

<div align="center">

### 🎭 Pipeline Stages

```mermaid
graph LR
    A[Initialize] --> B[Build]
    B --> C[Test]
    C --> D[Report]
    D --> E[Notify]
```

</div>

### 🎮 Pipeline Parameters
| 🎯 Parameter | 🔍 Options | 📝 Description |
|-------------|-----------|---------------|
| `TEST_ENV` | QA/STAGING/PROD | Target environment |
| `TEST_SUITE` | Smoke/Regression | Test suite to execute |

## 📊 Reporting

<div align="center">

### 📈 Allure Report Features

| 📊 Feature | 🎯 Benefit |
|-----------|-----------|
| 📸 Screenshots | Visual failure analysis |
| ⏱️ Timeline | Test execution flow |
| 📉 Trends | Historical test results |
| 🔍 Details | Step-by-step execution |

</div>

## 🌟 Best Practices

### 📝 Coding Standards
- ✨ Follow Page Object Model
- 🧹 Clean code principles
- 📚 Comprehensive documentation
- 🧪 Test data separation
- 🔄 Regular maintenance

### 🤝 Contributing

1. 🌿 Fork the repository
2. 🔄 Create feature branch (`git checkout -b feature/AmazingFeature`)
3. ✨ Make changes
4. 📝 Commit (`git commit -m 'Add AmazingFeature'`)
5. 🚀 Push (`git push origin feature/AmazingFeature`)
6. 🎯 Open Pull Request

## 🎯 Support & Contact

<div align="center">

[![Email](https://img.shields.io/badge/Email-Support-blue?style=for-the-badge&logo=mail.ru)](mailto:support@company.com)
[![GitHub Issues](https://img.shields.io/badge/GitHub-Issues-red?style=for-the-badge&logo=github)](https://github.com/issues)
[![Documentation](https://img.shields.io/badge/Documentation-Wiki-green?style=for-the-badge&logo=bookstack)](https://github.com/wiki)

</div>

## 📜 License

<div align="center">

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

Copyright 2024 - Present

**Made with ❤️ by Your Team**

</div>

## Test Otomasyon Projesi

Bu proje, web uygulamalarının otomatik testlerini gerçekleştirmek için tasarlanmış bir test otomasyon framework'üdür. Selenium WebDriver, Cucumber BDD ve Jenkins CI/CD entegrasyonunu içermektedir.

## 🛠 Teknolojiler ve Araçlar

- **Java 17**: Ana programlama dili
- **Maven**: Bağımlılık yönetimi ve build aracı
- **Selenium WebDriver**: Web otomasyon framework'ü
- **Cucumber**: Behavior Driven Development (BDD) framework'ü
- **JUnit**: Test framework'ü
- **Jenkins**: CI/CD pipeline yönetimi
- **Allure Reports**: Test raporlama aracı
- **WebDriverManager**: Browser driver yönetimi
- **Logback**: Loglama framework'ü

## 📁 Proje Yapısı

```
.
├── src/
│   ├── main/java/         # Ana kaynak kodları
│   │   ├── pages/         # Page Object Model sınıfları
│   │   ├── utils/         # Yardımcı sınıflar
│   │   └── config/        # Konfigürasyon sınıfları
│   └── test/
│       ├── java/          # Test sınıfları
│       │   ├── runners/   # Cucumber test runner'ları
│       │   └── steps/     # Step definition sınıfları
│       └── resources/     # Test kaynakları
│           └── features/  # Cucumber feature dosyaları
├── test-reports/         # Test raporları dizini
├── Jenkinsfile          # Jenkins pipeline tanımı
└── pom.xml             # Maven yapılandırması
```

## 🔧 Kurulum

1. JDK 17'yi yükleyin
2. Maven'i yükleyin
3. Projeyi klonlayın
4. Bağımlılıkları yüklemek için:
   ```bash
   mvn clean install
   ```

## 🚀 Jenkins Pipeline Detaylı Açıklaması

Jenkins pipeline, aşağıdaki aşamalardan oluşmaktadır:

### 1. Başlangıç Yapılandırması
```groovy
tools {
    maven 'maven'
    jdk 'JDK17'
}
```
- Maven ve JDK 17 araçları tanımlanır
- Pipeline'da kullanılacak parametreler belirlenir:
  - TEST_ENV: QA, STAGING, PRE-PROD ortamları
  - TEST_SUITE: Smoke veya Regression test suite'leri

### 2. Pipeline Seçenekleri
```groovy
options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
    timestamps()
    disableConcurrentBuilds()
    skipDefaultCheckout()
}
```
- Son 5 build'in logları saklanır
- Her adıma zaman damgası eklenir
- Eşzamanlı build'ler engellenir
- Varsayılan checkout işlemi atlanır

### 3. Test Aşamaları

#### Initialize Stage
- Workspace temizlenir
- Kaynak kod checkout edilir

#### Run Tests Stage
- Maven ile test suite çalıştırılır
- Seçilen ortam ve test suite parametreleri kullanılır
- Hata durumunda build başarısız olarak işaretlenir

#### Generate Reports Stage
- Test raporları oluşturulur ve arşivlenir:
  - Cucumber raporları
  - Surefire raporları
  - Allure raporları

### 4. Post-Build İşlemleri

#### Always Blok
- Test raporları arşivlenir
- Allure raporu oluşturulur
- Cucumber raporu oluşturulur
- Workspace temizlenir

#### Success/Failure Blokları
- Test sonuçları detaylı olarak loglanır:
  - Branch bilgisi
  - Test suite
  - Ortam
  - Durum

## 📊 Test Raporları

Proje üç farklı raporlama mekanizması kullanır:

1. **Cucumber Raporları**: BDD senaryolarının detaylı raporları
2. **Allure Raporları**: Zengin içerikli, interaktif test raporları
3. **Surefire Raporları**: Maven test raporları

## 🔍 Test Ortamları

- **QA**: Geliştirme sonrası ilk test ortamı
- **STAGING**: Prodüksiyon benzeri test ortamı
- **PRE-PROD**: Prodüksiyon öncesi son test ortamı

## 📝 Test Suite'leri

1. **Smoke Tests**
   - Kritik fonksiyonların hızlı kontrolü
   - Her deployment sonrası çalıştırılır
   - Ortalama süre: 5-10 dakika

2. **Regression Tests**
   - Tüm fonksiyonların detaylı testi
   - Major release öncesi çalıştırılır
   - Ortalama süre: 30-60 dakika

## 🤝 Katkıda Bulunma

1. Fork'layın
2. Feature branch oluşturun
3. Değişikliklerinizi commit'leyin
4. Branch'inizi push'layın
5. Pull Request oluşturun

## 📫 İletişim

Sorularınız için issue açabilir veya projenin maintainer'ları ile iletişime geçebilirsiniz.
