package com.eightycats.learning.reinforcement.value;

import com.eightycats.learning.reinforcement.*;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.litterbox.math.functions.Tanh;


/**
 *
 *
 *
 */
public class NeuralNetValueFunction implements ValueFunction
{

    /**
     * This is used to convert a State into
     * floating point values that can be passed
     * as input to the neural network.
     */
    protected StateSerializer serializer;

    /**
     * The network used to store and generalize
     * this value function.
     */
    protected NeuralNet network;

    public NeuralNetValueFunction( StateSerializer serializer )
    {
        this( serializer, 0, 0 );
    }


    public NeuralNetValueFunction(StateSerializer serializer,
                                  int hiddenLayerCount )
    {
       this( serializer, hiddenLayerCount, 0 );
    }

    public NeuralNetValueFunction(StateSerializer serializer,
                                  int hiddenLayerCount1,
                                  int hiddenLayerCount2 )
    {

        this.serializer = serializer;

        // if no hidden layer count was given,
        // then use a rule of thumb to
        // generate the number of hidden layer neurons
        // to use
        if( hiddenLayerCount1 <= 0 )
        {
           hiddenLayerCount1 = serializer.getCount();

           if( hiddenLayerCount2 > 0 )
           {
              hiddenLayerCount1 *= hiddenLayerCount2;
              hiddenLayerCount1 = (int) Math.sqrt(hiddenLayerCount1);
           }

        }

        if( hiddenLayerCount2 > 0 )
        {
           network = new NeuralNet( serializer.getCount(), hiddenLayerCount1, hiddenLayerCount2, 1 );
        }
        else
        {
           network = new NeuralNet( serializer.getCount(), hiddenLayerCount1, 1 );
        }

        network.setFunction( new Tanh() );
        network.randomize();

    }

    public NeuralNetValueFunction( StateSerializer serializer,
                                   NeuralNet network )
    {
       this.serializer = serializer;
       this.network = network;
    }

    @Override
    public void reset()
    {
        network.randomize();
    }

    @Override
    public double getValue(State state)
    {

        double[] inputs = serializer.serialize(state);

        // forward prop inputs
        double[] outputs = network.process( inputs );

        return outputs[0];

    }

    @Override
    public void update(State state, double deltaValue)
    {
        double[] inputs = serializer.serialize(state);

        // pass the inputs through the network
        // so that it know which inputs these error
        // values are for
        network.process( inputs );

        // create an array to hole the update value
        double[] errors = new double[1];
        errors[0] = deltaValue;

        // train the network by passing it
        // the update value as an error
        network.backup( errors );
    }

    public NeuralNet getNetwork()
    {
       return network;
    }

    public double getLearningRate()
    {
       return network.getLearningRate();
    }

    public void setLearningRate( double learningRate )
    {
       network.setLearningRate( learningRate );
    }

    public double getLearningRateDecay()
    {
       return getLearningRateDecay();
    }

    public void setLearningRateDecay( double learningRateDecay )
    {
       network.setLearningRateDecay( learningRateDecay );
    }

    @Override
    public String toString()
    {
       return network.toString();
    }

}
