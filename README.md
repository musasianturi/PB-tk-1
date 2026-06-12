# TravelKu — Sistem Pemesanan Perjalanan

Aplikasi konsol Java yang mensimulasikan platform pemesanan perjalanan (terinspirasi Traveloka/Tiket.com). Dibangun untuk tugas kuliah **Introduction to Programming for Business — TK-1**.

---

## Cara Setup & Menjalankan

### Prasyarat
- **Java 17** atau lebih tinggi (wajib, untuk fitur `sealed class` dan `pattern matching`)
- Cek versi: `java -version` dan `javac -version`

### Compile

Dari folder root proyek (`TK-1/`), jalankan:

```bash
# macOS / Linux
find src -name "*.java" -print0 | xargs -0 javac -d bin

# Windows (Command Prompt)
for /r src %f in (*.java) do @echo %f >> sources.txt
javac -d bin @sources.txt
del sources.txt
```

### Jalankan

```bash
java -cp bin Main
```

### Struktur Output Direktori

Setelah compile, folder `bin/` akan berisi semua file `.class` yang siap dijalankan.

---

## Struktur Proyek

```
TK-1/
├── src/
│   ├── Main.java                    [FULL]  Entry point — main loop & routing menu
│   ├── app/
│   │   └── TravelApp.java           [TODO]  Logika bisnis utama — ANGGOTA 3
│   ├── model/
│   │   ├── Reservation.java         [FULL]  sealed abstract class — superclass reservasi
│   │   ├── Flight.java              [TODO]  Entity penerbangan — ANGGOTA 1
│   │   ├── Hotel.java               [TODO]  Entity hotel — ANGGOTA 1
│   │   ├── FlightReservation.java   [TODO]  Reservasi penerbangan — ANGGOTA 2
│   │   └── HotelReservation.java    [TODO]  Reservasi hotel — ANGGOTA 2
│   ├── interfaces/
│   │   └── Bookable.java            [FULL]  Interface kontrak pemesanan
│   ├── exceptions/
│   │   └── ReservationNotFoundException.java  [FULL]  Custom exception
│   └── util/
│       └── ConfirmationNumberGenerator.java   [FULL]  Generator nomor konfirmasi
└── README.md
```

**[FULL]** = sudah selesai, jangan diubah kecuali ada alasan kuat  
**[TODO]** = bagian yang harus diimplementasikan tim

---

## Pembagian Tugas Tim

> Setiap anggota mengerjakan file yang ditandai, lalu merge/integrasikan bersama.

### Anggota 1 — Entity Classes (Model Layer)

| File | Yang Dikerjakan | Konsep Java |
|------|----------------|-------------|
| `src/model/Flight.java` | Constructor, semua getter, setter `setAvailableSeats()`, `toString()` | Encapsulation, Constructor |
| `src/model/Hotel.java` | Constructor, semua getter, setter `setAvailableRooms()`, `toString()` | Encapsulation, Constructor |

**Tips untuk Anggota 1:**
- Semua field sudah dideklarasikan, tinggal isi body method yang ada komentar `// TODO`
- Constructor: assign `this.fieldName = parameterName` untuk setiap field
- Getter: `return fieldName;`
- Setter: `this.fieldName = parameterName;`
- `toString()`: gunakan `String.format()` untuk format yang rapi

---

### Anggota 2 — Reservation Classes (Inheritance & Polymorphism)

| File | Yang Dikerjakan | Konsep Java |
|------|----------------|-------------|
| `src/model/FlightReservation.java` | Constructor, `book()`, `cancel()`, `display()`, getter | Inheritance, Polymorphism, Override |
| `src/model/HotelReservation.java` | Constructor, `book()`, `cancel()`, `display()`, getter | Inheritance, Polymorphism, Override |

**Tips untuk Anggota 2:**
- `book()`: generate nomor konfirmasi + update seats/rooms
- `cancel()`: kembalikan seats/rooms ke kondisi sebelum booking
- `display()`: tampilkan semua detail reservasi dalam format rapi (lihat contoh di komentar tiap file)
- Field `confirmationNumber`, `customerName`, `customerContact` diwarisi dari `Reservation` — akses langsung dengan `this.confirmationNumber`
- Gunakan `ConfirmationNumberGenerator.generate()` untuk nomor konfirmasi

