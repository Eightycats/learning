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

package com.eightycats.learning.reinforcement.policy;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.Policy;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.math.util.RandomUtils;

/**
 * This class implements the epsilon-greedy (e-greedy) policy. This policy selects the greedy (most
 * valuable action) most of the time, but a small percentage of the time (epsilon), it selects a
 * random, exploratory action.
 */
public abstract class EGreedyPolicyBase
    implements Policy
{
    /**
     * The probability that an exploratory (non-greedy) action will be selected.
     */
    protected double _epsilon;

    /**
     * This is the amount by which the epsilon value will decay after each selectAction() call.
     */
    protected double _decay = 0.0;

    protected RandomPolicy _randomPolicy = new RandomPolicy();

    public EGreedyPolicyBase (double epsilon)
    {
        setEpsilon(epsilon);
    }

    @Override
    public Action selectAction (State state)
    {
        double selection = RandomUtils.random();

        if (_epsilon > 0.0) {
            // Decay the epsilon value.
            // If the decay is 0, this obviously has no effect.
            _epsilon -= _decay;
        }

        Action action = null;

        if (selection >= _epsilon) {
            action = selectGreedyAction(state);
        }

        // if the e value indicates that we should choose a random action,
        // or if there was no greedy action
        if (action == null) {
            action = _randomPolicy.selectAction(state);
        }

        return action;
    }

    public Action selectGreedyAction (State currentState)
    {
        Action bestAction = null;

        // the best value so far according to the value function
        double max = Double.MIN_VALUE;

        Action[] actions = currentState.getActions();

        for (int i = 0; i < actions.length; i++) {
            double actionValue = getValue(currentState, actions[i]);
            if (actionValue > max) {
                max = actionValue;
                bestAction = actions[i];
            } else if (actionValue == max) {
                // randomly select from between two actions of equal value
                Action[] equalActions = new Action[] { bestAction, actions[i] };
                bestAction = RandomPolicy.selectAction(equalActions);
            }
        }
        return bestAction;
    }

    protected abstract double getValue (State currentState, Action action);

    /**
     * This sets the amount that epsilon will decay after each call to selectAction(). This should
     * be a very small number. As epsilon gets smaller, this policy will select greedy actions more
     * often and explore less.
     */
    public void setDecay (double decay)
    {
        _decay = decay;
    }

    public double getDecay ()
    {
        return _decay;
    }

    /**
     * @return the probability of a selection a non-greedy action.
     */
    public double getEpsilon ()
    {
        return _epsilon;
    }

    /**
     * This sets the probability that an exploratory (non-greedy) action will be selected. The
     * epsilon value must be between 0.0 and 1.0.
     *
     * @param epsilon the probability of a selection a non-greedy action.
     */
    public void setEpsilon (double epsilon)
    {
        if (epsilon < 0.0 || epsilon > 1.0) {
            throw new IllegalArgumentException("The epsilon value [" + epsilon
                + "] must be between 0.0 and 1.0.");
        }
        _epsilon = epsilon;
    }
}
