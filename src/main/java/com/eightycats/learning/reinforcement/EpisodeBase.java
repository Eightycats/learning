package com.eightycats.learning.reinforcement;

import com.eightycats.learning.reinforcement.util.EligibilityConstants;

/**
 *
 */
public abstract class EpisodeBase
    implements EligibilityConstants
{
    /**
     * The learning parameter values.
     */
    protected EpisodeConfig _config;

    protected State _currentState;

    protected Policy _policy;

    protected RewardFunction _rewardFunction;

    public EpisodeBase (State initialState, Policy policy, RewardFunction rewardFunction)
    {
        this(initialState, policy, rewardFunction, new EpisodeConfig());
    }

    public EpisodeBase (State initialState, Policy policy, RewardFunction rewardFunction,
        EpisodeConfig config)
    {
        _currentState = initialState;
        _policy = policy;
        _rewardFunction = rewardFunction;
        _config = config;
    }

    /**
     * Have we reached a terminal state? Game over?
     */
    public boolean isDone ()
    {
        return _currentState.isTerminal();
    }

    public void step ()
    {
        _currentState = next();
    }

    public abstract State next ();

    public State getCurrentState ()
    {
        return _currentState;
    }

    public void setPolicy (Policy policy)
    {
        _policy = policy;
    }

    public Policy getPolicy ()
    {
        return _policy;
    }

    public double getDiscount ()
    {
        return _config.getDiscount();
    }

    public double getLearningRate ()
    {
        return _config.getLearningRate();
    }
}