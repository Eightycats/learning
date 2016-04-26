package com.eightycats.learning.neuralnet;

import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.litterbox.math.functions.Function;
import com.eightycats.litterbox.math.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import com.eightycats.learning.neuralnet.io.NeuralNetTextWriter;

/**
 * A simple backpropagation neural network.
 *
 *
 */
public class NeuralNet implements Processor
{

    private InputLayer inputLayer;

    private Layer[] layers;

    private double learningRate = .1;

    private double learningRateDecay = 0.0;

    private double momentum = 0.0;

   /**
    * Creates a network with one hidden layer.
    *
    * @param inputCount int
    * @param hiddenNeurons int
    * @param outputCount int
    */
   public NeuralNet( int inputCount,
                     int hiddenNeurons,
                     int outputCount )
   {
      inputLayer  = new InputLayer( inputCount );
      layers = new Layer[2];
      layers[0] = new Layer( inputLayer.getOutputCount(), hiddenNeurons );
      layers[1] = new Layer( hiddenNeurons, outputCount );
   }


   /**
    * Creates a network with two hidden layers.
    *
    * @param inputCount int
    * @param hiddenLayer1Count int
    * @param hiddenLayer2Count int
    * @param outputCount int
    */
   public NeuralNet( int inputCount,
                     int hiddenLayer1Count,
                     int hiddenLayer2Count,
                     int outputCount )
   {
       inputLayer  = new InputLayer( inputCount );
       layers = new Layer[3];
       layers[0] = new Layer( inputLayer.getOutputCount(), hiddenLayer1Count );
       layers[1] = new Layer( hiddenLayer1Count, hiddenLayer2Count );
       layers[2] = new Layer( hiddenLayer2Count, outputCount );
   }
    
   
   public NeuralNet( InputLayer input, 
                     Layer[] layers )
   {
       this.inputLayer = input;
       this.layers = layers; 
   }
   
   public void setFunction( Function threshold )
   {
        for( int i = 0; i < layers.length; i++ )
        {
            layers[i].setFunction( threshold );
        }
   }

   /**
    * This runs the given inputs through the network
    * and returns the results.
    *
    * @param inputs double[]
    * @return double[]
    */
   public double[] process(double[] inputs)
   {

       inputLayer.setInputs( inputs );

       // the input layer adds a bias value,
       // which is why we don't just pass the
       // inputs directly
       double[] output = inputLayer.getOutputs();

       for( int i = 0; i < layers.length; i++ )
       {
           output = layers[i].process( output );
       }

       return output;

   }

   /**
    * This takes in the target outputs for the current
    * input pattern, calculates the output error, and
    * backs up that error to train the network.
    *
    * @param expectedOutputs double[] the target training pattern
    *                                 expected for this network's
    *                                 current input.
    *
    * @return double[] the differences between the current
    *                  network output and the expected output.
    */
   public double[] train( double[] expectedOutputs )
   {

        // the error values are the expected values
        // minus the current output values
        double[] errors = ArrayUtils.copy( expectedOutputs );
        ArrayUtils.subtract( errors, getOutputs() );

        // backpropagate the errors through this network's
        // layers and update the connection weights
        backup( errors );

        return errors;

   }

   /**
    * 
    * @param errors the differences between the current
    *               network output and the expected output.
    */
   public void backup( double[] errors )
   {

       for( int i = layers.length-1; i >= 0; i-- )
       {
           errors = layers[i].backup( errors, learningRate, momentum );
       }

       // decay learning rate
       learningRate -= (learningRate * learningRateDecay);

   }

   /**
    * This gets this network's output values from the last time
    * that the process() method was called.
    *
    * @return double[]
    */
   public double[] getOutputs()
   {
      // get the values from the output (last) layer
      return layers[ layers.length - 1 ].getOutputs();
   }

   /**
    * @return the learningRate.
    */
   public double getLearningRate()
   {
      return learningRate;
   }

   /**
    * @param learningRate The learningRate to set.
    */
   public void setLearningRate( double learningRate )
   {
      this.learningRate = learningRate;
   }

   /**
    * @return returns the learning rate decay.
    */
   public double getLearningRateDecay()
   {
      return learningRateDecay;
   }

   public double getMomentum()
   {
        return momentum;
   }

   public void setLearningRateDecay( double learningRateDecay )
   {
      this.learningRateDecay = learningRateDecay;
   }

   public void setMomentum(double momentum)
   {
       this.momentum = momentum;
   }

   /**
    * This randomizes the input weights for the neurons
    * in this network. 
    */
   public void randomize()
   {

       for( int i = 0; i < layers.length; i++ )
       {
           layers[i].randomize();
       }

   }

   /**
    * This returns the number of hidden layers plus one for the output
    * layer. This does not count the input layer. This method is
    * useful when writing out a representation of this network.
    *
    * @return int
    */
   public int getLayerCount()
   {
       return layers.length;
   }

   public Layer getLayer( int layerIndex )
   {
       return layers[ layerIndex ];
   }

   public InputLayer getInputLayer()
   {
       return inputLayer;
   }

   public String toString()
   {
       ByteArrayOutputStream buffer = new ByteArrayOutputStream();
       PrintStream output = new PrintStream( buffer );

       NeuralNetTextWriter.writeNetwork( this, output );

       return new String( buffer.toByteArray() );
   }

}
