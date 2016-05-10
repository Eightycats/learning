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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.learning.neuralnet.Neuron;
import com.eightycats.litterbox.logging.Logger;
import com.eightycats.litterbox.xml.sax.XMLHandlerBase;
import com.eightycats.math.functions.Function;

/**
 * This is a utility class for reading a NeuralNet instance from XML.
 */
public class NeuralNetXMLReader extends XMLHandlerBase
    implements NeuralNetReader
{
    protected InputLayer _inputLayer;

    protected List<Layer> _layers = new ArrayList<Layer>();

    protected Function _currentFunction = null;

    protected List<Neuron> _currentLayerNeurons = new ArrayList<Neuron>();

    protected List<Double> _currentNeuronWeights = new ArrayList<Double>();

    public void reset ()
    {
        _inputLayer = null;
        _currentLayerNeurons.clear();
        _currentNeuronWeights.clear();
        _currentFunction = null;
        _layers.clear();
    }

    public NeuralNet readNetwork (String xmlFilePath)
        throws Exception
    {
        NeuralNet network = null;
        FileReader file = new FileReader(xmlFilePath);
        try {
            BufferedReader reader = new BufferedReader(file);
            network = readNetwork(reader);
        } finally {
            try {
                file.close();
            } catch (Exception ex) {
                Logger.error("Could not close file [" + xmlFilePath
                    + "] while reading neural network from XML.");
            }
        }
        return network;
    }

    public static NeuralNet readNetwork (Reader input)
        throws Exception
    {
        return new NeuralNetXMLReader().read(input);
    }

    @Override
    public NeuralNet read (Reader xmlInput)
        throws Exception
    {
        // make sure to clear out any previously parsed info
        reset();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        InputSource xmlSource = new InputSource(xmlInput);

        parser.parse(xmlSource, this);

        // create new network and return it
        Layer[] layerArray = new Layer[_layers.size()];
        layerArray = _layers.toArray(layerArray);

        return new NeuralNet(_inputLayer, layerArray);
    }

    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes)
        throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);

        // parse the input layer
        if (qName.equals(NeuralNetXMLConstants.INPUT_LAYER)) {
            String countValue = attributes.getValue(NeuralNetXMLConstants.COUNT);
            int count = Integer.parseInt(countValue);

            String biasValue = attributes.getValue(NeuralNetXMLConstants.BIAS);
            double bias = Double.parseDouble(biasValue);

            _inputLayer = new InputLayer(count);
            _inputLayer.setBias(bias);

        } else if (qName.equals(NeuralNetXMLConstants.LAYER)) {

            // create function
            String functionClassName = attributes.getValue(NeuralNetXMLConstants.FUNCTION);
            try {
                Class<?> functionClass = Class.forName(functionClassName);
                // no class cast check here
                _currentFunction = (Function) functionClass.newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.error("Could not find function class: " + functionClassName);
                throw new SAXException(ex);
            } catch (Exception ex) {
                Logger.error("Could not instantiate function class: " + functionClassName);
                throw new SAXException(ex);
            }
        }

        // TODO: read weights
    }

    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);

        if (qName.equals(NeuralNetXMLConstants.LAYER)) {
            Neuron[] neurons = new Neuron[_currentLayerNeurons.size()];
            neurons = _currentLayerNeurons.toArray(neurons);
            _layers.add(new Layer(neurons, _currentFunction));
            _currentLayerNeurons.clear();
            _currentFunction = null;

        } else if (qName.equals(NeuralNetXMLConstants.NEURON)) {
            int weightCount = _currentNeuronWeights.size();
            double[] weights = new double[weightCount];
            for (int i = 0; i < weightCount; i++) {
                weights[i] = _currentNeuronWeights.get(i);
            }
            _currentLayerNeurons.add(new Neuron(weights));
            _currentNeuronWeights.clear();
        }

    }

    @Override
    protected void textField (String tagName, String text)
    {
        if (NeuralNetXMLConstants.WEIGHT.equals(tagName)) {
            _currentNeuronWeights.add(Double.valueOf(text.trim()));
        }
    }

    public static void main (String[] args)
    {
        try {
            NeuralNet network = new NeuralNetXMLReader().readNetwork(args[0]);
            NeuralNetTextWriter.print(network);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
