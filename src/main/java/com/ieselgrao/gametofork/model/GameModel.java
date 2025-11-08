package com.ieselgrao.gametofork.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameModel {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty lives = new SimpleIntegerProperty(3);
    private static final int MAX_LIVES = 3;


    private double fallSpeed = 2.0;        // velocidad base
    private final double baseSpeed = 2.0;  // para reiniciar
    private final double speedIncreasePerSecond = 0.03; // incremento por segundo
    private final double speedIncreasePerScore = 0.1;   // incremento por cada 10 puntos
    private long startTime;                 // tiempo de inicio del juego

    public GameModel() {
        startTime = System.currentTimeMillis();
    }


    public double getFallSpeed() {
        long elapsed = System.currentTimeMillis() - startTime; // ms
        double timeSpeed = baseSpeed + (elapsed / 1000.0) * speedIncreasePerSecond;
        double scoreSpeed = (score.get() / 10.0) * speedIncreasePerScore;
        fallSpeed = timeSpeed + scoreSpeed;

        fallSpeed = Math.min(fallSpeed, 10.0);

        return fallSpeed;
    }

    // --- SCORE Y LIVES ---
    public int getScore() { return score.get(); }
    public IntegerProperty scoreProperty() { return score; }

    public int getLives() { return lives.get(); }
    public IntegerProperty livesProperty() { return lives; }

    public boolean isGameOver() { return lives.get() <= 0; }

    public void addScore(int points) {
        score.set(score.get() + points);
    }

    public void loseLife() {
        if (lives.get() > 0) {
            lives.set(lives.get() - 1);
        }
    }

    public void resetGame() {
        score.set(0);
        lives.set(MAX_LIVES);
        fallSpeed = baseSpeed;
        startTime = System.currentTimeMillis();
    }
}