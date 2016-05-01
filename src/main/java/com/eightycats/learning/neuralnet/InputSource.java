package com.eightycats.learning.neuralnet;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 *
 * @version 1.0
 */
public interface InputSource
{

    public void reset();

    public double[] nextInput();

    public boolean hasMoreInputs();

}
