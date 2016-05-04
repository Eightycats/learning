package com.eightycats.learning.reinforcement;

/**
 * Gets the reward signal for a State.
 */
public interface RewardFunction
{
    public double getReward( State state );
}
