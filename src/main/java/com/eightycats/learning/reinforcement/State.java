package com.eightycats.learning.reinforcement;

/**
 * A common interface for States.
 */
public interface State
{
   /**
    * Returns the possible actions from this state.
    */
   public Action[] getActions();

   /**
    * Is this an end state?
    */
   public boolean isTerminal();

   @Override
   public boolean equals( Object object );
}
