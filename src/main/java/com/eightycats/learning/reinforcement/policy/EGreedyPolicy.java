package com.eightycats.learning.reinforcement.policy;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.ValueFunction;

/**
 * This class implements the epsilon-greedy (e-greedy) policy. This policy selects the greedy (most
 * valuable action) most of the time, but a small percentage of the time (epsilon), it selects an
 * action at random. This is used to explore the values of other actions.
 */
public class EGreedyPolicy extends EGreedyPolicyBase
{
    protected ValueFunction _valueFunction;

    public EGreedyPolicy (double epsilon, ValueFunction valueFunction)
    {
        super(epsilon);
        _valueFunction = valueFunction;
    }

    @Override
    protected double getValue (State currentState, Action action)
    {
        // return the value of the state that we would
        // enter if we take the given action
        return _valueFunction.getValue(action.getNextState(currentState));
    }
}
