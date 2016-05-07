package com.eightycats.learning.neuralnet;

/**
 * Common interface for source that provides inputs to a neural net.
 */
public interface InputSource
{
    public void reset();

    public double[] nextInput();

    public boolean hasMoreInputs();
}
