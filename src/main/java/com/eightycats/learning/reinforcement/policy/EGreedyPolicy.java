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
