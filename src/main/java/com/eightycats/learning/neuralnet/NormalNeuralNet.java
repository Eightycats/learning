package com.eightycats.learning.neuralnet;

import com.eightycats.learning.neuralnet.Layer;
import com.eightycats.learning.neuralnet.InputLayer;
import com.eightycats.litterbox.math.functions.Function;
import com.eightycats.litterbox.math.*;
import com.eightycats.litterbox.math.normalization.PassthroughNormalization;
import com.eightycats.litterbox.math.normalization.Normalizer;

/**
 * A simple backpropagation neural network.
 *
 *
 * @version 1.0
 */
public class NormalNeuralNet implements Processor
{

    private InputLayer inputLayer;

    private Layer[] hiddenLayers;

    private Layer outputLayer;

    private double learningRate = .1;

    private double learningRateDecay = 0.0;

    private double momentum = 0.0;

    private Normalizer normalization = new PassthroughNormalization();

   /**
    * Creates a network with one hidden layer.
    *
    * @param inputCount int
    * @param hiddenNeurons int
    * @param outputCount int
    */
   public NormalNeuralNet( int inputCount,
                           int hiddenNeurons,
                           int outputCount )
    {
       inputLayer  = new InputLayer( inputCount );
       hiddenLayers = new Layer[1];
       hiddenLayers[0] = new Layer( inputLayer.getOutputCount(), hiddenNeurons );
       outputLayer = new Layer( hiddenNeurons, outputCount );
    }


    /**
     * Creates a network with two hidden layers.
     *
     * @param inputCount int
     * @param hiddenLayer1Count int
     * @param hiddenLayer2Count int
     * @param outputCount int
     */
    public NormalNeuralNet( int inputCount,
                            int hiddenLayer1Count,
                            int hiddenLayer2Count,
                            int outputCount )
    {
        inputLayer  = new InputLayer( inputCount );
        hiddenLayers = new Layer[2];
        hiddenLayers[0] = new Layer( inputLayer.getOutputCount(), hiddenLayer1Count );
        hiddenLayers[1] = new Layer( hiddenLayer1Count, hiddenLayer2Count );
        outputLayer = new Layer( hiddenLayer2Count, outputCount );
    }

   public void setFunction( Function threshold )
   {
        for( int i = 0; i < hiddenLayers.length; i++ )
        {
            hiddenLayers[i].setFunction( threshold );
        }

        outputLayer.setFunction( threshold );
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

       inputs = normalization.normalize( inputs );

       inputLayer.setInputs( inputs );

       double[] output = inputLayer.getOutputs();
       double[] hiddenInput = output;

       for( int i = 0; i < hiddenLayers.length; i++ )
       {

           output = hiddenLayers[i].process(hiddenInput);

           // if there are more layers, the output
           // of this layer becomes the input for the next
           hiddenInput = output;

       }

       return outputLayer.process( output );

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
        ArrayUtils.subtract( errors, outputLayer.getOutputs() );

        // backpropagate the errors through this network's
        // layers and update the connection weights
        backup( errors );

        return errors;

   }

   public void backup( double[] errors )
   {

       errors = outputLayer.backup( errors, learningRate, momentum );

       for( int i = hiddenLayers.length-1; i >= 0; i-- )
       {
           errors = hiddenLayers[i].backup( errors, learningRate, momentum );
       }

       // decay learning rate
       learningRate -= (learningRate * learningRateDecay);

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


   public void randomize()
   {

       for( int i = 0; i < hiddenLayers.length; i++ )
       {
           hiddenLayers[i].randomize();
       }

       outputLayer.randomize();

   }

   public void setNormalization( Normalizer normalizer )
   {
       normalization = normalizer;
   }

}
