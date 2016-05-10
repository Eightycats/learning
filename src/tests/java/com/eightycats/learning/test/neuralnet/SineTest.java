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

package com.eightycats.learning.test.neuralnet;

import java.util.Random;

import com.eightycats.learning.neuralnet.InputSource;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.learning.neuralnet.train.TrainingHarness;
import com.eightycats.math.average.IncrementalAverage;
import com.eightycats.math.normalization.Normalizer;

/**
 * Trains a neural net against a sine wave.
 */
public class SineTest extends TestBase
    implements InputSource, Normalizer
{

    private static Random random = new Random();

    @Override
    public double[] process (double[] input)
    {
        double[] result = new double[1];
        result[0] = function(input[0]);
        return result;
    }

    public double function (double x)
    {
        return Math.sin(x) / 2;
    }

    @Override
    public double[] normalize (double[] input)
    {
        return new double[] { normalize(input[0]) };
    }

    @Override
    public double normalize (double input)
    {
        return input / (2 * Math.PI);
    }

    @Override
    public void reset ()
    {
    }

    @Override
    public double[] nextInput ()
    {
        double[] inputs = new double[1];
        inputs[0] = random.nextDouble() * 2 * Math.PI;
        return inputs;
    }

    @Override
    public boolean hasMoreInputs ()
    {
        return true;
    }

    public void train ()
    {

        try {
            NeuralNet network = train(1, 21, 1, .2, 0, 0.0, this, 10000);

            TrainingHarness trainer = new TrainingHarness(this);
            network.setLearningRateDecay(.0001);
            trainer.train(network, this, 20000);

            System.out.println("\n\nTraining complete.\n\n");

            System.out.println("Degrees, Network Output, Actual Value, Error, Avg. Error");

            double sampleCount = 100;
            double twoPI = 2 * Math.PI;
            double increment = twoPI / sampleCount;

            IncrementalAverage average = new IncrementalAverage();

            double[] inputs = new double[1];
            for (double i = 0; i <= twoPI; i += increment) {

                inputs[0] = i;
                // inputs[0] = this.normalize(i);

                System.out.print(Math.toDegrees(i));
                System.out.print(",");

                double[] output = network.process(inputs);

                System.out.print(output[0]);
                System.out.print(",");

                double[] expected = this.process(inputs);
                System.out.print(expected[0]);
                System.out.print(",");

                double error = output[0] - expected[0];
                System.out.print(error);
                System.out.print(",");

                average.add(error);

                System.out.println(average.getAverage());
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    public static void main (String[] args)
    {
        SineTest test = new SineTest();
        test.train();
    }

}
