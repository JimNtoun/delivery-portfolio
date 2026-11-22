package com.acme.delivery.transfer;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@Builder
public class ApiError implements Serializable {
    String description;
    Integer httpStatus;
    String path;

    @Builder.Default
    List<FieldValidationError> fieldErrors = List.of();
}