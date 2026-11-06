package com.example.noteapi.exception;

public class SpeedOverException extends RuntimeException {
    public SpeedOverException(int penalty) {
        super("スピード違反・罰金 +" + penalty + "円");
    }
}
