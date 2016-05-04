package com.eightycats.learning.reinforcement;

/**
 * Policies select which action to perform for a given State.
 */
public interface Policy
{
    public Action selectAction (State state);
}
