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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.learning.neuralnet.Neuron;
import com.eightycats.litterbox.xml.dom.DocumentAccessor;
import com.eightycats.litterbox.xml.dom.DocumentParser;
import com.eightycats.math.functions.Tanh;

/**
 * Writes out the contents of a NeuralNet as XML.
 */
public class NeuralNetXMLWriter
    implements NeuralNetWriter
{
    public static void writeNetwork (NeuralNet network, PrintStream output)
        throws Exception
    {
        Document document = DocumentParser.createDocument(NeuralNetXMLConstants.NETWORK);

        Element root = document.getDocumentElement();

        DocumentAccessor generator = new DocumentAccessor(document);

        // write out the input layer
        Node inputLayerNode = generator.addChild(root, NeuralNetXMLConstants.INPUT_LAYER);

        InputLayer inputs = network.getInputLayer();
        String inputCount = Integer.toString(inputs.getInputCount());
        generator.setAttribute(inputLayerNode, NeuralNetXMLConstants.COUNT, inputCount);

        String bias = Double.toString(inputs.getBias());
        generator.setAttribute(inputLayerNode, NeuralNetXMLConstants.BIAS, bias);

        int layerCount = network.getLayerCount();

        for (int i = 0; i < layerCount; i++) {
            Node layerNode = generator.addChild(root, NeuralNetXMLConstants.LAYER);

            Layer layer = network.getLayer(i);

            String functionClass = layer.getFunction().getClass().getName();
            generator.setAttribute(layerNode, NeuralNetXMLConstants.FUNCTION, functionClass);

            int neuronCount = layer.getNeuronCount();
            for (int j = 0; j < neuronCount; j++) {
                Node neuronNode = generator.addChild(layerNode, NeuralNetXMLConstants.NEURON);

                Neuron neuron = layer.getNeuron(j);
                int weightCount = neuron.getWeightCount();
                for (int k = 0; k < weightCount; k++) {
                    Node weightNode = generator.addChild(neuronNode, NeuralNetXMLConstants.WEIGHT);
                    DocumentAccessor.setText(weightNode, Double.toString(neuron.getWeight(k)));
                }
            }

        }
        output.println(generator.generate());
        output.flush();
    }

    @Override
    public void write (NeuralNet network, PrintStream output)
        throws Exception
    {
        writeNetwork(network, output);
    }

    public static void main (String[] args)
    {
        NeuralNet network = new NeuralNet(3, 5, 3, 1);
        network.setFunction(Tanh.getInstance());
        network.randomize();
        try {
            NeuralNetXMLWriter.writeNetwork(network, System.out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
