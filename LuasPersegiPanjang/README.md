# LuasPersegiPanjang

Aplikasi JavaFX sederhana untuk menghitung **luas persegi panjang**.  
User memasukkan panjang dan lebar, kemudian menekan tombol "Hitung" untuk melihat hasil.

## Struktur Project

src/main/java/

├─ app/

│ ├─ MainApp.java

│ └─ LuasController.java

└─ module-info.java


## Cara Menjalankan

1. Pastikan JavaFX SDK sudah ter-download dan path-nya diketahui.
2. Buka project di IntelliJ IDEA.
3. Tambahkan JavaFX SDK di **Project Structure → Libraries**.
4. Edit Run Configuration untuk `MainApp`: --module-path "D:\path\to\javafx-sdk-XX\lib" --add-modules javafx.controls

5. Run `MainApp`.

## Kelas Utama

### `MainApp`
- `start(Stage stage)` : Membuat scene dan menampilkan stage utama.
- `main(String[] args)` : Menjalankan aplikasi JavaFX.

### `LuasController`
- `LuasController()` : Konstruktor, membuat GUI dan tombol hitung.
- `hitung()` : Menghitung luas persegi panjang, menampilkan hasil, menangani input invalid.
- `getView()` : Mengembalikan VBox GUI untuk ditampilkan di scene.
