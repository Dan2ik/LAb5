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

    private long capitalLifespan;  // Время жизни капитальных домов
    private long woodenLifespan;   // Время жизни деревянных домов

    private long lastCapitalGeneratedTime = 0; // Время последней генерации капитального дома
    private long lastWoodenGeneratedTime = 0;  // Время последней генерации деревянного дома
    private boolean paused = false;

    // Конструктор с инициализацией параметров
    public Habitat(int width, int height, int N1, int N2, double P1, double P2, long capitalLifespan, long woodenLifespan) {
        this.width = width;
        this.height = height;
        this.N1 = N1;
        this.N2 = N2;
        this.P1 = P1;
        this.P2 = P2;
        this.capitalLifespan = capitalLifespan;
        this.woodenLifespan = woodenLifespan;
        this.buildings = new Vector<>();
        this.uniqueIds = new HashSet<>();
        this.birthTimeMap = new HashMap<>();
        this.random = new Random();
    }
    public Vector<Building> getBuildings() {
        return buildings;
    }
    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public void update(long elapsedTime) {
        if (paused) return;
        // Удаление объектов, у которых истек срок жизни
        buildings.removeIf(building -> !building.isAlive(elapsedTime));
        birthTimeMap.values().removeIf(building -> !building.isAlive(elapsedTime));

        // Проверка периода и вероятности для капитального дома
        if (elapsedTime - lastCapitalGeneratedTime >= N1) {
            if (Math.random() * 100.0 < P1) {  // Генерация с вероятностью P1
                generateBuilding(new Capital(elapsedTime, capitalLifespan));
            }
            lastCapitalGeneratedTime = elapsedTime; // Обновляем время последней генерации капитального дома
        }

        // Проверка периода и вероятности для деревянного дома
        if (elapsedTime - lastWoodenGeneratedTime >= N2) {
            if (Math.random() * 100.0 < P2) {  // Генерация с вероятностью P2
                generateBuilding(new Wooden(elapsedTime, woodenLifespan));
            }
            lastWoodenGeneratedTime = elapsedTime; // Обновляем время последней генерации деревянного дома
        }
    }

    private void generateBuilding(Building building) {
        if (uniqueIds.add(building.getId())) {
            buildings.add(building);
            birthTimeMap.put(building.getBirthTime(), building);
            System.out.println("Generated " + (building.isTypeCapital() ? "Capital" : "Wooden") +
                    " building at time " + building.getBirthTime());
        }
    }

    // Метод для подсчета общего количества зданий
    public int getBuildingCount() {
        return buildings.size();
    }

    // Метод для подсчета количества капитальных домов
    public int getCapitalBuildingCount() {
        return (int) buildings.stream().filter(Building::isTypeCapital).count();
    }

    // Метод для подсчета количества деревянных домов
    public int getWoodenBuildingCount() {
        return (int) buildings.stream().filter(building -> !building.isTypeCapital()).count();
    }

    // Геттеры и сеттеры для N1, N2, P1, P2, времени жизни капитальных и деревянных домов
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

    public long getCapitalLifespan() {
        return capitalLifespan;
    }

    public void setCapitalLifespan(long capitalLifespan) {
        this.capitalLifespan = capitalLifespan;
    }

    public long getWoodenLifespan() {
        return woodenLifespan;
    }

    public void setWoodenLifespan(long woodenLifespan) {
        this.woodenLifespan = woodenLifespan;
    }
}
