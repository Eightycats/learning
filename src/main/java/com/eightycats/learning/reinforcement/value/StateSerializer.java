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
