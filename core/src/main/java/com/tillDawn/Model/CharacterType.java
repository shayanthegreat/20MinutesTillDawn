package com.tillDawn.Model;

public enum CharacterType {
    Dasher("Dasher", 10, 200),
    Diamond("Diamond", 1, 700),
    Lilith("Lilith", 3, 500),
    Scarlet("Scarlet", 5, 300),
    Shana("Shana", 4, 400);
    private String name;
    private int speed;
    private int health;

    CharacterType(String name, int speed, int health) {
        this.name = name;
        this.speed = speed;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }
}
