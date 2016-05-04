package com.eightycats.learning.reinforcement;

/**
 * An Action that can be performed in a given State.
 */
public interface Action
{
    /**
     * Performs this action from the given State and returns the new State.
     */
    public State perform (State currentState);

    /**
     * Peeks at the next State if this action is performed.
     */
    public State getNextState (State currentState);

    @Override
    public boolean equals (Object object);

    @Override
    public String toString ();
}
