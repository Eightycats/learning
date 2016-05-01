package com.eightycats.learning.neuralnet.io;

import java.io.PrintStream;
import com.eightycats.learning.neuralnet.NeuralNet;

/**
 *
 *
 */
public interface NeuralNetWriter
{

    public void write( NeuralNet network, PrintStream output ) throws Exception;

}