**Catatan Pattern Matching untuk Anggota 2:**
Kelas ini akan dikenali di `cancelReservation()` dengan sintaks:
```java
if (res instanceof FlightReservation fr) {
    fr.cancel(); // 'fr' sudah bertipe FlightReservation, tidak perlu cast manual
}
```

---

### Anggota 3 — Business Logic (TravelApp)

| File | Yang Dikerjakan | Konsep Java |
|------|----------------|-------------|
| `src/app/TravelApp.java` | `initSampleData()`, `searchFlights()`, `searchAndBookFlight()`, `bookFlight()`, `searchHotels()`, `searchAndBookHotel()`, `bookHotel()`, `cancelReservation()`, `promptCancelReservation()`, `viewAllReservations()` | Stream, Lambda, Exception handling, Pattern matching |

**Tips untuk Anggota 3:**

Urutan implementasi yang disarankan:

1. **`initSampleData()`** — tambah 5+ penerbangan dan 5+ hotel ke ArrayList. Ini yang pertama harus jalan agar fitur lain bisa ditest.

2. **`searchFlights()`** — gunakan stream + lambda:
   ```java
   return flights.stream()
       .filter(f -> f.getOrigin().equalsIgnoreCase(origin))
       .filter(f -> f.getDestination().equalsIgnoreCase(destination))
       .filter(f -> f.getDate().equals(date))
       .filter(f -> f.getAvailableSeats() >= passengerCount)
       .collect(Collectors.toList());
   ```

3. **`searchAndBookFlight()`** — alur UI: minta input → tampilkan hasil → pilih → input data → panggil `bookFlight()`

4. **`bookFlight()`**:
   ```java
   FlightReservation res = new FlightReservation(flight, passengerCount, name, contact);
   res.book();
   reservations.add(res);
   res.display();
   ```

5. **`searchHotels()`** dan **`searchAndBookHotel()`** — mirip alur penerbangan

6. **`cancelReservation()`** — gunakan pattern matching:
   ```java
   for (Reservation res : reservations) {
       if (res.getConfirmationNumber() == confirmationNumber) {
           if (res instanceof FlightReservation fr) { fr.cancel(); }
           else if (res instanceof HotelReservation hr) { hr.cancel(); }
           reservations.remove(res);
           return;
       }
   }
   throw new ReservationNotFoundException(confirmationNumber);
   ```

7. **`viewAllReservations()`** — iterasi `reservations`, panggil `.display()` tiap elemen

---

## Konvensi Kode

Ikuti konvensi Java standar agar kode konsisten antar anggota tim:

| Elemen | Konvensi | Contoh |
|--------|----------|--------|
| Nama kelas | `PascalCase` | `FlightReservation`, `TravelApp` |
| Nama method & variabel | `camelCase` | `searchFlights()`, `passengerCount` |
| Nama konstanta | `UPPER_SNAKE_CASE` | `MAX_RETRIES`, `DEFAULT_PRICE` |
| Nama package | huruf kecil semua | `model`, `app`, `interfaces` |
| Komentar | Bahasa Indonesia atau Inggris, konsisten | `// Cari penerbangan sesuai kriteria` |
| Indentasi | 4 spasi | (gunakan IDE auto-format) |

---

## Alur Program (Flowchart Konseptual)

```
Program Start
     │
     ▼
  [Main Loop] ─── Tampilkan Menu ─── Baca Pilihan
     │
     ├── 1: Cari Penerbangan ──► minta input ──► searchFlights() ──► tampilkan ──► bookFlight()
     │
     ├── 2: Cari Hotel ────────► minta input ──► searchHotels() ───► tampilkan ──► bookHotel()
     │
     ├── 3: Lihat Semua ───────► viewAllReservations() ──► display() tiap reservasi
     │
     ├── 4: Batalkan ──────────► minta nomor ──► cancelReservation() ──► remove dari list
     │                                                    │
     │                                               [tidak ada] ──► ReservationNotFoundException
     │
     └── 0: Keluar ───────────► Program selesai
```

---

