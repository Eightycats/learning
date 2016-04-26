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

    public ValueModel( ValueFunction valueFunction )
    {
       this.valueFunction = valueFunction;
       listeners = new StateValueChangeSupport();
    }

    public void addListener(StateValueChangeListener listener)
    {
        listeners.addListener( listener );
    }

    public void removeListener(StateValueChangeListener listener)
    {
       listeners.removeListener( listener );
    }

    public void reset()
    {
        valueFunction.reset();
    }

    public double getValue(State s)
    {
        return valueFunction.getValue(s);
    }

    public void update(State state, double deltaValue)
    {
        valueFunction.update(state, deltaValue);

        StateValueChangeEvent event =
            new StateValueChangeEvent( valueFunction, state, deltaValue );

        listeners.fireEvent( event );

    }
}
