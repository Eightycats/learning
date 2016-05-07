package com.eightycats.learning.neuralnet.train;

import com.eightycats.learning.neuralnet.InputSource;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.litterbox.logging.Logger;
import com.eightycats.math.average.IncrementalAverage;
import com.eightycats.math.functions.Processor;
import com.eightycats.math.util.VectorMath;

/**
 *
 *
 */
public class TrainingHarness
{
    protected Processor expected;

    public TrainingHarness (Processor trainingFunction)
    {
        expected = trainingFunction;
    }

    public double[] getExpectedOutput (double[] inputs)
    {
        return expected.process(inputs);
    }

    public static void train (Processor trainingFunction, NeuralNet network,
        InputSource inputSource, int trainingRounds)
    {
        TrainingHarness harness = new TrainingHarness(trainingFunction);
        harness.train(network, inputSource, trainingRounds);
    }

    public void train (NeuralNet network, InputSource inputSource, int trainingRounds)
    {

        IncrementalAverage averageError = new IncrementalAverage();

        for (int count = 0; count < trainingRounds; count++) {

            // Logger.debug( "Training round: " + count);

            double[] inputs = inputSource.nextInput();

            // Logger.debug( "Inputs   : " + ArrayUtils.toString(inputs) );

            double[] output = network.process(inputs);

            double expectedValues[] = getExpectedOutput(inputs);

            // Logger.debug( "Output   : " + ArrayUtils.toString(output) );
            // Logger.debug( "Expected : " + ArrayUtils.toString(expectedValues) );

            double[] errorValues = network.train(expectedValues);

            double error = VectorMath.vectorLength(errorValues);

            // Logger.debug("Error magnitude: " + error );

            averageError.add(error);
            Logger.debug("Average error: " + averageError.getAverage());

        }

        Logger.debug(network.toString());
    }

}
