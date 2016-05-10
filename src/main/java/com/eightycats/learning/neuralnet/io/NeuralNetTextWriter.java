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

package com.eightycats.learning.neuralnet.io;

import java.io.PrintStream;

import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.Neuron;
import com.eightycats.learning.neuralnet.NeuralNet;

/**
 * Logs the values of a neural net as plain text.
 */
public class NeuralNetTextWriter
    implements NeuralNetWriter
{
    protected static NeuralNetTextWriter writer = new NeuralNetTextWriter();

    public static void print (NeuralNet network)
    {
        writeNetwork(network, System.out);
    }

    public static void writeNetwork (NeuralNet network, PrintStream output)
    {
        writer.write(network, output);
    }

    @Override
    public void write (NeuralNet network, PrintStream output)
    {
        output.println("Network:");

        output.println("Learning rate       : " + network.getLearningRate());
        output.println("Learning rate decay : " + network.getLearningRateDecay());
        output.println("Momentum            : " + network.getMomentum());

        InputLayer inputs = network.getInputLayer();
        output.println("Input count         : " + inputs.getInputCount());
        output.println("Input bias          : " + inputs.getBias());

        int layerCount = network.getLayerCount();

        for (int i = 0; i < layerCount; i++) {
            output.println("Layer [" + i + "]:");
            Layer layer = network.getLayer(i);
            output.println("Function: " + layer.getFunction().getClass().getName());
            int neuronCount = layer.getNeuronCount();
            for (int j = 0; j < neuronCount; j++) {
                output.println("Neuron [" + j + "]:");
                Neuron neuron = layer.getNeuron(j);
                int weightCount = neuron.getWeightCount();
                for (int k = 0; k < weightCount; k++) {
                    output.println("Weight [" + k + "]: " + neuron.getWeight(k));
                }
                output.println();
            }
        }

        output.flush();
    }
}
