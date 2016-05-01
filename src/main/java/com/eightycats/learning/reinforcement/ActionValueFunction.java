package com.eightycats.learning.reinforcement;

/**
 * Interface for getting at and updating the value of a given Action from a particular State.
 */
public interface ActionValueFunction
{
    public void reset();

    public double getValue( State s, Action a );

    public void update( State state, Action a, double deltaValue );
}
