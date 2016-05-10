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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.eightycats.learning.neuralnet.io.NeuralNetTextWriter;
import com.eightycats.math.functions.Function;
import com.eightycats.math.functions.Processor;
import com.eightycats.math.normalization.Normalizer;
import com.eightycats.math.normalization.PassthroughNormalization;
import com.eightycats.math.util.ArrayUtils;

/**
 * A simple backpropagation neural network.
 */
public class NeuralNet
    implements Processor
{
    protected InputLayer _inputLayer;

    protected Layer[] _layers;

    protected double _learningRate = .1;

    protected double _learningRateDecay = 0.0;

    protected double _momentum = 0.0;

    /**
     * Applied to the inputs to optionally keep them within a certain range.
     */
    protected Normalizer _normalizer = new PassthroughNormalization();

    /**
     * Creates a network with one hidden layer.
     *
     * @param inputCount number of inputs to this network.
     * @param hiddenNeurons the number of neurons in the  hidden layer.
     * @param outputCount number of expected outputs from this network.
     */
    public NeuralNet (int inputCount, int hiddenNeurons, int outputCount)
    {
        _inputLayer = new InputLayer(inputCount);
        _layers = new Layer[2];
        _layers[0] = new Layer(_inputLayer.getOutputCount(), hiddenNeurons);
        _layers[1] = new Layer(hiddenNeurons, outputCount);
    }

    /**
     * Creates a network with two hidden layers.
     *
     * @param inputCount number of inputs to this network.
     * @param hiddenLayer1Count the number of neurons in the first hidden layer.
     * @param hiddenLayer2Count the number of neurons in the second hidden layer.
     * @param outputCount number of expected outputs from this network.
     */
    public NeuralNet (int inputCount, int hiddenLayer1Count, int hiddenLayer2Count, int outputCount)
    {
        _inputLayer = new InputLayer(inputCount);
        _layers = new Layer[3];
        _layers[0] = new Layer(_inputLayer.getOutputCount(), hiddenLayer1Count);
        _layers[1] = new Layer(hiddenLayer1Count, hiddenLayer2Count);
        _layers[2] = new Layer(hiddenLayer2Count, outputCount);
    }

    public NeuralNet (InputLayer input, Layer[] layers)
    {
        _inputLayer = input;
        _layers = layers;
    }

    /**
     * Sets the threshold function on all of the layers.
     */
    public void setFunction (Function threshold)
    {
        for (int i = 0; i < _layers.length; i++) {
            _layers[i].setFunction(threshold);
        }
    }

    /**
     * Runs the given inputs through the network and returns the results.
     */
    @Override
    public double[] process (double[] inputs)
    {
        _inputLayer.setInputs(_normalizer.normalize(inputs));

        // the input layer adds a bias value, which is why we don't just pass the inputs directly
        double[] output = _inputLayer.getOutputs();

        for (int i = 0; i < _layers.length; i++) {
            output = _layers[i].process(output);
        }

        return output;
    }

    /**
     * Takes in the target outputs for the current input pattern, calculates the output error,
     * and backs up that error to train the network.
     *
     * @param expectedOutputs
     *            double[] the target training pattern expected for this network's current input.
     *
     * @return double[] the differences between the current network output and the expected output.
     */
    public double[] train (double[] expectedOutputs)
    {
        // the error values are the expected values minus the current output values
        double[] errors = ArrayUtils.copy(expectedOutputs);
        ArrayUtils.subtract(errors, getOutputs());

        // backpropagate the errors through this network's layers and update the connection weights
        backup(errors);

        return errors;
    }

    /**
     * @param errors the differences between the current network output and the expected output.
     */
    public void backup (double[] errors)
    {
        for (int i = _layers.length - 1; i >= 0; i--) {
            errors = _layers[i].backup(errors, _learningRate, _momentum);
        }

        // decay learning rate
        _learningRate -= (_learningRate * _learningRateDecay);
    }

    /**
     * This gets this network's output values from the last time that the process() method was
     * called.
     */
    public double[] getOutputs ()
    {
        // get the values from the output (last) layer
        return _layers[_layers.length - 1].getOutputs();
    }

    public double getLearningRate ()
    {
        return _learningRate;
    }

    public void setLearningRate (double learningRate)
    {
        _learningRate = learningRate;
    }

    public double getLearningRateDecay ()
    {
        return _learningRateDecay;
    }

    public double getMomentum ()
    {
        return _momentum;
    }

    public void setLearningRateDecay (double learningRateDecay)
    {
        _learningRateDecay = learningRateDecay;
    }

    public void setMomentum (double momentum)
    {
        _momentum = momentum;
    }

    /**
     * This randomizes the input weights for the neurons in this network.
     */
    public void randomize ()
    {
        for (int i = 0; i < _layers.length; i++) {
            _layers[i].randomize();
        }
    }

    /**
     * This returns the number of hidden layers plus one for the output layer. This does not count
     * the input layer.
     */
    public int getLayerCount ()
    {
        return _layers.length;
    }

    public Layer getLayer (int layerIndex)
    {
        return _layers[layerIndex];
    }

    public InputLayer getInputLayer ()
    {
        return _inputLayer;
    }

    /**
     * Sets a normalizer to apply to the inputs. To keep them within a certain range, for example.
     */
    public void setNormalizer (Normalizer normalizer)
    {
        _normalizer = normalizer;
    }

    @Override
    public String toString ()
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer);
        NeuralNetTextWriter.writeNetwork(this, output);
        return new String(buffer.toByteArray());
    }
}
