package com.eightycats.learning.reinforcement.util;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.State;

/**
 * Eligibility trace that keeps track of what State we were in and which Action we took at each step
 * along the way.
 */
public class ActionEligibilityTrace extends EligibilityTrace<StateActionPair>
{
    public ActionEligibilityTrace ()
    {
        this(new EligibilityConfig());
    }

    public ActionEligibilityTrace (EligibilityConfig config)
    {
        super(config);
    }

    public void add (State state, Action action)
    {
        add(new StateActionPair(state, action));
    }

    public StateActionPair getStateActionPair (int index)
    {
        return get(index);
    }
}
