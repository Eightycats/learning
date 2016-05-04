package com.eightycats.learning.reinforcement.value.event;

import com.eightycats.learning.reinforcement.*;

/**
 * Interface for listening for changes to the value function.
 */
public interface StateValueModel extends ValueFunction
{
    public void addListener( StateValueChangeListener listener );

    public void removeListener( StateValueChangeListener listener );
}
