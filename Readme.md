## How To Use This Project
Cara menggunakan project ini :
* Buka file local.properties
* Tuliskan varaibel apiKey=[VALUE], API Key bisa didapatkan dari https://api.themoviedb.org
* Rebuild project


## About Project
Alur penggunaan aplikasi ini :

* Halaman Home :
  Halaman ini merupakan halaman utama berisi 4 grup daftar film. Tiap item dari daftar tersebut
  dapat di tekan dan akan menuju ke halaman detail. Pull down untuk refresh halaman untuk mengupdate
  konten atau jika terjadi kesalahan.

* Halaman Genre :
  Berisi daftar film berdasarkan genre. Untuk defaultnya berisi film semua genre. Tekan tombol filter
  di sebelah kanan top app bar, akan muncul daftar genre yang bisa dipilih lebih dari satu. Tekan
  tombol filter untuk konfirmasi dan daftar film akan di muat ulang sesuai gabungan genre yang
  dipilih. Tekan salah satu konten untuk melihat detail. Pull down untuk refresh halaman

* Halaman Detail :
  Halaman detail informasi film yang di pilih. Berisi judul, genre, deskripsi, poster, trailer, dan
  review film. Video trailer dapat diputar dengan tekan tombol play.


## Build With
Teknologi yang digunakan :

* Kotlin
* Coroutines - Asynchronous task
* Dagger-Hilt - Dependency Injection
* Retrofit 2 dan Okhttp3 - Http request dan Logger
* Gson converter - konversi JSON ke java/kotlin object
* Glide - Image loader
* ViewPager 2
* Paging 3 - Pagination
* Navigation Component
* Youtube Player - from https://github.com/PierfrancescoSoffritti/android-youtube-player
* Design patter MVVM with clean architecture
* Remote data dari https://api.themoviedb.org


