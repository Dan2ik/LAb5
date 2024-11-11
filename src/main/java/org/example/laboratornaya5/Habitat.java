package org.example.laboratornaya5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

public class Habitat {
    private int width;
    private int height;
    private Vector<Building> buildings;
    private HashSet<String> uniqueIds;
    private HashMap<Long, Building> birthTimeMap;
    private Random random;

    private int N1; // Период генерации капитальных домов
    private int N2; // Период генерации деревянных домов
    private double P1; // Вероятность генерации капитальных домов
    private double P2; // Вероятность генерации деревянных домов

    private long lastCapitalGeneratedTime = 0; // Время последней генерации капитального дома
    private long lastWoodenGeneratedTime = 0; // Время последней генерации деревянного дома

    public Habitat(int width, int height, int N1, int N2, double P1, double P2) {
        this.width = width;
        this.height = height;
        this.buildings = new Vector<>();
        this.uniqueIds = new HashSet<>();
        this.birthTimeMap = new HashMap<>();
        this.random = new Random();
        this.N1 = N1;
        this.N2 = N2;
        this.P1 = P1;
        this.P2 = P2;
    }

    public void update(long elapsedTime) {
        // Проверка периода и вероятности для капитального дома
        if (elapsedTime - lastCapitalGeneratedTime >= N1) {
            if (random.nextDouble() < P1) {
                generateBuilding(new Capital(elapsedTime));
                lastCapitalGeneratedTime = elapsedTime; // Обновляем время последней генерации капитального дома
            }
        }

        // Проверка периода и вероятности для деревянного дома
        if (elapsedTime - lastWoodenGeneratedTime >= N2) {
            if (random.nextDouble() < P2) {
                generateBuilding(new Wooden(elapsedTime));
                lastWoodenGeneratedTime = elapsedTime; // Обновляем время последней генерации деревянного дома
            }
        }
    }

    private void generateBuilding(Building building) {
        if (uniqueIds.add(building.getId())) {
            buildings.add(building);
            birthTimeMap.put(building.getBirthTime(), building);
            // Здесь можно добавить код для визуализации объекта
            System.out.println("Generated " + (building.isTypeCapital() ? "Capital" : "Wooden") + " building at time " + building.getBirthTime());
        }
    }

    public static void main(String[] args) {
        Habitat habitat = new Habitat(100, 100, 5, 10, 0.5, 0.3);

        // Симуляция обновления каждые 1 секунду
        for (long time = 0; time < 100; time++) {
            habitat.update(time);
            try {
                Thread.sleep(1000); // Пауза в 1 секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Геттеры и сеттеры для N1, N2, P1 и P2
    public int getN1() {
        return N1;
    }

    public void setN1(int N1) {
        this.N1 = N1;
    }

    public int getN2() {
        return N2;
    }

    public void setN2(int N2) {
        this.N2 = N2;
    }

    public double getP1() {
        return P1;
    }

    public void setP1(double P1) {
        this.P1 = P1;
    }

    public double getP2() {
        return P2;
    }

    public void setP2(double P2) {
        this.P2 = P2;
    }
}
