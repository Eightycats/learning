package com.eightycats.learning.neuralnet;

import com.eightycats.litterbox.math.functions.Derivable;
import com.eightycats.litterbox.math.functions.Function;
import com.eightycats.litterbox.math.functions.Sigmoid;
import com.eightycats.litterbox.math.*;

/**
 *
 *
 *
 */
public class Layer implements Processor
{
   private double[] inputs;

   private double[] outputs;

   private Neuron[] neurons;

   private Function function;

   public Layer( int inputCount, int neuronCount )
   {
      this( inputCount, neuronCount, new Sigmoid() );
   }

   public Layer( int inputCount, int neuronCount, Function thresholdFunction )
   {

      function = thresholdFunction;

      // create arrays to store the most recent inputs and
      // outputs for this network
      inputs = new double[inputCount];
      outputs = new double[neuronCount];

      neurons = new Neuron[neuronCount];
      for( int i = 0; i < neuronCount; i++ )
      {
         neurons[i] = new Neuron(inputCount);
      }

   }

   public Layer( Neuron[] neurons )
   {
      this( neurons, new Sigmoid() );
   }

   public Layer( Neuron[] neurons, Function thresholdFunction )
   {
      this.function = thresholdFunction;

      this.neurons = neurons;

      int inputCount = neurons[0].getWeightCount();

      // create arrays to store the most recent inputs and
      // outputs for this network
      inputs = new double[inputCount];
      outputs = new double[ neurons.length ];

   }

   @Override
public double[] process( double[] inputValues )
   {
      // save copy of input values
      ArrayUtils.copyInto( inputValues, inputs );

      for( int i = 0; i < neurons.length; i++ )
      {

         // Get the weighted sum of the input values and
         // run this stimulation through the squashing function.
         // Set the result as the output values for the neuron
         // at this index.
         outputs[i] = neurons[i].process( inputs, function );

      }

      // return a copy of the outputs so that
      // no one can mess with our internal output values
      return ArrayUtils.copy( outputs );

   }

   public void setFunction( Function newFunction )
   {
      function = newFunction;
   }

   public Function getFunction()
   {
      return function;
   }

   public int getNeuronCount()
   {
      return neurons.length;
   }

   public Neuron getNeuron( int neuronIndex )
   {
      return neurons[ neuronIndex ];
   }

   public double getWeight( int neuronIndex, int inputIndex )
   {
      return neurons[neuronIndex].getWeight(inputIndex);
   }

   public double[] backup( double[] errors,
                           double learningRate )
   {
       return backup( errors, learningRate, 0 );
   }

    public double[] backup( double[] errors,
                            double learningRate,
                            double momentum )
   {

       int neuronCount = getNeuronCount();

       double[] deltas = new double[ neuronCount ];

       // calculate the errors for the upstream layer
       double[] upstreamErrors = new double[ inputs.length ];

       for( int neuronIndex = 0; neuronIndex < neuronCount; neuronIndex++ )
       {

          // get the slope of the activation function
          // at the point of the current output value
          double outputSlope = 1;

          if( function instanceof Derivable )
          {
              Derivable derivableFunction = (Derivable) function;
              Function derivative = derivableFunction.getDerivative();
              outputSlope = derivative.apply( outputs[neuronIndex] );
          }

          // multiply the error value times the slope of the current output
          deltas[neuronIndex] = errors[neuronIndex] * outputSlope;

          // for each input value
          for( int inputIndex = 0; inputIndex < inputs.length; inputIndex++ )
          {

             // - get the weight for the current input to
             // the current neuron
             //
             // - multiply the weight times the output
             //   error (aka delta) for the current neuron
             //
             double weightedError = getWeight(neuronIndex, inputIndex) *
                                    deltas[neuronIndex];

             // accumulate all of the weighted error values
             // for the current input
             upstreamErrors[inputIndex] += weightedError;

             // Adjust weights for the current layer
             double weightChange = learningRate *
                                   deltas[neuronIndex] *
                                   inputs[ inputIndex ];

             // Add momentum (if any) to the weight adjustment.
             // This is the last weight change times the momentum factor.
             double momentumWeightChange = momentum *
                neurons[ neuronIndex ].getMomentum( inputIndex );

             weightChange += momentumWeightChange;

             neurons[ neuronIndex ].adjustWeight( inputIndex,
                                                  weightChange );

          }

       }

       return upstreamErrors;

   }

   public double[] getOutputs()
   {
       return outputs;
   }

   public void randomize()
   {
       for(int i = 0; i < neurons.length; i++)
       {
          neurons[i].randomize();
       }
   }

}
