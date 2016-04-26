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
