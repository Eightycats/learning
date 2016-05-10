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

package com.eightycats.learning.reinforcement.value;

import com.eightycats.learning.reinforcement.State;
import com.eightycats.math.util.Range;

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
