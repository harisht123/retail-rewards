package com.charter.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CustomerPurchases {

    @NotBlank(message = "customerId must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Schema(example = "customerId1")
    private String customerId;

    @Valid
    @Schema(example = "[{\"purchaseId\": 1, \"purchaseDate\": \"2023-02-20\", \"purchaseAmount\": 100}," +
            " {\"purchaseId\": 2, \"purchaseDate\": \"2023-03-12\", \"purchaseAmount\": 90}," +
            "{\"purchaseId\": 2, \"purchaseDate\": \"2023-03-25\", \"purchaseAmount\": 80}," +
            "{\"purchaseId\": 2, \"purchaseDate\": \"2023-04-05\", \"purchaseAmount\": 140}]")
    private List<Purchase> purchases;
}
