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

package com.eightycats.learning.neuralnet.train;

import com.eightycats.learning.neuralnet.InputSource;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.litterbox.logging.Logger;
import com.eightycats.math.average.IncrementalAverage;
import com.eightycats.math.functions.Processor;
import com.eightycats.math.util.VectorMath;

/**
 * A harness for training a network.
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
