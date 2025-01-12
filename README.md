# Simple Banking API

Bu proje, temel bankacılık işlemlerini gerçekleştiren bir Spring Boot REST API'sidir.

## 🚀 Özellikler

* Hesap oluşturma ve yönetimi
* Para yatırma ve çekme işlemleri
* Fatura ödeme işlemleri
* Hesap bakiye ve işlem geçmişi sorgulama
* Hata yönetimi ve validasyon kontrolleri

## 📋 Teknik Gereksinimler

* Java 11
* Spring Boot 2.5.6
* PostgreSQL
* Gradle
* Lombok
* MapStruct

## ⚙️ Kurulum

1. Projeyi klonlayın


`git clone https://github.com/MutlucanKarinca/SimpleBanking/tree/master`


2. PostgreSQL veritabanını kurun ve bağlantı bilgilerini `application.properties` dosyasında güncelleyin

3. Projeyi derleyin ve çalıştırın

`./gradlew bootRun`


## 🎯 API Endpoints

### Account Controller (`/account/v1`)

* `POST /account/v1` - Yeni hesap oluşturma
* `GET /account/v1/{accountId}` - Hesap bilgilerini getirme
* `POST /account/v1/deposit/{accountId}` - Para yatırma
* `POST /account/v1/withdraw/{accountId}` - Para çekme

### Bill Payment Controller (`/bill-payment/v1`)

* `POST /bill-payment/v1/pay` - Fatura ödeme


## 🔧 Teknoloji Stack

* Spring Boot
* Spring Data JPA
* PostgreSQL
* Lombok
* MapStruct
* JUnit & Mockito

## 🛡️ Hata Kodları

* `BANKING_001` - Yetersiz bakiye
* `BANKING_002` - Hesap bulunamadı
* `BANKING_003` - Geçersiz işlem
* `BANKING_004` - Zorunlu alan eksik

