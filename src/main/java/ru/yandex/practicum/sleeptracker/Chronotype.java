package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL, LARK, PIGEON;

    @Override
    public String toString() {
        switch (this) {
            case OWL: return "Сова";
            case LARK: return "Жаворонок";
            case PIGEON: return "Голубь";
            default: return super.toString();
        }
    }
}