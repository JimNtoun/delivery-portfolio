package com.acme.delivery.transfer;

import java.io.Serializable;

public record FieldValidationError(
        String field,
        String message
) implements Serializable {
}