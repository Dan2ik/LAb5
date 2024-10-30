package org.example.aaaaaalab5;

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
        Habitat habitat = new Habitat(getTextPeriodC(), getTextPeriodW(), 5, 10, 0.5, 0.3);
        // Симуляция обновления каждые 1 секунду
        for (long time = 0; time < 100; time++) {
            habitat.update(time);
            
            try {
                Thread.sleep(1000); // Пауза в 1 секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
    public Integer getTextPeriodC() {
        // Считываем текст из TextField
        String inputText = PeriodC.getText();
        try {
            // Преобразуем текст в целое число
            return Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            // Обработка ошибки, если текст не является числом
            System.err.println("Ошибка преобразования текста в число: " + e.getMessage());
            return null; // или возвращаем какое-то значение по умолчанию
        }
    }
    @FXML
    private TextField PeriodW;

        public Integer getTextPeriodW() {
            // Считываем текст из TextField
            String inputText = PeriodW.getText();
            try {
                // Преобразуем текст в целое число
                return Integer.parseInt(inputText);
            } catch (NumberFormatException e) {
                // Обработка ошибки, если текст не является числом
                System.err.println("Ошибка преобразования текста в число: " + e.getMessage());
                return null; // или возвращаем какое-то значение по умолчанию
            }
        }

    @FXML
    private TextField chanceC;
        public Integer getTextchanceC() {
            // Считываем текст из TextField
            String inputText = chanceC.getText();
            try {
                // Преобразуем текст в целое число
                return Integer.parseInt(inputText);
            } catch (NumberFormatException e) {
                // Обработка ошибки, если текст не является числом
                System.err.println("Ошибка преобразования текста в число: " + e.getMessage());
                return null; // или возвращаем какое-то значение по умолчанию
            }
        }
    @FXML
    private TextField chanceW;
    public Integer getTextchanceW() {
        // Считываем текст из TextField
        String inputText = chanceW.getText();
        try {
            // Преобразуем текст в целое число
            return Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            // Обработка ошибки, если текст не является числом
            System.err.println("Ошибка преобразования текста в число: " + e.getMessage());
            return null; // или возвращаем какое-то значение по умолчанию
        }
    }
}
