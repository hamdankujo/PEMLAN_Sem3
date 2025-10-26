package app;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Controller untuk aplikasi JavaFX menghitung luas persegi panjang.
 * Menyediakan input panjang dan lebar, tombol hitung, dan label hasil.
 */
public class LuasController {

    private TextField tPanjang = new TextField();
    private TextField tLebar = new TextField();
    private Label hasil = new Label();
    private VBox view;

    /**
     * Konstruktor untuk membuat tampilan GUI dan menambahkan event handler tombol hitung.
     */
    public LuasController(){
        Button hitung = new Button("Hitung");
        hitung.setOnAction(e -> hitung());

        view = new VBox(10,
                new Label("Panjang"), tPanjang,
                new Label("Lebar"), tLebar,
                hitung, hasil
        );
        view.setPadding(new Insets(20));
    }

    /**
     * Menghitung luas persegi panjang berdasarkan input panjang dan lebar.
     * Menampilkan hasil di label atau pesan error jika input tidak valid.
     */
    private void hitung(){
        try{
            double p = Double.parseDouble(tPanjang.getText());
            double l = Double.parseDouble(tLebar.getText());
            hasil.setText("Luas = " + (p*l));
        }catch(Exception ex){
            hasil.setText("Input tidak valid");
        }
    }

    /**
     * Mengembalikan view VBox untuk ditampilkan di scene JavaFX.
     * @return VBox berisi semua komponen GUI
     */
    public VBox getView(){
        return view;
    }
}
