package com.eightycats.learning.reinforcement.policy;

import com.eightycats.learning.reinforcement.*;

/**
 * This policy always selects the action
 * that is most valuable according to the 
 * given value function.
 *
 *
 */
public class GreedyPolicy extends EGreedyPolicy
{
   public GreedyPolicy( ValueFunction valueFunction )
   {
       super( 0.0, valueFunction );
   }
}
