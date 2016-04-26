package com.eightycats.learning.neuralnet.test;

import com.eightycats.learning.neuralnet.*;
import com.eightycats.learning.neuralnet.train.*;
import com.eightycats.litterbox.math.Processor;
import com.eightycats.litterbox.math.functions.Tanh;

/**
 *
 *
 *
 */
public abstract class TestBase implements Processor
{



    public NeuralNet train(int inputCount,
                           int hiddenCount,
                           int outputCount,
                           double learningRate,
                           double learningRateDecay,
                           double momentum,
                           InputSource trainingInput,
                           int trainingRounds)
    {

       TrainingHarness trainer = new TrainingHarness( this );


       NeuralNet network =
            new NeuralNet( inputCount, hiddenCount, outputCount );

       network.setFunction( new Tanh() );
       network.setLearningRate( learningRate );
       network.setLearningRateDecay( learningRateDecay );
       network.setMomentum( momentum );

       trainer.train( network, trainingInput, trainingRounds );

       return network;

    }

}
