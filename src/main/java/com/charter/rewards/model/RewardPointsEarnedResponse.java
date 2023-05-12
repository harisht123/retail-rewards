package com.charter.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RewardPointsEarnedResponse {

    @Schema(example = "bda9c5d5-7567-4bdf-9a71-c0079527d7a7")
    private String requestId;

    private List<Reward> rewards;
}
