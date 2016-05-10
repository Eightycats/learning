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
