package com.eightycats.learning.neuralnet;


/**
 * A stateful input layer. It maintains a copy of the latest input values that it was
 * passed. This input layer is biased, meaning that the value of the output at index 0 will always
 * be 1 by default.
 */
public class InputLayer
{
    /**
     * The latest output values.
     */
    protected double[] _outputs;

    public InputLayer (int inputCount)
    {
        _outputs = new double[inputCount + 1];
    }

    public InputLayer (double[] inputs)
    {
        this(inputs.length);
        setInputs(inputs);
        // Use a bias input value of 1 by default
        setBias(1.0);
    }

    public void setInputs (double[] inputs)
    {
        // skip over the bias slot
        System.arraycopy(inputs, 0, _outputs, 1, inputs.length);
    }

    public void setInput (int index, double value)
    {
        _outputs[index + 1] = value;
    }

    public int getInputCount ()
    {
        return _outputs.length - 1;
    }

    public double getOutput (int index)
    {
        return _outputs[index];
    }

    /**
     * Gets the number of output values from this layer.
     */
    public int getOutputCount ()
    {
        return _outputs.length;
    }

    /**
    * Gets the output values.
    */
    public double[] getOutputs ()
    {
        return _outputs;
    }

    /**
     * In case you really want to change the bias value. The bias is 1 by default.
     */
    public void setBias (double biasValue)
    {
        _outputs[0] = biasValue;
    }

    public double getBias ()
    {
        return _outputs[0];
    }
}
