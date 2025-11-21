package com.ieselgrao.gametofork;

import com.ieselgrao.gametofork.model.GameModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;
import java.io.IOException;

public class MainApplication extends Application {

    private static Stage primaryStage;
    private static GameModel gameModel; // Estado del juego compartido
    private static MediaPlayer mediaPlayer; // 🎵 Variable para la música

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        gameModel = new GameModel(); // Inicializa el modelo de juego
        stage.setTitle("Círculos en Caída");
        stage.setResizable(false);

        switchToStartView(); // Vista inicial
        stage.show();
    }

    // --- Cambios importantes: métodos para controlar la música ---
    private static void initMusic() {
        if (mediaPlayer == null) {
            try {
                Media media = new Media(MainApplication.class.getResource("/sounds/musica_fondo.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repite en bucle
                mediaPlayer.setVolume(0.5); // Volumen medio
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startMusic() {
        initMusic();
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // reinicia desde el principio
            mediaPlayer.play();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    public static void resumeMusic() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    public static void toggleMute() {
        if (mediaPlayer != null) mediaPlayer.setMute(!mediaPlayer.isMute());
    }

    // --- Cambio de vistas ---
    public static void switchToStartView() throws IOException {
        stopMusic(); // No música en la pantalla de inicio
        loadScene("start-view.fxml", 600, 600);
    }

    public static void switchToGameView() throws IOException {
        gameModel.resetGame(); // Reinicia el estado del juego
        loadScene("game-view.fxml", 600, 600);
        startMusic(); // Música empieza desde el inicio
    }

    public static void switchToGameOverView() throws IOException {
        loadScene("game-over-view.fxml", 600, 600);
        stopMusic(); // Para música al perder
    }

    private static void loadScene(String fxmlFile, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        primaryStage.setScene(scene);
    }

    public static GameModel getGameModel() {
        return gameModel;
    }

    public static void main(String[] args) {
        launch();
    }
}
