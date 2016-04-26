package com.eightycats.learning.neuralnet.test;

import java.util.Random;

import com.eightycats.litterbox.math.IncrementalAverage;
import com.eightycats.litterbox.math.normalization.Normalizer;
import com.eightycats.learning.neuralnet.InputSource;
import com.eightycats.learning.neuralnet.NeuralNet;
import com.eightycats.learning.neuralnet.train.TrainingHarness;

/**
 *
 *
 *
 */
public class SineTest extends TestBase implements InputSource, Normalizer
{

    private static Random random = new Random();

    public double[] process(double[] input)
    {
        double[] result = new double[1];
        result[0] = function( input[0] );
        return result;
    }

    public double function(double x)
    {
       return Math.sin(x) / 2;
    }

    public double[] normalize( double[] input )
    {
       return new double[]{ normalize( input[0] ) };
    }
    
    public double normalize( double input )
    {
       return input / (2 * Math.PI);
    }

    public void reset()
    {
    }

    public double[] nextInput()
    {
        double[] inputs = new double[1];
        inputs[0] = random.nextDouble() * 2 * Math.PI;
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
            NeuralNet network = train( 1, 21, 1, .2, 0, 0.0, this, 10000 );

            TrainingHarness trainer = new TrainingHarness( this );
            network.setLearningRateDecay(.0001);
            trainer.train( network, this, 20000 );

            System.out.println("\n\nTraining complete.\n\n");

            double sampleCount = 100;
            double twoPI = 2 * Math.PI;
            double increment = twoPI / sampleCount;

            IncrementalAverage average = new IncrementalAverage();

            double[] inputs = new double[1];

            for(double i = 0; i <= twoPI; i += increment)
            {

                inputs[0] = i;
                // inputs[0] = this.normalize(i);

                System.out.print( Math.toDegrees(i) );
                System.out.print( "," );

                double[] output = network.process(inputs);

                System.out.print(output[0]);
                System.out.print( "," );

                double[] expected = this.process(inputs);
                System.out.print( expected[0] );
                System.out.print( "," );

                double error = output[0] - expected[0];
                System.out.print( error  );
                System.out.print( "," );

                average.add( error );

                System.out.println(average.getAverage());

            }

        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args)
    {

        SineTest test = new SineTest();
        test.train();

    }

}
