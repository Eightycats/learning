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

import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.ValueFunction;

/**
 * A wrapper around a value function that fires off events when the function values change.
 */
public class ValueModel implements StateValueModel
{
    protected ValueFunction _valueFunction;

    protected StateValueChangeSupport _listeners;

    public ValueModel (ValueFunction valueFunction)
    {
        _valueFunction = valueFunction;
        _listeners = new StateValueChangeSupport();
    }

    @Override
    public void addListener (StateValueChangeListener listener)
    {
        _listeners.addListener(listener);
    }

    @Override
    public void removeListener (StateValueChangeListener listener)
    {
        _listeners.removeListener(listener);
    }

    @Override
    public void reset ()
    {
        _valueFunction.reset();
    }

    @Override
    public double getValue (State s)
    {
        return _valueFunction.getValue(s);
    }

    @Override
    public void update (State state, double deltaValue)
    {
        _valueFunction.update(state, deltaValue);
        // don't even bother to create the event if no one cares
        if (_listeners.hasListeners()) {
            _listeners.fireEvent(new StateValueChangeEvent(_valueFunction, state, deltaValue));
        }
    }
}
