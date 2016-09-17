package ru.innopolis.mputilov;

public class Helper {
    static void sleepWithIgnorance(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}