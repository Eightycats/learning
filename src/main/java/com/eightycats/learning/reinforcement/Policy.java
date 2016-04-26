package com.eightycats.learning.reinforcement;

/**
 * Policies are used to select which action to perform.
 *
 *
 */
public interface Policy
{

   public Action selectAction( State state );

}
