Feature: Gezi Sefası Arama Fonksiyonu
  Kullanıcı olarak Gezi Sefası sitesinde arama yapabilmeliyim

  @smoke @arama
  Scenario: Anasayfada başarılı arama yapma
    Given Kullanıcı Gezimanya anasayfasına gider
    When Arama kutusuna "Kapadokya" yazar
 