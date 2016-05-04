package com.eightycats.learning.neuralnet.test;

import com.eightycats.learning.neuralnet.*;

import com.eightycats.litterbox.math.functions.*;
import com.eightycats.litterbox.math.IncrementalAverage;
import com.eightycats.litterbox.math.*;

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
