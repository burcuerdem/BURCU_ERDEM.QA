---
name: Test Case Sayısı ve İster Uyumu
overview: Taslağa göre kaç test case olması gerektiği, gereksiz case'ın kaldırılması ve projenin isteri karşılık durumunun değerlendirilmesi.
todos: []
isProject: false
---

# Test Case Sayısı ve İster Uyumu Planı

## Taslağa Göre Kaç Case Yazılabilir?

Taslak **"en az bir test case yazılmalıdır"** diyor ve 6 adımlı tek bir akış tanımlıyor. Yani:

- **Minimum:** 1 test case (tüm 6 adımı kapsayan tek E2E test).
- **Ek case’ler:** Taslak ek senaryo (negatif, sadece career, sadece filtre vb.) istemediği için zorunlu değil.

Ayrı ayrı adım testleri (sadece Adım 1, sadece Adım 2…) yazılabilir ama taslak tek akış verdiği ve **"gereksiz case varsa sil"** denildiği için bunlar gereksiz sayılır; tek E2E yeterli.

**Sonuç:** Taslağa göre **1 test case** (tüm akışı kapsayan) yeterli ve profesyonel. İsterseniz ileride “smoke” gibi ikinci bir case eklenebilir; şu an için 1 case hedeflenmeli.

---

## Mevcut Durum ve Gereksiz Case

Şu an [CareerFlowTest.java](c:\Users\eness\OneDrive\Belgeler\insederone automation\src\test\java\com\insiderone\CareerFlowTest.java) içinde **2 test** var:


| Test                           | Kapsam                                | Değerlendirme                                                                                                                            |
| ------------------------------ | ------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `testVerifyInsiderOneHomePage` | Sadece Adım 1 (ana sayfa aç, doğrula) | **Gereksiz.** Adım 1, `testCareerFlowFull` içinde zaten yapılıyor ve assert ediliyor. Aynı şeyi iki kez test etmek gereksiz case üretir. |
| `testCareerFlowFull`           | 6 adımın tamamı                       | **Gerekli.** Taslağın istediği tek E2E case bu.                                                                                          |


**Öneri:** `testVerifyInsiderOneHomePage` kaldırılsın; sadece **1 case** kalsın: tüm akışı kapsayan E2E test. İsim olarak `testCareerFlowFull` kalabilir veya daha açıklayıcı bir isim (örn. `test_FromHomePageToLeverApplicationForm`) verilebilir.

---

## Proje İsteri Karşılığı


| Gereksinim                                                    | Karşılık   | Açıklama                                                                                                                                                                                                                 |
| ------------------------------------------------------------- | ---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| En az bir test case                                           | Karşılıyor | Şu an 2 var; 1’e indirince da karşılıyor.                                                                                                                                                                                |
| Java veya Python + Selenium                                   | Karşılıyor | Java + Selenium + JUnit 5 kullanılıyor.                                                                                                                                                                                  |
| BDD kullanılmamalı                                            | Karşılıyor | Cucumber/Quantum/Codeception yok.                                                                                                                                                                                        |
| Başarısız adımda ekran görüntüsü                              | Karşılıyor | [ScreenshotOnFailureExtension](c:\Users\eness\OneDrive\Belgeler\insederone automation\src\test\java\com\insiderone\ScreenshotOnFailureExtension.java) ile fail’de SS alınıyor; Apply sonrası başarı SS’i de var.         |
| Page Object Model (POM)                                       | Karşılıyor | [BasePage](c:\Users\eness\OneDrive\Belgeler\insederone automation\src\main\java\com\insiderone\pages\BasePage.java), HomePage, CareersPage, OpenPositionsPage, LeverJobsPage mevcut; locator ve akış sayfa sınıflarında. |
| Adım 1: Ana sayfa ziyaret ve doğrulama                        | Karşılıyor | `testCareerFlowFull` içinde.                                                                                                                                                                                             |
| Adım 2: We're hiring → Career + Explore open roles            | Karşılıyor | Aynı testte.                                                                                                                                                                                                             |
| Adım 3: Explore open roles → Software Dev → xx Open Positions | Karşılıyor | Aynı testte.                                                                                                                                                                                                             |
| Adım 4: Location Istanbul / Team QA + liste doğrulama         | Karşılıyor | Aynı testte.                                                                                                                                                                                                             |
| Adım 5: İlanlarda QA ve Istanbul, Turkiye kontrolü            | Karşılıyor | Aynı testte.                                                                                                                                                                                                             |
| Adım 6: Apply → Lever Application Form doğrulama              | Karşılıyor | Aynı testte; `section-wrapper page-full-width` ile form yüklenmesi bekleniyor, SS alınıyor.                                                                                                                              |


**Özet:** Proje taslak isterlerini karşılıyor. Tek iyileştirme: gereksiz case’ı kaldırıp **1 profesyonel E2E case** ile bırakmak.

---

## Yapılacak Tek Değişiklik

- [CareerFlowTest.java](c:\Users\eness\OneDrive\Belgeler\insederone automation\src\test\java\com\insiderone\CareerFlowTest.java) içinden `**testVerifyInsiderOneHomePage`** metodunu (ve ilgili `@Test` annotasyonunu) silmek.
- Sadece `**testCareerFlowFull`** kalacak; proje “en az bir test case” ve “gereksiz case yok” kriterlerine uygun, profesyonel hale gelir.

İsterseniz `testCareerFlowFull` ismi `test_FromHomeToLeverApplicationForm` veya `testCareerFlow_E2E` gibi daha açıklayıcı bir isimle değiştirilebilir (opsiyonel).