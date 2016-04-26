package com.eightycats.learning.reinforcement.value.event;

import com.eightycats.learning.reinforcement.*;

/**
 *
 *
 */
public interface StateValueModel extends ValueFunction
{
    public void addListener( StateValueChangeListener listener );

    public void removeListener( StateValueChangeListener listener );
}
