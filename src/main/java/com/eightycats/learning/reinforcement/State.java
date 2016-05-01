package com.eightycats.learning.reinforcement;

/**
 * 
 *
 *
 */
public interface State
{

   /**
    * Returns the possible actions from this state.
    *
    * @return Action[]
    */
   public Action[] getActions();

   /**
    * Is this an end state?
    *
    * @return boolean
    */
   public boolean isTerminal();
 
   public boolean equals( Object object );   
   
}
