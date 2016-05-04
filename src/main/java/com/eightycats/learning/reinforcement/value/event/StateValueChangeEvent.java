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
