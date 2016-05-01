package com.eightycats.learning.reinforcement;

/**
 * The reward function is used to get the reward value for 
 * a state.
 * 
 *
 */
public interface RewardFunction
{

    public double getReward( State state );

}
