package com.eightycats.learning.neuralnet.io;

import java.io.Reader;
import com.eightycats.learning.neuralnet.NeuralNet;

/**
 *
 *
 */
public interface NeuralNetReader
{

    public NeuralNet read( Reader input ) throws Exception;

}
