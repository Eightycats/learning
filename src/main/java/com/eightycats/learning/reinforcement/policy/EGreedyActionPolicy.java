package com.eightycats.learning.reinforcement.policy;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.State;

/**
 *
 *
 */
public class EGreedyActionPolicy extends EGreedyPolicyBase
{
    private ActionValueFunction valueFunction;

    /**
     * @param epsilon percentage of how often this policy should choose an exploratory action
     *                rather that the current best action.
     */
    public EGreedyActionPolicy (double epsilon, ActionValueFunction valueFunction)
    {
        super(epsilon);
        this.valueFunction = valueFunction;
    }

    @Override
    protected double getValue (State currentState, Action action)
    {
        return valueFunction.getValue(currentState, action);
    }

}
