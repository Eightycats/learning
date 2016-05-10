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

package com.eightycats.learning.reinforcement.value.event;

import com.eightycats.learning.reinforcement.*;
import java.util.*;

/**
 * Fired off when the value function changes.
 */
public class StateValueChangeEvent extends EventObject
{
    private static final long serialVersionUID = 1L;

    protected State _state;

    protected double _value;

    public StateValueChangeEvent (ValueFunction source, State state, double value)
    {
        super(source);
        _state = state;
        _value = value;
    }

    public State getState ()
    {
        return _state;
    }

    public double getValue ()
    {
        return _value;
    }
}
