package com.eightycats.learning.reinforcement.util;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.State;

/**
 *
 *
 *
 */
public class StateActionPair
{
    private State state;

    private Action action;

    public StateActionPair( State state, Action action )
    {
       this.state = state;
       this.action = action;
    }

    public State getState()
    {
        return state;
    }

    public Action getAction()
    {
        return action;
    }

    public boolean equals( Object object )
    {
        boolean equals = false;

        if( object instanceof StateActionPair )
        {
            StateActionPair other = (StateActionPair) object;

            equals = state.equals( other.state ) &&
                     action.equals( other.action );

        }

        return equals;

    }

    /**
     * Used when this object is the key in 
     * a hash table.
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
       return state.hashCode() + action.hashCode();
    }
    
    
    public String toString()
    {
        return "("+state.toString()+", "+action.toString()+")";
    }

}
