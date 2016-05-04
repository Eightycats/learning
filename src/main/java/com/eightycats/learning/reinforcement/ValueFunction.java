package com.eightycats.learning.reinforcement;

/**
 * Common interface for getting the current value of a state.
 */
public interface ValueFunction
{
    public void reset ();

    public double getValue (State s);

    public void update (State state, double deltaValue);
}
