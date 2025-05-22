package com.tillDawn.Model;

public enum CharacterType {
    Dasher("Dasher"),
    Diamond("Diamond"),
    Lilith("Lilith"),
    Scarlet("Scarlet"),
    Shana("Shana");
    private String name;
    CharacterType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
