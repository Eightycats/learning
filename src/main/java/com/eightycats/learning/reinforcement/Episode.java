package com.eightycats.learning.reinforcement;

import com.eightycats.learning.reinforcement.util.EligibilityConfig;

/**
 * An Episode based on the value of States.
 */
public abstract class Episode extends EpisodeBase
{
    protected ValueFunction _valueFunction;

    public Episode (State initialState, Policy policy, ValueFunction valueFunction,
        RewardFunction rewardFunction, EligibilityConfig config)
    {
        super(initialState, policy, rewardFunction, config);
        _valueFunction = valueFunction;
    }
}
