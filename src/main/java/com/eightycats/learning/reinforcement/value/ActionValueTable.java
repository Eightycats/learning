/**
 *
 */
package com.eightycats.learning.reinforcement.value;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.util.StateActionPair;

/**
 *
 *
 */
public class ActionValueTable extends ValueTableBase<StateActionPair>
    implements ActionValueFunction
{

    @Override
    public double getValue (State state, Action action)
    {
        return super.getValue(getKey(state, action));
    }

    public void setValue (State state, Action action, double value)
    {
        super.setValue(getKey(state, action), value);
    }

    @Override
    public void update (State state, Action action, double deltaValue)
    {
        super.update(getKey(state, action), deltaValue);
    }

    protected StateActionPair getKey (State state, Action action)
    {
        return new StateActionPair(state, action);
    }
}
