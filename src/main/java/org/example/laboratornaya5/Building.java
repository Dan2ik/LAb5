package org.example.laboratornaya5;

import java.util.UUID;

public abstract class Building implements IBehaviour {
    private final long lifespan;   // Время жизни объекта
    private final String id;       // Уникальный идентификатор объекта
    private final long birthTime;  // Время создания объекта

    public Building(long birthTime, long lifespan) {
        this.birthTime = birthTime;
        this.lifespan = lifespan;
        this.id = UUID.randomUUID().toString();  // Генерация уникального идентификатора
    }

    public String getId() {
        return id;
    }

    public long getBirthTime() {
        return birthTime;
    }

    public long getLifespan() {
        return lifespan;
    }

    // Метод для проверки, жив ли объект
    public boolean isAlive(long currentTime) {
        return currentTime - birthTime < lifespan;
    }

    // Абстрактный метод для определения типа здания (капитальное или деревянное)
    public abstract boolean isTypeCapital();
}

class Capital extends Building {
    public Capital(long birthTime, long lifespan) {
        super(birthTime, lifespan);
    }

    @Override
    public void update(long time) {
        // Реализация логики обновления для капитального дома
    }

    @Override
    public boolean isTypeCapital() {
        return true;  // Капитальный дом
    }
}

class Wooden extends Building {
    public Wooden(long birthTime, long lifespan) {
        super(birthTime, lifespan);
    }

    @Override
    public void update(long time) {
        // Реализация логики обновления для деревянного дома
    }

    @Override
    public boolean isTypeCapital() {
        return false;  // Деревянный дом
    }
}
