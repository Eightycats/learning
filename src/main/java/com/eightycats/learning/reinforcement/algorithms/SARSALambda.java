/**
 * Copyright 2016 Matthew A Jensen <eightycats@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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
    protected ActionEligibilityTrace _trace;

    public SARSALambda (State initialState, Policy policy, ActionValueFunction valueFunction,
        RewardFunction rewardFunction, EligibilityConfig config)
    {
        super(initialState, policy, valueFunction, rewardFunction);
        _trace = new ActionEligibilityTrace(config);
    }

    public void reset ()
    {
        _trace.clear();
    }

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

        Logger.debug("******************************************");
        Logger.debug("Reward     : " + reward);
        Logger.debug("Current    : " + currentValue);
        Logger.debug("Next       : " + nextValue);
        Logger.debug("Discounted : " + (getDiscount() * nextValue));

        double valueUpdate = reward + getDiscount() * nextValue - currentValue;

        Logger.debug("TD Error (sigma) : " + valueUpdate);

        // add the state to the eligibilty trace
        _trace.add(state, action);

        // this should already get done in step() call
        // this.currentState = nextState;

        // update the value function for all eligible states
        int eligibleCount = _trace.getLength();

        Logger.debug("Trace:\n" + _trace);
        Logger.debug("Trace length: " + eligibleCount);

        valueUpdate *= getLearningRate();
        Logger.debug("Sigma with Learning Rate: " + valueUpdate);

        for (int i = 0; i < eligibleCount; i++) {
            // calculate each particular state's update value
            // based on the state's eligibility and the learning rate
            StateActionPair eligiblePair = _trace.getStateActionPair(i);
            double error = valueUpdate * _trace.getEligibility(i);
            Logger.debug("State-Action[" + i + "] :\n" + eligiblePair);
            Logger.debug("Error        : " + error);
            _valueFunction.update(eligiblePair.getState(), eligiblePair.getAction(), error);

        }

        return nextState;
    }
}
