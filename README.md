# TravelKu — Sistem Pemesanan Perjalanan

Aplikasi konsol Java yang mensimulasikan platform pemesanan perjalanan (penerbangan & hotel).

---

## Cara Menjalankan

### Prasyarat
- **Java 17** atau lebih tinggi (wajib, untuk fitur `sealed class` dan `pattern matching`)
- Cek versi: `java -version` dan `javac -version`

### Compile

Dari folder root proyek (`TK-1/`):

```bash
# macOS / Linux
find src -name "*.java" | xargs javac -d out

# Windows (Command Prompt)
for /r src %f in (*.java) do @echo %f >> sources.txt
javac -d out @sources.txt
del sources.txt
```

### Jalankan

```bash
java -cp out Main
```

---


## Deskripsi Kelas

### `Main`
Entry point. Membuat satu `TravelApp`, lalu menjalankan loop menu dengan `switch` expression. Input angka dijaga dengan `try-catch InputMismatchException`.

### `TravelApp`
Pusat logika bisnis. Menyimpan tiga `ArrayList`:
- `flights` — inventori penerbangan
- `hotels` — inventori hotel
- `reservations` — semua reservasi aktif

Menyediakan method untuk setiap menu: search, book, cancel, dan view.

### `Flight`
Entity data penerbangan. Menyimpan nomor penerbangan, rute, tanggal, ketersediaan kursi, dan harga per kursi.

### `Hotel`
Entity data hotel. Menyimpan ID, nama, lokasi, tanggal check-in/out, ketersediaan kamar, dan harga per malam.

### `Reservation` *(sealed abstract)*
Superclass untuk semua reservasi. Menyimpan field bersama: `confirmationNumber`, `customerName`, `customerContact`. Mendeklarasikan method abstrak `display()` dan mewarisi kontrak `Bookable`.

### `FlightReservation` *(extends Reservation)*
- `book()` — generate nomor konfirmasi, kurangi `availableSeats` sesuai `passengerCount`
- `cancel()` — kembalikan `availableSeats`
- `display()` — cetak kotak konfirmasi; total harga = `passengerCount × pricePerSeat`

### `HotelReservation` *(extends Reservation)*
- `book()` — generate nomor konfirmasi, kurangi `availableRooms` sebesar 1
- `cancel()` — kembalikan `availableRooms`
- `display()` — cetak kotak konfirmasi; total harga = `jumlah malam × pricePerNight`

### `Bookable` *(interface)*
Mendefinisikan kontrak: `book()`, `cancel()`, `getConfirmationNumber()`.

### `ConfirmationNumberGenerator`
Utility class `final`. Method statis `generate()` mengembalikan angka acak 6 digit (100000–999999).

### `ReservationNotFoundException`
Custom exception yang dilempar oleh `cancelReservation()` jika nomor konfirmasi tidak ditemukan.

## Data Sampel (dimuat saat aplikasi start)

| Kode | Rute | Tanggal | Kursi | Harga/kursi |
|------|------|---------|-------|-------------|
| GA-101 | Jakarta → Bali | 2026-08-01 | 50 | Rp 1.500.000 |
| QG-201 | Jakarta → Surabaya | 2026-08-01 | 80 | Rp 800.000 |
| ID-301 | Bali → Lombok | 2026-08-02 | 30 | Rp 600.000 |
| GA-401 | Surabaya → Makassar | 2026-08-03 | 60 | Rp 1.200.000 |
| SJ-501 | Jakarta → Yogyakarta | 2026-08-01 | 100 | Rp 700.000 |

| Kode | Hotel | Lokasi | Check-in | Check-out | Kamar | Harga/malam |
|------|-------|--------|----------|-----------|-------|-------------|
| HTL-001 | Hotel Mulia Jakarta | Jakarta | 2026-08-01 | 2026-08-03 | 10 | Rp 800.000 |
| HTL-002 | Potato Head Beach Club | Bali | 2026-08-01 | 2026-08-04 | 5 | Rp 2.500.000 |
| HTL-003 | Sheraton Surabaya | Surabaya | 2026-08-01 | 2026-08-02 | 15 | Rp 600.000 |
| HTL-004 | Tentrem Yogyakarta | Yogyakarta | 2026-08-01 | 2026-08-03 | 8 | Rp 1.100.000 |
| HTL-005 | Aston Makassar | Makassar | 2026-08-01 | 2026-08-03 | 20 | Rp 500.000 |

---

## Test Cases

### Positif

| # | Method yang Ditest | Input | Output yang Diharapkan |
|---|---|---|---|
| P1 | `searchFlights` | Asal: `Jakarta`, Tujuan: `Bali`, Tanggal: `2026-08-01`, Penumpang: `1` | List tidak kosong, elemen pertama adalah GA-101 |
| P2 | `searchFlights` (case-insensitive) | Asal: `jakarta`, Tujuan: `bali`, Tanggal: `2026-08-01`, Penumpang: `1` | Jumlah dan isi hasil sama dengan P1 |
| P3 | `searchHotels` | Kota: `Bali`, Check-in: `2026-08-01`, Check-out: `2026-08-04`, Tamu: `2` | List tidak kosong, elemen pertama adalah HTL-002 |
| P4 | `FlightReservation.book()` | Flight 50 kursi, 2 penumpang | `availableSeats == 48`, nomor konfirmasi 6 digit (100000–999999) |
| P5 | `FlightReservation.book()` → `cancel()` | Flight 50 kursi, 3 penumpang | Setelah cancel, `availableSeats` kembali ke 50 |

### Negatif

| # | Method yang Ditest | Input | Output yang Diharapkan |
|---|---|---|---|
| N1 | `searchFlights` | Asal: `Solo`, Tujuan: `Manado`, Tanggal: `2026-08-01`, Penumpang: `1` | List kosong (rute tidak ada) |
| N2 | `searchFlights` | Asal: `Jakarta`, Tujuan: `Bali`, Tanggal: `2026-08-01`, Penumpang: `999` | List kosong (kursi tidak cukup) |
| N3 | `searchHotels` | Kota: `Bandung`, Check-in: `2026-08-01`, Check-out: `2026-08-03`, Tamu: `1` | List kosong (kota tidak ada di data) |
| N4 | `cancelReservation` | Nomor konfirmasi: `999999` | Throws `ReservationNotFoundException` |
| N5 | `searchHotels` | Kota: `Jakarta`, Check-in: `2026-08-01`, Check-out: `2026-08-05`, Tamu: `1` | List kosong (tanggal check-out tidak cocok dengan data) |

---

## Contoh Penggunaan

```
Menu 1 — Cari Penerbangan:
  Kota asal           : Jakarta
  Kota tujuan         : Bali
  Tanggal (YYYY-MM-DD): 2026-08-01
  Jumlah penumpang    : 2
  → Tampil GA-101, pilih 1, isi nama & kontak
  → Kotak konfirmasi cetak dengan total Rp 3.000.000

Menu 2 — Cari Hotel:
  Kota                      : Jakarta
  Tanggal check-in  (YYYY-MM-DD): 2026-08-01
  Tanggal check-out (YYYY-MM-DD): 2026-08-03
  Jumlah tamu               : 2
  → Tampil HTL-001, pilih 1, isi nama & kontak
  → Kotak konfirmasi cetak dengan total Rp 1.600.000 (2 malam × Rp 800.000)

Menu 3 — Lihat Semua Pemesanan:
  → Cetak kedua reservasi di atas

Menu 4 — Batalkan:
  → Masukkan nomor konfirmasi dari salah satu pemesanan
  → Reservasi dihapus, kursi/kamar dikembalikan
```
