package com.leo.commons.utils;

/**
 * Created by lhilb on 28.05.2017.
 */
public class SphThread {
    // -------------------------------------------------------------------------------------------------------------------------
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void endlessLoop() {
        while (Thread.currentThread().isAlive()) {
            sleep(1000);
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------
}
