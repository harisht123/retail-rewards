package com.charter.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class RewardPointsEarnedRequest {

    @NotBlank(message = "requestId must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z0-9-]*$")
    @Schema(example = "bda9c5d5-7567-4bdf-9a71-c0079527d7a7")
    private String requestId;

    @Valid
    private List<CustomerPurchases> customerPurchases;
}
