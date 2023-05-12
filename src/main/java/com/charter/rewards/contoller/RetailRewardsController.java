package com.charter.rewards.contoller;

import com.charter.rewards.model.RewardPointsEarnedRequest;
import com.charter.rewards.model.RewardPointsEarnedResponse;
import com.charter.rewards.service.RewardsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetailRewardsController {

    private final RewardsService rewardsService;

    @PostMapping("/rewards/points-earned")
    public ResponseEntity<RewardPointsEarnedResponse> rewardsPointsEarned(@Valid @RequestBody RewardPointsEarnedRequest rewardPointsEarnedRequest) {
        log.info("Rewards points earned request received. Request: {}", rewardPointsEarnedRequest);
        return ResponseEntity.ok(rewardsService.calculateRewards(rewardPointsEarnedRequest));
    }
}

