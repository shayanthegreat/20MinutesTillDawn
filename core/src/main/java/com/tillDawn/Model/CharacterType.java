package com.tillDawn.Model;

public enum CharacterType {
    Dasher("Dasher", 10, 20),
    Diamond("Diamond", 1, 70),
    Lilith("Lilith", 3, 50),
    Scarlet("Scarlet", 5, 30),
    Shana("Shana", 4, 40);
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
