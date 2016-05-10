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

package com.eightycats.learning.neuralnet;

import com.eightycats.math.functions.Derivable;
import com.eightycats.math.functions.Function;
import com.eightycats.math.functions.Processor;
import com.eightycats.math.functions.Sigmoid;
import com.eightycats.math.util.ArrayUtils;

/**
 * A layer of neurons in a NeuralNet.
 */
public class Layer
    implements Processor
{
    protected double[] _inputs;

    protected double[] _outputs;

    protected Neuron[] _neurons;

    protected Function _function;

    public Layer (int inputCount, int neuronCount)
    {
        this(inputCount, neuronCount, new Sigmoid());
    }

    public Layer (int inputCount, int neuronCount, Function thresholdFunction)
    {
        _function = thresholdFunction;

        // create arrays to store the most recent inputs and
        // outputs for this network
        _inputs = new double[inputCount];
        _outputs = new double[neuronCount];

        _neurons = new Neuron[neuronCount];
        for (int i = 0; i < neuronCount; i++) {
            _neurons[i] = new Neuron(inputCount);
        }
    }

    public Layer (Neuron[] neurons)
    {
        this(neurons, new Sigmoid());
    }

    public Layer (Neuron[] neurons, Function thresholdFunction)
    {
        _neurons = neurons;
        _function = thresholdFunction;

        int inputCount = neurons[0].getWeightCount();

        // create arrays to store the most recent inputs and
        // outputs for this network
        _inputs = new double[inputCount];
        _outputs = new double[neurons.length];
    }

    @Override
    public double[] process (double[] inputValues)
    {
        // save copy of input values
        ArrayUtils.copyInto(inputValues, _inputs);

        for (int i = 0; i < _neurons.length; i++) {

            // Get the weighted sum of the input values and
            // run this stimulation through the squashing function.
            // Set the result as the output values for the neuron
            // at this index.
            _outputs[i] = _neurons[i].process(_inputs, _function);

        }

        // return a copy of the outputs so that
        // no one can mess with our internal output values
        return ArrayUtils.copy(_outputs);
    }

    public void setFunction (Function thresholdFunction)
    {
        _function = thresholdFunction;
    }

    public Function getFunction ()
    {
        return _function;
    }

    public int getNeuronCount ()
    {
        return _neurons.length;
    }

    public Neuron getNeuron (int neuronIndex)
    {
        return _neurons[neuronIndex];
    }

    public double getWeight (int neuronIndex, int inputIndex)
    {
        return _neurons[neuronIndex].getWeight(inputIndex);
    }

    public double[] backup (double[] errors, double learningRate)
    {
        return backup(errors, learningRate, 0);
    }

    public double[] backup (double[] errors, double learningRate, double momentum)
    {
        int neuronCount = getNeuronCount();

        double[] deltas = new double[neuronCount];

        // calculate the errors for the upstream layer
        double[] upstreamErrors = new double[_inputs.length];

        for (int neuronIndex = 0; neuronIndex < neuronCount; neuronIndex++) {

            // get the slope of the activation function
            // at the point of the current output value
            double outputSlope = 1;

            if (_function instanceof Derivable) {
                Derivable derivableFunction = (Derivable) _function;
                Function derivative = derivableFunction.getDerivative();
                outputSlope = derivative.apply(_outputs[neuronIndex]);
            }

            // multiply the error value times the slope of the current output
            deltas[neuronIndex] = errors[neuronIndex] * outputSlope;

            // for each input value
            for (int inputIndex = 0; inputIndex < _inputs.length; inputIndex++) {

                // - get the weight for the current input to the current neuron
                //
                // - multiply the weight times the output error (aka delta) for the current neuron
                double weightedError = getWeight(neuronIndex, inputIndex) * deltas[neuronIndex];

                // accumulate all of the weighted error values for the current input
                upstreamErrors[inputIndex] += weightedError;

                // Adjust weights for the current layer
                double weightChange = learningRate * deltas[neuronIndex] * _inputs[inputIndex];

                // Add momentum (if any) to the weight adjustment.
                // This is the last weight change times the momentum factor.
                double momentumWeightChange = momentum
                    * _neurons[neuronIndex].getMomentum(inputIndex);

                weightChange += momentumWeightChange;

                _neurons[neuronIndex].adjustWeight(inputIndex, weightChange);
            }
        }
        return upstreamErrors;
    }

    public double[] getOutputs ()
    {
        return _outputs;
    }

    public void randomize ()
    {
        for (int i = 0; i < _neurons.length; i++) {
            _neurons[i].randomize();
        }
    }
}
