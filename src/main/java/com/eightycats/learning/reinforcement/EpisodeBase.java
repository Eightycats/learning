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

package com.eightycats.learning.reinforcement;

import com.eightycats.learning.reinforcement.util.EligibilityConstants;

/**
 * A base training episode.
 */
public abstract class EpisodeBase
    implements EligibilityConstants
{
    /**
     * The learning parameter values.
     */
    protected EpisodeConfig _config;

    /**
     * The state we are currently in.
     */
    protected State _currentState;

    /**
     * Select the next state.
     */
    protected Policy _policy;

    /**
     * The reward signal.
     */
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
