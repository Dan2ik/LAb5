package org.example.laboratornaya5;

import java.util.UUID;

public abstract class Building implements IBehaviour {
    private String id;
    private long birthTime;

    public Building(long birthTime) {
        this.birthTime = birthTime;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public long getBirthTime() {
        return birthTime;
    }

    public abstract boolean isTypeCapital();  // Абстрактный метод для определения типа здания
}

class Capital extends Building {
    public Capital(long birthTime) {
        super(birthTime);
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
    public Wooden(long birthTime) {
        super(birthTime);
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