package com.eightycats.learning.reinforcement.value.event;

import com.eightycats.learning.reinforcement.*;
import java.util.*;

/**
 *
 *
 *
 */
public class StateValueChangeEvent extends EventObject
{

    private State state;

    private double value;

    public StateValueChangeEvent( ValueFunction source,
                                  State state,
                                  double value)
    {
        super( source );
        this.state = state;
        this.value = value;
    }

    public State getState()
    {
        return state;
    }

    public double getValue()
    {
        return value;
    }

}
