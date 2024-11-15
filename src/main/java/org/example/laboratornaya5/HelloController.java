package org.example.laboratornaya5;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class HelloController {
    private boolean running = false;
    private boolean isPaused;
    private long pausedTime;

    @FXML
    private Canvas canvas;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Text timeText;

    @FXML
    private TextField PeriodC;

    @FXML
    private TextField PeriodW;

    @FXML
    private TextField ChanceC;

    @FXML
    private TextField ChanceW;

    @FXML
    private Label LabelC;

    @FXML
    private Label LabelW;

    @FXML
    private TextField capitalLifespanField;

    @FXML
    private TextField woodenLifespanField;


    private AnimationTimer timer;
    private long startTime;
    private Habitat habitat;
    private Map<Long, Building> buildingMap = new HashMap<>();

    // Метод для добавления обработчика нажатия клавиш
    public void addKeyHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case B -> {
                    if (!running) startSimulation();
                }
                case E -> {
                    if (running) stopSimulation();
                }
                case T -> toggleTimeVisibility();
                case I -> {
                    if (running) showConfirmationDialog(habitat);
                }
                case P -> {
                    habitat.togglePause(); // Включаем/выключаем паузу
                    if (habitat.isPaused()){
                        showNotification("Уведомление", "Пауза активирована",
                                "Пауза активирована");
                    }else {
                        showNotification("Уведомление", "Пауза активирована",
                                "Пауза активирована");

                    }
                    System.out.println("Пауза: " + (habitat.isPaused() ? "Включена" : "Выключена"));
                }
            }
        });
    }

    @FXML
    public void initialize() {
        // Инициализация таймера
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedTime = (now - startTime) / 1_000_000_000.0;
                updateSimulation(elapsedTime);
                if (habitat != null) {
                    LabelC.setText(String.valueOf(habitat.getCapitalBuildingCount()));
                    LabelW.setText(String.valueOf(habitat.getWoodenBuildingCount()));
                }

                // Удаляем здания с истекшим временем жизни
                long currentTime = System.nanoTime();
                buildingMap.values().removeIf(building -> !building.isAlive(currentTime));
            }
        };

        // Добавление обработчика нажатия клавиш
        canvas.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                addKeyHandler(newScene);
            }
        });
    }

    @FXML
    private void startSimulation() {
        try {
            int N1 = Integer.parseInt(PeriodC.getText());
            int N2 = Integer.parseInt(PeriodW.getText());
            double P1 = Double.parseDouble(ChanceC.getText());
            double P2 = Double.parseDouble(ChanceW.getText());
            long capitalLifespan = Long.parseLong(capitalLifespanField.getText());
            long woodenLifespan = Long.parseLong(woodenLifespanField.getText());

            if (P1 > 100 || P1 < 0 || P2 > 100 || P2 < 0) {
                showAlert("Ошибка", "Недопустимое значение шанса",
                        "Значение шанса должно быть в диапазоне от 0 до 100.");
                return;
            }

            if (N1 < 0 || N2 < 0 || capitalLifespan <= 0 || woodenLifespan <= 0) {
                showAlert("Ошибка", "Недопустимое значение периода или времени жизни",
                        "Значение должно быть положительным числом.");
                return;
            }

            // Инициализация Habitat с заданными параметрами
            habitat = new Habitat(100, 100, N1, N2, P1, P2, capitalLifespan, woodenLifespan);
            running = true;
            startButton.setDisable(true);
            stopButton.setDisable(false);

            // Запуск таймера
            startTime = System.nanoTime();
            timer.start();

        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Неверный формат ввода",
                    "Введите корректные числовые значения для периодов и вероятностей.");
        }
    }

    @FXML
    private void showCurrentObjectsDialog() {
        if (habitat == null) {
            System.out.println("Симуляция не запущена.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Текущие объекты");
        alert.setHeaderText("Список активных объектов");
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Список активных объектов");
        TextFlow content = new TextFlow(
                new Text(String.format("Количество капитальных домов: %d\n", habitat.getCapitalBuildingCount())),
                new Text(String.format("Количество деревянных домов: %d\n", habitat.getWoodenBuildingCount()))
        );
        alert.getDialogPane().setContent(content);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();



    }



    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void stopSimulation() {
        if (showConfirmationDialog(habitat)) {
            resetSimulation();
        } else {
            isPaused = false;
            startTime = System.nanoTime() - pausedTime;
        }
        if (!running) return;
        running = false;
        timer.stop();
        clearSimulation();
    }
    @FXML
    private void pause() {
        habitat.togglePause(); // Включаем/выключаем паузу
        if (habitat.isPaused()){
            showNotification("Уведомление", "Пауза активирована",
                    "Пауза активирована");
        }else {
            showNotification("Уведомление", "Пауза активирована",
                    "Пауза активирована");

        }
        System.out.println("Пауза: " + (habitat.isPaused() ? "Включена" : "Выключена"));
    }

    private void resetSimulation() {
        running = false;
        isPaused = false;
        startButton.setDisable(false);
        stopButton.setDisable(true);

        LabelC.setText("0");
        LabelW.setText("0");
        timeText.setText("Время симуляции: 00:00");

        pausedTime = 0;
        startTime = 0;
        habitat = null;
        buildingMap.clear();  // Очистка активных объектов
    }

    @FXML
    private void toggleTimeVisibility() {
        timeText.setVisible(!timeText.isVisible());
    }

    private void updateSimulation(double elapsedTime) {
        timeText.setText(String.format("Time: %.2fs", elapsedTime));

        if (habitat != null) {
            habitat.update((long) elapsedTime);

            // Очистка и отрисовка на Canvas
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // Здесь можно добавить логику для рисования зданий на canvas
        }
    }

    private void clearSimulation() {
        if (canvas != null) {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        timeText.setText("Time: 0s");
    }

    private boolean showConfirmationDialog(Habitat habitat) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Остановить симуляцию?");
        TextFlow content = new TextFlow(
                new Text(String.format("Количество капитальных домов: %d\n", habitat.getCapitalBuildingCount())),
                new Text(String.format("Количество деревянных домов: %d\n", habitat.getWoodenBuildingCount()))
        );
        alert.getDialogPane().setContent(content);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    private void showNotification(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
