# Insider One İşe Alım Sayfası Otomasyonu

Insider One ana sayfasından Career sayfası, açık pozisyonlar, filtreleme (Istanbul / Quality Assurance) ve Lever başvuru formuna kadar akışı test eden **Java + Selenium + Page Object Model (POM)** projesi. BDD framework kullanılmaz.

## Gereksinimler

- Java 11+
- Maven 3.6+
- Chrome tarayıcı (ChromeDriver WebDriverManager ile otomatik indirilir)

## Proje Yapısı

- `src/main/java/com/insiderone/config/` — Konfigürasyon
- `src/main/java/com/insiderone/pages/` — Page Object Model sayfa sınıfları
- `src/main/java/com/insiderone/utils/` — Screenshot yardımcısı
- `src/test/java/com/insiderone/` — JUnit 5 testleri (BaseTest, CareerFlowTest)
- `screenshots/` — Başarısız testlerde alınan ekran görüntüleri

## Testleri Çalıştırma

```bash
mvn clean test
```

Belirli bir test sınıfı:

```bash
mvn test -Dtest=CareerFlowTest
```

Sadece ana sayfa doğrulama testi (Adım 1):

```bash
mvn test -Dtest=CareerFlowTest#testVerifyInsiderOneHomePage
```

## Test Akışı

1. insiderone.com ana sayfasına gidilir ve doğrulanır.
2. "We're hiring" tıklanır; Career sayfası ve "Explore open roles" doğrulanır.
3. "Explore open roles" tıklanır; Software Development altında "Open Positions" linki tıklanır.
4. Location: Istanbul, Turkiye; Team: Quality Assurance seçilir; liste görüntülenir.
5. Tüm ilanlarda pozisyonda "Quality Assurance", Location'da "Istanbul, Turkiye" kontrol edilir.
6. "Apply" tıklanır; Lever Application Form sayfasına yönlendirme doğrulanır.

Başarısız adımlarda ekran görüntüsü `screenshots/` klasörüne timestamp ile kaydedilir.
