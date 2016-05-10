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

package com.eightycats.learning.reinforcement.policy;

import com.eightycats.learning.reinforcement.*;


public class SoftmaxPolicy
{
   private ValueFunction valueFunction;

   private double temperature;

   public SoftmaxPolicy( ValueFunction valueFunction )
   {
      this.valueFunction = valueFunction;
   }


   public SoftmaxPolicy( ValueFunction valueFunction, double temperature )
   {
      this.valueFunction = valueFunction;
      this.temperature = temperature;
   }

   public Action selectAction( State state )
   {
       Action[] actions = state.getActions();

       double total = 0;

       for( int i = 0; i < actions.length; i++ )
       {
           total +=
              // TODO: FIXME
              Math.exp( valueFunction.getValue( state ) / temperature );
       }


       // TODO: fix
       return null;

   }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public double getTemperature()
    {
        return temperature;
    }

}
