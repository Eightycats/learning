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

package com.eightycats.learning.reinforcement.util;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.State;

/**
 * Eligibility trace that keeps track of what State we were in and which Action we took at each step
 * along the way.
 */
public class ActionEligibilityTrace extends EligibilityTrace<StateActionPair>
{
    public ActionEligibilityTrace ()
    {
        this(new EligibilityConfig());
    }

    public ActionEligibilityTrace (EligibilityConfig config)
    {
        super(config);
    }

    public void add (State state, Action action)
    {
        add(new StateActionPair(state, action));
    }

    public StateActionPair getStateActionPair (int index)
    {
        return get(index);
    }
}
