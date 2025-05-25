package com.tillDawn.Model;

import java.util.Random;

public enum AbilityType {
    Vitality("Vitality"),
    Damager("Damager"),
    Procrease("Procrease"),
    Amocrease("Amocrease"),
    Speedy("Speedy")
    ;
    private String name;

    AbilityType(String name) {
        this.name = name;
    }

    private static final AbilityType[] VALUES = values();
    private static final Random RANDOM = new Random();

    public static AbilityType getRandomAbility() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }

    public String getName() {
        return name;
    }
}
