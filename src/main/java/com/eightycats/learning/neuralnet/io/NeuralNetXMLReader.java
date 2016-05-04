package com.eightycats.learning.neuralnet.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.eightycats.litterbox.logging.Logger;
import com.eightycats.litterbox.math.functions.Function;
import com.eightycats.litterbox.xml.sax.XMLHandlerBase;
import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.learning.neuralnet.Neuron;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This is a utility class for reading a NeuralNet instance from XML.
 */
public class NeuralNetXMLReader extends XMLHandlerBase implements NeuralNetReader
{

    private InputLayer inputLayer;

    private List<Layer> layers = new ArrayList<Layer>();

    private Function currentFunction = null;

    private List<Neuron> currentLayerNeurons = new ArrayList<Neuron>();

    private List<Neuron> currentNeuronWeights = new ArrayList<Neuron>();

    public void reset ()
    {
        inputLayer = null;
        currentLayerNeurons.clear();
        currentNeuronWeights.clear();
        currentFunction = null;
        layers.clear();
    }

    public NeuralNet readNetwork (String xmlFilePath) throws Exception
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

    public static NeuralNet readNetwork (Reader input) throws Exception
    {
        NeuralNetXMLReader parser = new NeuralNetXMLReader();
        return parser.read(input);
    }

    @Override
    public NeuralNet read (Reader xmlInput) throws Exception
    {
        // make sure to clear out any previously parsed info
        reset();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        InputSource xmlSource = new InputSource(xmlInput);

        parser.parse(xmlSource, this);

        // create new network and return it
        Layer[] layerArray = new Layer[layers.size()];
        layerArray = layers.toArray(layerArray);

        return new NeuralNet(inputLayer, layerArray);

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

            inputLayer = new InputLayer(count);
            inputLayer.setBias(bias);

        } else if (qName.equals(NeuralNetXMLConstants.LAYER)) {

            // TODO: create function

        }

        // TODO: read weights

    }

    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);

        if (qName.equals(NeuralNetXMLConstants.LAYER)) {
            Neuron[] neurons = new Neuron[currentLayerNeurons.size()];
            neurons = currentLayerNeurons.toArray(neurons);

            Layer layer = new Layer(neurons, currentFunction);
            layers.add(layer);

            currentLayerNeurons.clear();
            currentFunction = null;
        }

    }

    @Override
    protected void textField (String tagName, String text)
    {
        // java.util.Arrays.

    }

}
