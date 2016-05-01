package com.eightycats.learning.reinforcement.policy;

import java.util.Random;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.State;

/**
 * This policy selects a possible action at random.
 */
public class RandomPolicy
{
    protected static Random random = new Random();

    public Action selectAction (State state)
    {
        Action[] actions = state.getActions();
        return selectAction(actions);
    }

    public static Action selectAction (Action[] actions)
    {
        return actions[random.nextInt(actions.length)];
    }
}
