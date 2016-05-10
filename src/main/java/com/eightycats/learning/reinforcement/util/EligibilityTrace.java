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

package com.eightycats.learning.reinforcement.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a trace of visited states that are eligible for having their value updated
 * in the value function.
 */
public class EligibilityTrace<S> implements EligibilityConstants
{
    /**
     * Settings to use for this trace.
     */
    protected EligibilityConfig _config;

    /**
     * The list/trace of past states eligible for having their value updated.
     */
    protected List<S> _trace = new ArrayList<S>();


    public EligibilityTrace ()
    {
        this(new EligibilityConfig());
    }

    public EligibilityTrace (EligibilityConfig config)
    {
        setConfig(config);
    }

    public void setConfig (EligibilityConfig config)
    {
        _config = config;
    }

    public void add (S eligible)
    {
        _trace.add(0, eligible);
        truncate();
    }

    public S get (int index)
    {
        return _trace.get(index);
    }

    public int getLength ()
    {
        return _trace.size();
    }

    public void clear ()
    {
        _trace.clear();
    }

    public double getEligibility (int index)
    {
        return Math.pow(_config.getDiscountedLambda(), index);
    }

    /**
     * This is called when the max length (lambda) changes in order to make sure that the trace size
     * does not exceed a max length.
     */
    protected void truncate ()
    {
        // trim the size of the trace to the max length
        if (_trace.size() > _config.getMaxTraceLength()) {
            _trace = _trace.subList(0, _config.getMaxTraceLength());
        }
    }

    /**
     * Dump the contents of the trace and current eligibility values.
     */
    @Override
    public String toString ()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("Length: ");
        buffer.append(getLength());
        buffer.append("\n");

        buffer.append("Max Length: ");
        buffer.append(_config.getMaxTraceLength());
        buffer.append("\n");

        for (int i = 0; i < getLength(); i++) {
            buffer.append(get(i));
            buffer.append(" : ");
            buffer.append(getEligibility(i));
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
