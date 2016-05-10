/**
 * Copyright 2016 Matthew A Jensen <eightycats@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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
