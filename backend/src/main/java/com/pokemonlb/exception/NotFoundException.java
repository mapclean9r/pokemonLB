package com.pokemonlb.exception;

/** Thrown by services when a requested resource does not exist; answered with HTTP 404. */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String resource, Object id) {
        super(resource + " " + id + " not found");
    }
}
