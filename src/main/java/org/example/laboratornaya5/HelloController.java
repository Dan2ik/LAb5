package org.example.laboratornaya5;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Canvas canvas; // Canvas для рисования

    @FXML
    private Button startButton; // Кнопка для запуска симуляции

    @FXML
    private Button stopButton; // Кнопка для остановки симуляции

    @FXML
    private Text timeText; // Текстовый компонент для отображения времени

    @FXML
    private TextField PeriodC; // Поле для периода капитальных домов

    @FXML
    private TextField PeriodW; // Поле для периода деревянных домов

    @FXML
    private TextField ChanceC; // Поле для вероятности капитальных домов

    @FXML
    private TextField ChanceW; // Поле для вероятности деревянных домов

    private AnimationTimer timer; // Таймер для обновления симуляции
    private long startTime; // Время начала симуляции
    private Habitat habitat; // Экземпляр Habitat для управления зданиями

    @FXML
    public void initialize() {
        // Инициализация таймера
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedTime = (now - startTime) / 1_000_000_000.0;
                updateSimulation(elapsedTime);
            }
        };
    }

    @FXML
    private void startSimulation() {
        // Чтение параметров из текстовых полей
        try {
            int N1 = Integer.parseInt(PeriodC.getText());
            int N2 = Integer.parseInt(PeriodW.getText());
            double P1 = Double.parseDouble(ChanceC.getText());
            double P2 = Double.parseDouble(ChanceW.getText());

            // Инициализация Habitat с заданными параметрами
            habitat = new Habitat(100, 100, N1, N2, P1, P2);

            // Запуск таймера
            startTime = System.nanoTime();
            timer.start();

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введите корректные числовые значения для периодов и вероятностей.");
        }
    }

    @FXML
    private void stopSimulation() {
        // Остановка симуляции
        timer.stop();
        clearSimulation();
    }

    @FXML
    private void toggleTimeVisibility() {
        // Переключение видимости текстового компонента времени
        timeText.setVisible(!timeText.isVisible());
    }

    private void updateSimulation(double elapsedTime) {
        // Обновление времени и вызов обновления Habitat
        timeText.setText(String.format("Time: %.2fs", elapsedTime));

        // Обновление объектов в Habitat
        if (habitat != null) {
            habitat.update((long) elapsedTime);

            // Очистка и отрисовка на Canvas
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // Здесь можно добавить логику для рисования зданий на canvas
        }
    }

    private void clearSimulation() {
        // Очистка объектов после завершения симуляции
        if (canvas != null) {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        timeText.setText("Time: 0s");
    }
}
