package com.eightycats.learning.reinforcement.value.event;

import com.eightycats.learning.reinforcement.*;

/**
 *
 *
 */
public class ValueModel implements StateValueModel
{

    protected ValueFunction valueFunction;

    protected StateValueChangeSupport listeners;

    public ValueModel (ValueFunction valueFunction)
    {
        this.valueFunction = valueFunction;
        listeners = new StateValueChangeSupport();
    }

    @Override
    public void addListener (StateValueChangeListener listener)
    {
        listeners.addListener(listener);
    }

    @Override
    public void removeListener (StateValueChangeListener listener)
    {
        listeners.removeListener(listener);
    }

    @Override
    public void reset ()
    {
        valueFunction.reset();
    }

    @Override
    public double getValue (State s)
    {
        return valueFunction.getValue(s);
    }

    @Override
    public void update (State state, double deltaValue)
    {
        valueFunction.update(state, deltaValue);
        listeners.fireEvent(new StateValueChangeEvent(valueFunction, state, deltaValue));
    }
}
