package com.eightycats.learning.reinforcement.value;

import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.ValueFunction;
import com.eightycats.math.functions.Tanh;

/**
 * Value function backed by a neural network.
 */
public class NeuralNetValueFunction implements ValueFunction
{
    /**
     * This is used to convert a State into floating point values that can be passed as input to the
     * neural network.
     */
    protected StateSerializer _serializer;

    /**
     * The network used to store and generalize this value function.
     */
    protected NeuralNet _network;

    public NeuralNetValueFunction (StateSerializer serializer)
    {
        this(serializer, 0, 0);
    }

    public NeuralNetValueFunction (StateSerializer serializer, int hiddenLayerCount)
    {
        this(serializer, hiddenLayerCount, 0);
    }

    public NeuralNetValueFunction (StateSerializer serializer, int hiddenLayerCount1,
        int hiddenLayerCount2)
    {

        _serializer = serializer;

        // if no hidden layer count was given, then use a rule of thumb to
        // generate the number of hidden layer neurons
        if (hiddenLayerCount1 <= 0) {
            hiddenLayerCount1 = serializer.getCount();

            if (hiddenLayerCount2 > 0) {
                hiddenLayerCount1 *= hiddenLayerCount2;
                hiddenLayerCount1 = (int) Math.sqrt(hiddenLayerCount1);
            }
        }

        if (hiddenLayerCount2 > 0) {
            _network = new NeuralNet(serializer.getCount(), hiddenLayerCount1, hiddenLayerCount2, 1);
        } else {
            _network = new NeuralNet(serializer.getCount(), hiddenLayerCount1, 1);
        }

        _network.setFunction(new Tanh());
        _network.randomize();
    }

    public NeuralNetValueFunction (StateSerializer serializer, NeuralNet network)
    {
        _serializer = serializer;
        _network = network;
    }

    @Override
    public void reset ()
    {
        _network.randomize();
    }

    @Override
    public double getValue (State state)
    {
        double[] inputs = _serializer.serialize(state);

        // forward prop inputs
        double[] outputs = _network.process(inputs);

        return outputs[0];

    }

    @Override
    public void update (State state, double deltaValue)
    {
        double[] inputs = _serializer.serialize(state);

        // pass the inputs through the network
        // so that it know which inputs these error
        // values are for
        _network.process(inputs);

        // create an array to hold the update value
        double[] errors = new double[1];
        errors[0] = deltaValue;

        // train the network by passing it
        // the update value as an error
        _network.backup(errors);
    }

    public NeuralNet getNetwork ()
    {
        return _network;
    }

    public double getLearningRate ()
    {
        return _network.getLearningRate();
    }

    public void setLearningRate (double learningRate)
    {
        _network.setLearningRate(learningRate);
    }

    public double getLearningRateDecay ()
    {
        return getLearningRateDecay();
    }

    public void setLearningRateDecay (double learningRateDecay)
    {
        _network.setLearningRateDecay(learningRateDecay);
    }

    @Override
    public String toString ()
    {
        return _network.toString();
    }
}
