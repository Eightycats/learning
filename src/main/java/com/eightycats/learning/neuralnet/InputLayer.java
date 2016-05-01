package com.eightycats.learning.neuralnet;

import com.eightycats.litterbox.math.*;

/**
 * This is a stateful input layer. It maintains a copy of the latest
 * input values that it was passed. This input layer
 * is biased, meaning that the value of the output at index 0 will always
 * be 1 by default.
 *
 *
 */
public class InputLayer
{

   /**
    * A bias input value of 1 will always be returned as the first input.
    */
   private double bias = 1.0;

   /**
    * The lates input values passed to this input layer.
    */
   private double[] inputs;

   public InputLayer(int inputCount)
   {
      inputs = new double[inputCount];
   }

   public InputLayer( double[] inputValues )
   {
      this( inputValues.length );
      setInputs( inputValues );
   }

   public void setInputs( double[] inputValues )
   {
      ArrayUtils.copyInto( inputValues, inputs );
   }

   public void setInput(int index, double value)
   {
      inputs[index] = value;
   }

   public int getInputCount()
   {
      return inputs.length;
   }

   /**
    * @see neuralnet.SourceVector#getSource(int)
    */
   public double getOutput( int index )
   {
      // the first output value will always
      // be the bias value
      if( index == 0 )
      {
         return bias;
      }

      return inputs[index-1];
   }

   /**
    *
    */
   public int getOutputCount()
   {
      return getInputCount() + 1;
   }

   /**
    *
    */
   public double[] getOutputs()
   {
      double[] outputs = new double[ getOutputCount() ];
      outputs[0] = bias;

      System.arraycopy( inputs, 0, outputs, 1, inputs.length );

      return outputs;
   }

   /**
    * In case you really want to change the bias value. The
    * bias value is 1 by default.
    *
    * @param biasValue double
    */
   public void setBias(double biasValue)
   {
      bias = biasValue;
   }

   public double getBias()
   {
      return bias;
   }

}

