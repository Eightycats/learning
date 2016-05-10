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

/**
 *
 */
package com.eightycats.learning.reinforcement.value;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.util.StateActionPair;

/**
 *
 *
 */
public class ActionValueTable extends ValueTableBase<StateActionPair>
    implements ActionValueFunction
{

    @Override
    public double getValue (State state, Action action)
    {
        return super.getValue(getKey(state, action));
    }

    public void setValue (State state, Action action, double value)
    {
        super.setValue(getKey(state, action), value);
    }

    @Override
    public void update (State state, Action action, double deltaValue)
    {
        super.update(getKey(state, action), deltaValue);
    }

    protected StateActionPair getKey (State state, Action action)
    {
        return new StateActionPair(state, action);
    }
}
