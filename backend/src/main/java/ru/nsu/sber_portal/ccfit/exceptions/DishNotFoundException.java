package ru.nsu.sber_portal.ccfit.exceptions;


public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String message) {
        super(message);
    }
}
