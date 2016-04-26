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
