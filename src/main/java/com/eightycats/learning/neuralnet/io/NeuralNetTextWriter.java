package com.eightycats.learning.neuralnet.io;

import java.io.PrintStream;

import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.Neuron;
import com.eightycats.learning.neuralnet.NeuralNet;

/**
 *
 *
 *
 */
public class NeuralNetTextWriter implements NeuralNetWriter
{

    private static NeuralNetTextWriter writer = new NeuralNetTextWriter();

    public static void print( NeuralNet network )
    {
       writeNetwork( network, System.out );
    }

    public static void writeNetwork( NeuralNet network, PrintStream output )
    {
        writer.write( network, output );
    }

    public void write( NeuralNet network, PrintStream output )
    {
        output.println( "Network:" );

        output.println( "Learning rate       : " +
                        network.getLearningRate() );
        output.println( "Learning rate decay : " +
                        network.getLearningRateDecay() );
        output.println( "Momentum            : " +
                        network.getMomentum() );

        InputLayer inputs = network.getInputLayer();
        output.println( "Input count         : " +
                        inputs.getInputCount() );
        output.println( "Input bias          : " +
                        inputs.getBias() );

        int layerCount = network.getLayerCount();

        for( int i = 0; i < layerCount; i++ )
        {
            output.println( "Layer ["+i+"]:" );
            Layer layer = network.getLayer(i);

            int neuronCount = layer.getNeuronCount();
            for( int j = 0; j < neuronCount; j++ )
            {

                output.println( "Neuron ["+j+"]:" );
                Neuron neuron = layer.getNeuron(j);
                int weightCount = neuron.getWeightCount();
                for( int k = 0; k < weightCount; k++ )
                {
                    output.println( "Weight ["+k+"]: " + neuron.getWeight(k) );
                }

                output.println();

            }

        }

        output.flush();

    }

}
