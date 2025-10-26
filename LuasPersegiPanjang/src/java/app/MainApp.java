package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class untuk menjalankan aplikasi JavaFX menghitung luas persegi panjang.
 */
public class MainApp extends Application {

    /**
     * Method utama JavaFX untuk membuat stage dan scene.
     * @param stage stage utama aplikasi
     */
    @Override
    public void start(Stage stage) {
        LuasController c = new LuasController();
        stage.setScene(new Scene(c.getView(), 260, 260));
        stage.setTitle("Luas Persegi Panjang");
        stage.show();
    }

    /**
     * Main method untuk menjalankan aplikasi JavaFX.
     * @param args argumen command line
     */
    public static void main(String[] args) {
        launch(args);
    }
}
