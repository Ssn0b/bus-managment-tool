package com.snob.busmanagmenttool.exception;

public class BusSeatIsAlreadyTaken extends RuntimeException{
    public BusSeatIsAlreadyTaken(String message) {
        super(message);
    }
}
