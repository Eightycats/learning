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

package com.eightycats.learning.neuralnet.test;

import com.eightycats.learning.neuralnet.*;

import com.eightycats.math.functions.*;
import com.eightycats.math.IncrementalAverage;
import com.eightycats.math.*;

/**
 *
 *
 */
public class PositiveNegativeTest
{

    public static void main (String[] args)
    {
        try {
            int total = 10000;

            NeuralNet network = new NeuralNet(1, 13, 1);

            network.setFunction(new Tanh());
            network.setLearningRate(.2);

            double[] inputs = new double[1];

            for (int count = 0; count < total; count++) {

                System.out.println("\n\nTraining round: " + count);

                double[] trainingValues = new double[1];

                switch (count % 5) {

                case 0:
                    inputs[0] = -1.0;
                    trainingValues[0] = 0.0;
                    break;

                case 1:
                    inputs[0] = -0.5;
                    trainingValues[0] = 1.0;
                    break;

                case 2:
                    inputs[0] = 0.0;
                    trainingValues[0] = 0.0;
                    break;

                case 3:
                    inputs[0] = 0.5;
                    trainingValues[0] = -1.0;
                    break;

                case 4:
                    inputs[0] = 1.0;
                    trainingValues[0] = 0.0;
                    break;

                }

                System.out.println("Input[" + count + "]: " + inputs[0]);

                double[] output = network.process(inputs);

                System.out.println("Output   : " + output[0]);
                System.out.println("Expected : " + trainingValues[0]);

                double[] errorValues = network.train(trainingValues);

                System.out.println("Error magnitude: " + VectorMath.vectorLength(errorValues));

                System.out.println("Learning Rate: " + network.getLearningRate());

            }

            network.setLearningRateDecay(.0001);

            for (int count = 0; count < total; count++) {

                System.out.println("\n\nTraining round: " + count);

                double[] trainingValues = new double[1];

                switch (count % 5) {

                case 0:
                    inputs[0] = -1.0;
                    trainingValues[0] = 0.0;
                    break;

                case 1:
                    inputs[0] = -0.5;
                    trainingValues[0] = 1.0;
                    break;

                case 2:
                    inputs[0] = 0.0;
                    trainingValues[0] = 0.0;
                    break;

                case 3:
                    inputs[0] = 0.5;
                    trainingValues[0] = -1.0;
                    break;

                case 4:
                    inputs[0] = 1.0;
                    trainingValues[0] = 0.0;
                    break;

                }

                double[] output = network.process(inputs);

                System.out.println("Output   : " + output[0]);
                System.out.println("Expected : " + trainingValues[0]);

                double[] errorValues = network.train(trainingValues);

                System.out.println("Error magnitude: " + VectorMath.vectorLength(errorValues));

                System.out.println("Learning Rate: " + network.getLearningRate());

            }

            System.out.println("\n\nTraining complete.\n\n");

            double sampleCount = 100;
            double range = 2.0;
            double increment = .02;
            range -= 1.0;

            IncrementalAverage average = new IncrementalAverage();

            for (double i = -1.0; i <= range; i += increment) {

                inputs[0] = i;

                System.out.print(inputs[0]);
                System.out.print(",");

                double[] output = network.process(inputs);

                System.out.print(output[0]);
                System.out.println();

            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

}
