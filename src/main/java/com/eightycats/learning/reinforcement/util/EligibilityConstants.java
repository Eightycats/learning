package com.eightycats.learning.reinforcement.util;

public interface EligibilityConstants
{

    public static final double DEFAULT_LAMBDA = 0.5;

    public static final double DEFAULT_MIN_ELIGIBILITY = 0.0001;

    /**
     * This is the default max length for a trace, if it's not restricted by the value of
     * lambda. Helps keeps the trace from growing forever.
     */
    public static final int DEFAULT_SIZE_LIMIT = 10 * 1024;
}
