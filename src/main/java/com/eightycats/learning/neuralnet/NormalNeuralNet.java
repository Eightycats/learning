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

package com.eightycats.learning.neuralnet;

import com.eightycats.math.functions.Function;
import com.eightycats.math.functions.Processor;
import com.eightycats.math.normalization.Normalizer;
import com.eightycats.math.normalization.PassthroughNormalization;
import com.eightycats.math.util.ArrayUtils;

/**
 * A simple backpropagation neural network.
 */
public class NormalNeuralNet implements Processor
{
    protected InputLayer inputLayer;

    protected Layer[] hiddenLayers;

    protected Layer outputLayer;

    protected double learningRate = .1;

    protected double learningRateDecay = 0.0;

    protected double momentum = 0.0;

    protected Normalizer normalization = new PassthroughNormalization();

    /**
     * Creates a network with one hidden layer.
     *
     * @param inputCount
     *            int
     * @param hiddenNeurons
     *            int
     * @param outputCount
     *            int
     */
    public NormalNeuralNet (int inputCount, int hiddenNeurons, int outputCount)
    {
        inputLayer = new InputLayer(inputCount);
        hiddenLayers = new Layer[1];
        hiddenLayers[0] = new Layer(inputLayer.getOutputCount(), hiddenNeurons);
        outputLayer = new Layer(hiddenNeurons, outputCount);
    }

    /**
     * Creates a network with two hidden layers.
     *
     * @param inputCount
     *            int
     * @param hiddenLayer1Count
     *            int
     * @param hiddenLayer2Count
     *            int
     * @param outputCount
     *            int
     */
    public NormalNeuralNet (int inputCount, int hiddenLayer1Count, int hiddenLayer2Count,
        int outputCount)
    {
        inputLayer = new InputLayer(inputCount);
        hiddenLayers = new Layer[2];
        hiddenLayers[0] = new Layer(inputLayer.getOutputCount(), hiddenLayer1Count);
        hiddenLayers[1] = new Layer(hiddenLayer1Count, hiddenLayer2Count);
        outputLayer = new Layer(hiddenLayer2Count, outputCount);
    }

    public void setFunction (Function threshold)
    {
        for (int i = 0; i < hiddenLayers.length; i++) {
            hiddenLayers[i].setFunction(threshold);
        }
        outputLayer.setFunction(threshold);
    }

    /**
     * This runs the given inputs through the network and returns the results.
     */
    @Override
    public double[] process (double[] inputs)
    {
        inputLayer.setInputs(normalization.normalize(inputs));

        double[] output = inputLayer.getOutputs();
        double[] hiddenInput = output;

        for (int i = 0; i < hiddenLayers.length; i++) {
            output = hiddenLayers[i].process(hiddenInput);

            // if there are more layers, the output  of this layer becomes the input for the next
            hiddenInput = output;
        }

        return outputLayer.process(output);
    }

    /**
     * This takes in the target outputs for the current input pattern, calculates the output error,
     * and backs up that error to train the network.
     *
     * @param expectedOutputs
     *            double[] the target training pattern expected for this network's current input.
     *
     * @return double[] the differences between the current network output and the expected output.
     */
    public double[] train (double[] expectedOutputs)
    {
        // the error values are the expected values
        // minus the current output values
        double[] errors = ArrayUtils.copy(expectedOutputs);
        ArrayUtils.subtract(errors, outputLayer.getOutputs());

        // backpropagate the errors through this network's
        // layers and update the connection weights
        backup(errors);

        return errors;

    }

    public void backup (double[] errors)
    {
        errors = outputLayer.backup(errors, learningRate, momentum);

        for (int i = hiddenLayers.length - 1; i >= 0; i--) {
            errors = hiddenLayers[i].backup(errors, learningRate, momentum);
        }

        // decay learning rate
        learningRate -= (learningRate * learningRateDecay);
    }

    public double getLearningRate ()
    {
        return learningRate;
    }

    public void setLearningRate (double learningRate)
    {
        this.learningRate = learningRate;
    }

    public double getLearningRateDecay ()
    {
        return learningRateDecay;
    }

    public void setLearningRateDecay (double learningRateDecay)
    {
        this.learningRateDecay = learningRateDecay;
    }

    public double getMomentum ()
    {
        return momentum;
    }

    public void setMomentum (double momentum)
    {
        this.momentum = momentum;
    }

    public void randomize ()
    {

        for (int i = 0; i < hiddenLayers.length; i++) {
            hiddenLayers[i].randomize();
        }

        outputLayer.randomize();

    }

    public void setNormalization (Normalizer normalizer)
    {
        normalization = normalizer;
    }

}
