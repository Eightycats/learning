package com.eightycats.learning.reinforcement.algorithms;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionEpisode;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.Policy;
import com.eightycats.learning.reinforcement.RewardFunction;
import com.eightycats.learning.reinforcement.State;

/**
 * SARSA (State-Action-Reward-State-Action)
 */
public class SARSA extends ActionEpisode
{
    /**
     * @param initialState starting state.
     * @param policy chooses next action.
     * @param valueFunction determines how valuable we currently think a State/Action pair are.
     * @param rewardFunction returns the reward signal.
     */
    public SARSA (State initialState, Policy policy, ActionValueFunction valueFunction,
        RewardFunction rewardFunction)
    {
        super(initialState, policy, valueFunction, rewardFunction);
    }

    /**
     * Uses the policy to select a next Action from those available from the current State. Performs
     * the Action, and returns the new State.
     */
    @Override
    public State next ()
    {
        State state = getCurrentState();
        Action action = _policy.selectAction(state);

        double currentValue = _valueFunction.getValue(state, action);

        State nextState = action.perform(state);

        // get the reward for performing the action
        double reward = _rewardFunction.getReward(nextState);

        Action nextAction = _policy.selectAction(nextState);
        double nextValue = _valueFunction.getValue(nextState, nextAction);

        double valueUpdate = reward + getDiscount() * nextValue - currentValue;

        // mutliply the update value times the learning factor
        valueUpdate *= getLearningRate();

        _valueFunction.update(state, action, valueUpdate);

        return nextState;
    }
}
