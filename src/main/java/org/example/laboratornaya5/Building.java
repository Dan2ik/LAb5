package org.example.laboratornaya5;

import java.util.UUID;

public abstract class Building implements IBehaviour {
    private String id;
    public Boolean type;
    private long birthTime;

    public Building(long birthTime) {
        this.birthTime = birthTime;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
    public Long getBirthTime(){
        return birthTime;
    }
}

class Capital extends Building {
    public Capital(long birthTime) {
        super(birthTime);
        this.type = true;
    }

    @Override
    public void update(long time) {

    }

    @Override
    public boolean isTypeCapital() {
        return false;
    }
}
class Wooden extends Building {
    public Wooden(long birthTime) {
        super(birthTime);
        this.type = false;
    }

    @Override
    public void update(long time) {

    }

    @Override
    public boolean isTypeCapital() {
        return false;
    }
}