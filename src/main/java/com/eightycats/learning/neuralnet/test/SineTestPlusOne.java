package com.eightycats.learning.neuralnet.test;


/**
 *
 *
 */
public class SineTestPlusOne extends SineTest
{
    public double function(double x)
    {
       return (Math.sin(x)+1) / 2;
    }

    public static void main(String[] args)
    {
        SineTest test = new SineTestPlusOne();
        test.train();
    }

}
