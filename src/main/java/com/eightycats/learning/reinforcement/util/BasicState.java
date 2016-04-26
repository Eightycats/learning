package com.eightycats.learning.reinforcement.util;

import java.util.ArrayList;
import java.util.List;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.State;

/**
 * A simple State implementation with a name and a list of possible actions that can be taken from
 * this state.
 */
public class BasicState implements State
{
    protected String _name;

    protected boolean _terminal = false;

    protected List<Action> _actions = new ArrayList<Action>();

    public BasicState (String name)
    {
        _name = name;
    }

    public void addAction (Action action)
    {
        _actions.add(action);
    }

    @Override
    public Action[] getActions ()
    {
        Action[] array = new Action[_actions.size()];
        return _actions.toArray(array);
    }

    public String getName ()
    {
        return _name;
    }

    public void setName (String name)
    {
        _name = name;
    }

    @Override
    public boolean isTerminal ()
    {
        return _terminal;
    }

    /**
     * Sets whether or not this is a terminal (end) state.
     */
    public void setTerminal (boolean terminal)
    {
        _terminal = terminal;
    }

    @Override
    public String toString ()
    {
        return getName();
    }

    @Override
    public int hashCode ()
    {
        return _name.hashCode();
    }
}
