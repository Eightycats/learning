package com.eightycats.learning.reinforcement.value;

import com.eightycats.litterbox.math.Range;
import com.eightycats.learning.reinforcement.*;

/**
 * Implementations of this interface are used to convert a State into an array of double values.
 * This serialized representation of the state can then be passed conveniently as input to a neural
 * network, tile coding, or other value function.
 */
public interface StateSerializer
{
    /**
     * Returns an array of values that represent the given state.
     */
    public double[] serialize (State state);

    /**
     * Gets the number of values returned by calls to serialize().
     *
     * @return the length of the array returned by <code>serialize</code>.
     */
    public int getCount ();

    /**
     * Gets the range of possible values for the value at the give <code>index</code> in the
     * array returned by the <code>serialize()</code> method.
     *
     * @param index
     *            the output index whose range we want to know. This index must be less than the
     *            count returned by <code>getCount()</code>.
     * @return the range of possible output values.
     */
    public Range getValueRange (int index);

}
