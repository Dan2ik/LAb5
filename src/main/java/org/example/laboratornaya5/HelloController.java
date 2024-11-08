package org.example.laboratornaya5;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.scene.control.TextField;

public class HelloController {

    // Аннотация @FXML используется для связывания компонентов FXML с полями класса
    @FXML
    private Canvas canvas; // Canvas для рисования

    @FXML
    private Button startButton; // Кнопка для запуска симуляции

    @FXML
    private Button stopButton; // Кнопка для остановки симуляции

    @FXML
    private Text timeText; // Текстовый компонент для отображения времени

    private AnimationTimer timer; // Таймер для обновления симуляции
    private long startTime; // Время начала симуляции

    @FXML
    public void initialize() {
        // Инициализация таймера и других компонентов
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Обновление симуляции с учетом прошедшего времени
                updateSimulation((now - startTime) / 1_000_000_000.0);
            }
        };
    }

    @FXML
    private void startSimulation() {
        // Запуск симуляции
        startTime = System.nanoTime(); // Установка текущего времени как начального
        timer.start(); // Запуск таймера
    }

    @FXML
    private void stopSimulation() {
        // Остановка симуляции
        timer.stop(); // Остановка таймера
        clearSimulation(); // Очистка симуляции
    }

    @FXML
    private void toggleTimeVisibility() {
        // Переключение видимости текстового компонента времени
        timeText.setVisible(!timeText.isVisible());
    }

    private void updateSimulation(double elapsedTime) {
        // Логика обновления объектов симуляции
        timeText.setText(String.format("Time: %.2fs", elapsedTime)); // Обновление текста времени
        // Отрисовка объектов на canvas (здесь можно добавить логику рисования)
    }

    private void clearSimulation() {
        // Очистка объектов после завершения симуляции
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Очистка canvas
        timeText.setText("Time: 0s"); // Сброс текста времени

    }
    @FXML
    private TextField PeriodC;
    public String getTextPeriodC() {
// Считываем текст из TextField
        String inputText = PeriodC.getText();
        return inputText;
        }
    @FXML
    private TextField PeriodW;
    public String getTextPeriodW() {
// Считываем текст из TextField
        String inputText = PeriodW.getText();
        return inputText;
    }
    @FXML
    private TextField chanceC;
    public String getTextchanceC() {
// Считываем текст из TextField
        String inputText = chanceC.getText();
        return inputText;
    }
    @FXML
    private TextField chanceW;
    public String getTextchanceW() {
// Считываем текст из TextField
        String inputText = chanceW.getText();
        return inputText;
    }
}
