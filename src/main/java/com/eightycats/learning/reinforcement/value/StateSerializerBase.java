package com.eightycats.learning.reinforcement.value;

import com.eightycats.litterbox.math.Range;
import com.eightycats.learning.reinforcement.State;

/**
 * Base class for serializing states to values between 0.0 and 1.0.
 */
public abstract class StateSerializerBase
    implements StateSerializer
{
    /**
     * Number of outputs.
     */
    protected int _count;

    protected Range[] _ranges;

    public StateSerializerBase (int outputCount)
    {
        _count = outputCount;

        // default the output range values from 0.0 to 1.0
        _ranges = new Range[_count];

        for (int i = 0; i < _ranges.length; i++) {
            _ranges[i] = Range.UNIT_RANGE;
        }
    }

    /**
     * Returns the length of the array returned by this class's serialize method.
     */
    @Override
    public int getCount ()
    {
        return _count;
    }

    public void setValueRange (int index, double minValue, double maxValue)
    {
        setValueRange(index, new Range(minValue, maxValue));
    }

    public void setValueRange (int index, Range range)
    {
        _ranges[index] = range;
    }

    @Override
    public Range getValueRange (int index)
    {
        return _ranges[index];
    }

    @Override
    public abstract double[] serialize (State state);
}
