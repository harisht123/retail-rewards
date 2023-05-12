package com.charter.rewards.service;

import com.charter.rewards.model.RewardPointsEarnedRequest;
import com.charter.rewards.model.RewardPointsEarnedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RewardsServiceTest {

    RewardsService rewardsService = new RewardsService();

    @BeforeEach
    public void setup() {
        rewardsService.setPointPerDollarAboveHundred(2);
        rewardsService.setPointPerDollarAboveFiftyBelowHundred(1);
    }

    @Test
    void testRewardServiceSingleCustomer() throws IOException {
        RewardPointsEarnedRequest request = readRewardPointsEarnedRequestFromJson("src/test/resources/rewardPointsEarnedRequest1.json");
        RewardPointsEarnedResponse response = rewardsService.calculateRewards(request);

        assertNotNull(response);
        assertEquals( "bda9c5d5-7567-4bdf-9a71-c0079527d7a7", response.getRequestId());
        assertEquals( 1, response.getRewards().size());
        assertEquals( "customerId1", response.getRewards().get(0).getCustomerId());
        assertEquals( 90, response.getRewards().get(0).getTotalRewards());
        assertEquals( 90, response.getRewards().get(0).getRewardsByMonth().get(Month.APRIL));
    }

    @Test
    void testRewardServiceMultipleCustomers() throws IOException {
        RewardPointsEarnedRequest request = readRewardPointsEarnedRequestFromJson("src/test/resources/rewardPointsEarnedRequest2.json");
        RewardPointsEarnedResponse response = rewardsService.calculateRewards(request);

        assertNotNull(response);
        assertEquals( "bda9c5d5-7567-4bdf-9a71-c0079527d7a7", response.getRequestId());
        assertEquals( 3, response.getRewards().size());

        assertEquals( "customerId1", response.getRewards().get(0).getCustomerId());
        assertEquals( 424, response.getRewards().get(0).getTotalRewards());
        assertEquals( 294, response.getRewards().get(0).getRewardsByMonth().get(Month.MARCH));
        assertEquals( 90, response.getRewards().get(0).getRewardsByMonth().get(Month.APRIL));
        assertEquals( 40, response.getRewards().get(0).getRewardsByMonth().get(Month.MAY));

        assertEquals( "customerId2", response.getRewards().get(1).getCustomerId());
        assertEquals( 1230, response.getRewards().get(1).getTotalRewards());
        assertEquals( 580, response.getRewards().get(1).getRewardsByMonth().get(Month.MARCH));
        assertEquals( 650, response.getRewards().get(1).getRewardsByMonth().get(Month.APRIL));

        assertEquals( "customerId3", response.getRewards().get(2).getCustomerId());
        assertEquals( 90, response.getRewards().get(2).getTotalRewards());
        assertEquals( 90, response.getRewards().get(2).getRewardsByMonth().get(Month.APRIL));
    }

    private RewardPointsEarnedRequest readRewardPointsEarnedRequestFromJson(String pathname) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(new File(pathname), RewardPointsEarnedRequest.class);
    }

}