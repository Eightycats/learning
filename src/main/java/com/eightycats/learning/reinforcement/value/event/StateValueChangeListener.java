package com.eightycats.learning.reinforcement.value.event;

import java.util.*;

/**
 * Interface for listening for StateValueChangeEvents.
 */
public interface StateValueChangeListener extends EventListener
{
    public void valueChanged (StateValueChangeEvent event);
}
