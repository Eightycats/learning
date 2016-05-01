package com.eightycats.learning.reinforcement;

import com.eightycats.learning.reinforcement.util.EligibilityConfig;

/**
 *
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

    protected void setValueFunction (ValueFunction valueFunction)
    {
        _valueFunction = valueFunction;
    }

    public ValueFunction getValueFunction ()
    {
        return _valueFunction;
    }
}
