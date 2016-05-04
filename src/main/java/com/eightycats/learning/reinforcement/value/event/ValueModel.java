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
