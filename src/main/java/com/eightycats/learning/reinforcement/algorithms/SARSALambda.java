package com.eightycats.learning.reinforcement.algorithms;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionEpisode;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.Policy;
import com.eightycats.learning.reinforcement.RewardFunction;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.util.ActionEligibilityTrace;
import com.eightycats.learning.reinforcement.util.EligibilityConfig;
import com.eightycats.learning.reinforcement.util.StateActionPair;
import com.eightycats.litterbox.logging.Logger;

/**
 * Implements the SARSA algorithm with an eligibility trace.
 */
public class SARSALambda extends ActionEpisode
{
    /**
     * The collection of previous states-action pairs that are eligible for update.
     */
    protected ActionEligibilityTrace trace;

    public SARSALambda (State initialState, Policy policy, ActionValueFunction valueFunction,
        RewardFunction rewardFunction, EligibilityConfig config)
    {
        super(initialState, policy, valueFunction, rewardFunction);
        trace = new ActionEligibilityTrace(config);
    }

    public void reset ()
    {
        trace.clear();
    }

    @Override
    public State next ()
    {

        State state = getCurrentState();
        Action action = _policy.selectAction(state);

        double currentValue = valueFunction.getValue(state, action);

        State nextState = action.perform(state);

        // get the reward for performing the action
        double reward = _rewardFunction.getReward(nextState);

        Action nextAction = _policy.selectAction(nextState);
        double nextValue = valueFunction.getValue(nextState, nextAction);

        Logger.debug("******************************************");
        Logger.debug("Reward     : " + reward);
        Logger.debug("Current    : " + currentValue);
        Logger.debug("Next       : " + nextValue);
        Logger.debug("Discounted : " + (getDiscount() * nextValue));

        double valueUpdate = reward + getDiscount() * nextValue - currentValue;

        Logger.debug("TD Error (sigma) : " + valueUpdate);

        // add the state to the eligibilty trace
        trace.add(state, action);

        // this should already get done in step() call
        // this.currentState = nextState;

        // update the value function for all eligible states
        int eligibleCount = trace.getLength();

        Logger.debug("Trace:\n" + trace);
        Logger.debug("Trace length: " + eligibleCount);

        valueUpdate *= getLearningRate();
        Logger.debug("Sigma with Learning Rate: " + valueUpdate);

        for (int i = 0; i < eligibleCount; i++) {
            // calculate each particular state's update value
            // based on the state's eligibility and the learning rate
            StateActionPair eligiblePair = trace.getStateActionPair(i);
            double error = valueUpdate * trace.getEligibility(i);
            Logger.debug("State-Action[" + i + "] :\n" + eligiblePair);
            Logger.debug("Error        : " + error);
            valueFunction.update(eligiblePair.getState(), eligiblePair.getAction(), error);

        }

        return nextState;
    }
}
