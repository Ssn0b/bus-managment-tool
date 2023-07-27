package com.snob.busmanagmenttool.exception;

public class BusAlreadyHasRouteException extends RuntimeException{
    public BusAlreadyHasRouteException(String message) {
        super(message);
    }
}