## Hierarki Kelas (UML Sederhana)

```
<<interface>>
  Bookable
  + book(): void
  + cancel(): void
  + getConfirmationNumber(): int
      ▲
      │ implements
      │
sealed abstract class
  Reservation (implements Bookable)
  # confirmationNumber: int
  # customerName: String
  # customerContact: String
  + display(): void  [abstract]
      ▲
      ├────────────────────────┐
      │                        │
  FlightReservation        HotelReservation
  - flight: Flight         - hotel: Hotel
  - passengerCount: int    - guestCount: int
  + book()                 + book()
  + cancel()               + cancel()
  + display()              + display()


  Flight                   Hotel
  - flightNumber: String   - hotelId: String
  - origin: String         - name: String
  - destination: String    - location: String
  - date: String           - checkInDate: String
  - availableSeats: int    - checkOutDate: String
  - pricePerSeat: double   - availableRooms: int
  + getters/setters        - pricePerNight: double
  + toString()             + getters/setters
                           + toString()

final class
  ConfirmationNumberGenerator
  + generate(): int  [static]

class
  ReservationNotFoundException extends Exception
  + getConfirmationNumber(): int
```

---

## Test Cases

Setelah semua implementasi selesai, pastikan skenario berikut berjalan dengan benar:

| # | Skenario | Input | Expected Output |
|---|----------|-------|-----------------|
| 1 | Cari penerbangan — ada | Origin: Jakarta, Dest: Bali, Date: 2025-08-01, Penumpang: 2 | Daftar penerbangan ditampilkan |
| 2 | Cari penerbangan — tidak ada | Origin: Solo, Dest: Manado, Date: 2025-08-01, Penumpang: 1 | "Tidak ada penerbangan tersedia" |
| 3 | Pesan penerbangan | Pilih no.1, Nama: Budi, Kontak: 08123 | Nomor konfirmasi 6 digit tampil |
| 4 | Cari hotel — ada | Lokasi: Bali, Check-in: 2025-08-01, Check-out: 2025-08-04, Tamu: 2 | Daftar hotel ditampilkan |
| 5 | Pesan hotel | Pilih no.1, Nama: Siti, Kontak: 08987 | Nomor konfirmasi 6 digit tampil |
| 6 | Lihat semua pemesanan | Menu 3 | Semua reservasi dari tes 3 & 5 tampil |
| 7 | Batalkan — valid | Nomor konfirmasi dari tes 3 | "Reservasi berhasil dibatalkan" |
| 8 | Batalkan — tidak ada | 999999 (angka acak) | Pesan ReservationNotFoundException |
| 9 | Input tidak valid | Ketik "abc" di field angka | Pesan error, program tidak crash |
| 10 | Keluar | Menu 0 | "Terima kasih" & program exit |

---

## Fitur Java yang Harus Terlihat di Kode

Pastikan semua konsep berikut muncul dalam implementasi akhir (untuk penilaian rubrik):

- [ ] **I/O**: `Scanner` untuk input, `System.out.println` untuk output
- [ ] **Kelas & Objek**: `Flight`, `Hotel`, instansiasi dengan `new`
- [ ] **Kontrol**: `while(true)` loop, `switch`, `if-else`
- [ ] **Koleksi**: `ArrayList<Flight>`, `ArrayList<Hotel>`, `ArrayList<Reservation>`
- [ ] **Stream & Lambda**: `.stream().filter(f -> ...).collect()`
- [ ] **Enkapsulasi**: semua field `private`, diakses via `getter/setter`
- [ ] **Pewarisan**: `FlightReservation extends Reservation`
- [ ] **Polimorfisme**: `reservations.forEach(r -> r.display())`
- [ ] **Abstract class**: `abstract class Reservation` dengan `abstract display()`
- [ ] **Interface**: `Bookable` dengan `book()` dan `cancel()`
- [ ] **Exception handling**: `try-catch InputMismatchException`, `ReservationNotFoundException`
- [ ] **Pattern Matching**: `if (res instanceof FlightReservation fr)`
- [ ] **Sealed class**: `sealed abstract class Reservation permits ...`
- [ ] **Final class**: `final class ConfirmationNumberGenerator`
