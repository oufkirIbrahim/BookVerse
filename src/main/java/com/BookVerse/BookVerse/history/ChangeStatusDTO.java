package com.BookVerse.BookVerse.history;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusDTO {
    @NotNull(message = "Transaction ID is required")
    @NotEmpty(message = "Transaction ID is required")
    private Long transactionId;

    @Pattern(regexp = "APPROVED|REJECTED", message = "Status must be either APPROVED or REJECTED")
    private String status;
}
