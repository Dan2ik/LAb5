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

    // Конструктор с инициализацией параметров
    public Habitat(int width, int height, int N1, int N2, double P1, double P2) {
        this.width = width;
        this.height = height;
        this.N1 = N1;
        this.N2 = N2;
        this.P1 = P1;
        this.P2 = P2;
        this.buildings = new Vector<>();
        this.uniqueIds = new HashSet<>();
        this.birthTimeMap = new HashMap<>();
        this.random = new Random();
    }

    public void update(long elapsedTime) {
        // Проверка периода и вероятности для капитального дома
        if (elapsedTime - lastCapitalGeneratedTime >= N1) {
            System.out.println("Проверка генерации капитального дома: прошло " + (elapsedTime - lastCapitalGeneratedTime) + " секунд");
            if (random.nextDouble() < P1) {  // Генерация с вероятностью P1
                generateBuilding(new Capital(elapsedTime));
                lastCapitalGeneratedTime = elapsedTime; // Обновляем время последней генерации капитального дома
            } else {
                System.out.println("Капитальный дом не сгенерирован по вероятности P1 = " + P1);
            }
        }

        // Проверка периода и вероятности для деревянного дома
        if (elapsedTime - lastWoodenGeneratedTime >= N2) {
            System.out.println("Проверка генерации деревянного дома: прошло " + (elapsedTime - lastWoodenGeneratedTime) + " секунд");
            if (random.nextDouble() < P2) {  // Генерация с вероятностью P2
                generateBuilding(new Wooden(elapsedTime));
                lastWoodenGeneratedTime = elapsedTime; // Обновляем время последней генерации деревянного дома
            } else {
                System.out.println("Деревянный дом не сгенерирован по вероятности P2 = " + P2);
            }
        }
    }
    private void generateBuilding(Building building) {
        if (uniqueIds.add(building.getId())) {
            buildings.add(building);
            birthTimeMap.put(building.getBirthTime(), building);
            System.out.println("Generated " + (building.isTypeCapital() ? "Capital" : "Wooden") + " building at time " + building.getBirthTime());
        }
    }

    // Метод для подсчета общего количества зданий
    public int getBuildingCount() {
        return buildings.size();
    }

    // Метод для подсчета количества капитальных домов
    public int getCapitalBuildingCount() {
        int count = 0;
        for (Building building : buildings) {
            if (building.isTypeCapital()) {
                count++;
            }
        }
        return count;
    }

    // Метод для подсчета количества деревянных домов
    public int getWoodenBuildingCount() {
        int count = 0;
        for (Building building : buildings) {
            if (!building.isTypeCapital()) {
                count++;
            }
        }
        return count;
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
