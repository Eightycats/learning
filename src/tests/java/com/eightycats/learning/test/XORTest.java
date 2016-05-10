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

/**
 *
 */
package com.eightycats.learning.neuralnet.test;

import com.eightycats.learning.neuralnet.*;
import com.eightycats.math.*;

/**
 *
 */
public class XORTest extends TestBase implements InputSource
{

   private int inputCounter = 0;

   public double[] process( double[] input )
   {
        double[] result = new double[1];

        result[0] = xor( (int) input[0], (int) input[1] );

        return result;
   }

   public static double xor( int one, int two )
   {

      double result = one | two;

      if( one != 0 && two != 0 )
      {
         result = 0.0;
      }

      return result;

   }

   public void reset()
   {
   }

   public double[] nextInput()
   {
       double[] inputs = new double[3];

       int input1 = ( (inputCounter & 1) == 1 ) ? 1 : 0;
       int input2 = ( (inputCounter & 2) == 2 ) ? 1 : 0;

       inputCounter++;
       inputCounter %= 4;

       inputs[0] = (double) input1;
       inputs[1] = (double) input2;
       inputs[2] = (double) ( input1 & input2 );

       return inputs;
   }

   public boolean hasMoreInputs()
   {
       return true;
   }

   public void train()
   {

       try
       {

          NeuralNet network =
                super.train( 3, 3, 1, .2, .0001, 0.0, this, 500 );

          System.out.println("\n\nTraining complete.\n\n");

          double[] inputs = new double[3];

          for( int i = 0; i < 2; i++ )
          {

             for( int j = 0; j < 2; j++ )
             {

                inputs[0] = (double) i;
                inputs[1] = (double) j;
                inputs[2] = (double) ( i & j );

                System.out.println("Inputs : "+ ArrayUtils.toString( inputs ) );

                double[] output = network.process(inputs);

                System.out.println("Output : " + output[0]);
                System.out.println();

             }

          }

       }
       catch( Throwable ex )
       {
          ex.printStackTrace();
       }


   }

   public static void main( String[] args )
   {
      XORTest test = new XORTest();
      test.train();
   }

}
