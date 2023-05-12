package com.charter.rewards.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Month;
import java.util.Map;

@Data
public class Reward {

    @Schema(example = "customerId1")
    private String customerId;

    @Schema(example = "250")
    private int totalRewards;

    @Schema(example = "{\"FEBRUARY\": 50, \"MARCH\": 70, \"APRIL\": 130}")
    private Map<Month, Integer> rewardsByMonth;
}
