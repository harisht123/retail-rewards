package com.charter.rewards.service;

import com.charter.rewards.model.Purchase;
import com.charter.rewards.model.Reward;
import com.charter.rewards.model.RewardPointsEarnedRequest;
import com.charter.rewards.model.RewardPointsEarnedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardsService {

    @Value("${rewards.pointPerDollarAboveFiftyBelowHundred}")
    private int pointPerDollarAboveFiftyBelowHundred;

    @Value("${rewards.pointPerDollarAboveHundred}")
    private int pointPerDollarAboveHundred;

    public RewardPointsEarnedResponse calculateRewards(RewardPointsEarnedRequest rewardPointsEarnedRequest) {
        RewardPointsEarnedResponse rewardPointsEarnedResponse = new RewardPointsEarnedResponse();
        rewardPointsEarnedResponse.setRequestId(rewardPointsEarnedRequest.getRequestId());

        Map<String, Reward> rewardMap = new HashMap<>();
        rewardPointsEarnedRequest.getCustomerPurchases().forEach(customerPurchases -> {
            Reward reward = calculateReward(customerPurchases.getPurchases());

            if (rewardMap.containsKey(customerPurchases.getCustomerId())) {
                rewardMap.get(customerPurchases.getCustomerId()).getRewardsByMonth().forEach((key, value) -> {
                    if (reward.getRewardsByMonth().containsKey(key)) {
                        int rewardPoints = value + reward.getRewardsByMonth().get(key);
                        reward.getRewardsByMonth().put(key, rewardPoints);
                    } else {
                        reward.getRewardsByMonth().put(key, value);
                    }
                });

            }

            reward.setTotalRewards(calculateTotalRewards(reward.getRewardsByMonth()));
            reward.setCustomerId(customerPurchases.getCustomerId());
            rewardMap.put(customerPurchases.getCustomerId(), reward);
        });

        List<Reward> rewards = new ArrayList<>(rewardMap.values());
        rewardPointsEarnedResponse.setRewards(rewards);

        log.info("Reward points earned response: {}", rewardPointsEarnedResponse);
        return rewardPointsEarnedResponse;
    }

    private Reward calculateReward(List<Purchase> purchases) {
        Reward reward = new Reward();
        Map<Month, Integer> rewardsByMonths = new HashMap<>();

        purchases.forEach(purchase -> {
            Month purchaseMonth = purchase.getPurchaseDate().getMonth();
            int rewardPoints = calculateRewardPoints(purchase.getPurchaseAmount());

            if (rewardsByMonths.containsKey(purchaseMonth)) {
                rewardPoints += rewardsByMonths.get(purchaseMonth);
                rewardsByMonths.put(purchaseMonth, rewardPoints);
            } else {
                rewardsByMonths.put(purchaseMonth, rewardPoints);
            }

        });

        reward.setRewardsByMonth(rewardsByMonths);
        return reward;
    }

    private int calculateRewardPoints(int purchaseAmount) {
        int rewardPoints = 0;
        if (purchaseAmount <= 50) {
            return rewardPoints;
        }

        if (purchaseAmount <= 100) {
            rewardPoints = (purchaseAmount - 50) * pointPerDollarAboveFiftyBelowHundred;
        }

        if (purchaseAmount > 100) {
            rewardPoints = (50 * pointPerDollarAboveFiftyBelowHundred) + ((purchaseAmount - 100) * pointPerDollarAboveHundred);

        }

        return rewardPoints;
    }

    private int calculateTotalRewards(Map<Month, Integer> rewardsByMonths) {
        int totalRewards = 0;
        Collection<Integer> rewardPointsByMonth = rewardsByMonths.values();
        for (Integer rewardPoints : rewardPointsByMonth) {
            totalRewards += rewardPoints;
        }

        return totalRewards;
    }

    public void setPointPerDollarAboveFiftyBelowHundred(int pointPerDollarAboveFiftyBelowHundred) {
        this.pointPerDollarAboveFiftyBelowHundred = pointPerDollarAboveFiftyBelowHundred;
    }

    public void setPointPerDollarAboveHundred(int pointPerDollarAboveHundred) {
        this.pointPerDollarAboveHundred = pointPerDollarAboveHundred;
    }
}
