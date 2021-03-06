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
 * A State/Action pair used as a key for the value of that Action from the State.
 */
public class StateActionPair
{
    protected State _state;

    protected Action _action;

    public StateActionPair (State state, Action action)
    {
        _state = state;
        _action= action;
    }

    public State getState ()
    {
        return _state;
    }

    public Action getAction ()
    {
        return _action;
    }

    @Override
    public boolean equals (Object object)
    {
        boolean equals = false;
        if (object instanceof StateActionPair) {
            StateActionPair other = (StateActionPair) object;
            equals = _state.equals(other._state) && _action.equals(other._action);
        }
        return equals;
    }

    /**
     * Used when this object is the key in a hash table.
     */
    @Override
    public int hashCode ()
    {
        return _state.hashCode() + _action.hashCode();
    }

    @Override
    public String toString ()
    {
        return "[" + _state.toString() + ", " + _action.toString() + "]";
    }
}
