package com.eightycats.learning.neuralnet;

import com.eightycats.litterbox.math.ArrayUtils;
import com.eightycats.litterbox.math.RandomUtils;
import com.eightycats.litterbox.math.functions.Function;

/**
 *
 *
 */
public class Neuron
{

    /**
     * The input weights. inputs[n] is multiplied by weights[n] to get the weighted input value.
     */
    private double[] weights;

    /**
     * The most recent weight adjustments. These values are used in calulating momentum when
     * training a network.
     */
    private double[] momentum;

    public Neuron (int weightCount)
    {
        weights = new double[weightCount];
        momentum = new double[weightCount];
        randomize();
    }

    public Neuron (double[] weights)
    {
        this.weights = ArrayUtils.copy(weights);
        momentum = new double[weights.length];
    }

    public int getWeightCount ()
    {
        return weights.length;
    }

    public void setWeight (int weightIndex, double weight)
    {
        weights[weightIndex] = weight;
    }

    public double getWeight (int weightIndex)
    {
        return weights[weightIndex];
    }

    public double getMomentum (int weightIndex)
    {
        return momentum[weightIndex];
    }

    /**
     * Randomizes the weight values.
     */
    public void randomize ()
    {
        randomize(-0.4, 0.4);
    }

    /**
     * Randomizes the weight values within the given range. By default, weights are within the range
     * of -0.4 and 0.4.
     *
     * @param minWeight
     *            double a minimum weight value between -1.0 and 1.0.
     * @param maxWeight
     *            double a maximum weight value between -1.0 and 1.0.
     */
    public void randomize (double minWeight, double maxWeight)
    {
        for (int i = 0; i < getWeightCount(); i++) {
            // create random relatively small weight values
            double random = RandomUtils.randomInRange(minWeight, maxWeight);

            setWeight(i, random);

        }

        // clear out the momentum array
        ArrayUtils.zero(momentum);
    }

    /**
     * This adds the given weight delta to the weight at the given index.
     */
    public void adjustWeight (int weightIndex, double weightChange)
    {
        setWeight(weightIndex, weights[weightIndex] + weightChange);

        if (Double.isNaN(getWeight(weightIndex))) {
            try {
                throw new RuntimeException("Weight set to NaN");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // TODO: is this the correct way to update the momentum?
        momentum[weightIndex] = weightChange;
    }

    /**
     * This multiplies each of the input values by its respective weight and sums the results. This
     * total is the weighted stimulation for the given input pattern.
     *
     * @param inputs
     *            double[] an array of input values. The number of inputs should match the number of
     *            weights.
     * @return double the sum of the weighted input values.
     */
    public double getStimulation (double[] inputs)
    {

        double stimulation = 0.0;

        for (int i = 0; i < inputs.length; i++) {
            stimulation += weights[i] * inputs[i];
        }

        return stimulation;

    }

    public double process (double[] inputs, Function function)
    {

        // get the weighted sum of the input values
        double stimulation = getStimulation(inputs);

        // run this weighted sum through the squashing function
        // and set it as the output values for the neuron
        // at this index
        return function.apply(stimulation);

    }

    @Override
    public String toString ()
    {
        return ArrayUtils.toString(weights);
    }

}
