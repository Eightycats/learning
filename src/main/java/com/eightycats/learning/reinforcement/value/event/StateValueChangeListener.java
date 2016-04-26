package com.eightycats.learning.reinforcement.value.event;

import java.util.*;

/**
 *
 *
 *
 */
public interface StateValueChangeListener extends EventListener
{
    public void valueChanged( StateValueChangeEvent event );
}
