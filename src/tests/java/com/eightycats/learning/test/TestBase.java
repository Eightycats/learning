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

package com.eightycats.learning.neuralnet.test;

import com.eightycats.learning.neuralnet.*;
import com.eightycats.learning.neuralnet.train.*;
import com.eightycats.math.Processor;
import com.eightycats.math.functions.Tanh;

/**
 *
 *
 *
 */
public abstract class TestBase implements Processor
{



    public NeuralNet train(int inputCount,
                           int hiddenCount,
                           int outputCount,
                           double learningRate,
                           double learningRateDecay,
                           double momentum,
                           InputSource trainingInput,
                           int trainingRounds)
    {

       TrainingHarness trainer = new TrainingHarness( this );


       NeuralNet network =
            new NeuralNet( inputCount, hiddenCount, outputCount );

       network.setFunction( new Tanh() );
       network.setLearningRate( learningRate );
       network.setLearningRateDecay( learningRateDecay );
       network.setMomentum( momentum );

       trainer.train( network, trainingInput, trainingRounds );

       return network;

    }

}
