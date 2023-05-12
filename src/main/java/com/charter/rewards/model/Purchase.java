package com.charter.rewards.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Purchase {

    @Min(1)
    private int purchaseId;

    @NotNull
    private LocalDate purchaseDate;

    @Min(0)
    private int purchaseAmount;
}
