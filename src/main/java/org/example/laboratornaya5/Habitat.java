package org.example.laboratornaya5;
import java.util.*;
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
            if (elapsedTime % N1 == 0 && random.nextDouble() < P1) {
                generateBuilding(new Capital(elapsedTime));
            }
            if (elapsedTime % N2 == 0 && random.nextDouble() < P2) {
                generateBuilding(new Wooden(elapsedTime));
            }
        }

        private void generateBuilding(Building building) {
            if (uniqueIds.add(building.getId())) {
                buildings.add(building);
                birthTimeMap.put(building.getBirthTime(), building);
                // Здесь можно добавить код для визуализации объекта
                System.out.println("Generated " + (building.type ? "Capital" : "Wooden") + " building at time " + building.getBirthTime());
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
}
