package com.eightycats.learning.reinforcement.value.event;

import com.eightycats.litterbox.event.EventSupportBase;

public class StateValueChangeSupport
    extends EventSupportBase<Object, StateValueChangeListener, StateValueChangeEvent>
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void publishEvent (StateValueChangeListener listener, StateValueChangeEvent event)
    {
        listener.valueChanged(event);
    }
}
