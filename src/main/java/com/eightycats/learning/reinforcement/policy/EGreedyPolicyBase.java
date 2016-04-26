package com.eightycats.learning.reinforcement.policy;

import java.util.*;
import com.eightycats.learning.reinforcement.*;

/**
 * This class implements the epsilon-greedy (e-greedy) policy.
 * This policy selects the greedy (most valuable action)
 * most of the time, but a small percentage of the time (epsilon),
 * it selects an action at random. This is used to explore
 * the values of other actions.
 */
public abstract class EGreedyPolicyBase implements Policy
{

   private static Random random = new Random();

   /**
    * epsilon - This is the probability that an exploratory 
    * (non-greedy) action will be selected. 
    */
   private double e;

   /**
    * This is the amount by which the epsilon value 
    * will decay after each selectAction() call.
    */
   private double decay = 0.0;

   protected RandomPolicy randomPolicy;

   public EGreedyPolicyBase(double epsilon)
   {
       setEpsilon( epsilon );
       randomPolicy = new RandomPolicy();
   }

   public Action selectAction(State state)
   {
      double selection = random.nextDouble();

      if( e > 0.0 )
      {
         // Decay the epsilon value.
         // If the decay is 0, this obviously has no effect.
         e -= decay;
      }          
      
      Action action = null;

      if( selection >= e )
      {
         action = selectGreedyAction( state );
      }

      // if the e value indicates that we should choose a random action,
      // or if there was no greedy action
      if( action == null )
      {
         action = randomPolicy.selectAction( state );
      }
      
      return action;

    }

    public Action selectGreedyAction( State currentState )
    {

        Action bestAction = null;

        // the best value so far according to the value function
        double max = Double.MIN_VALUE;

        Action[] actions = currentState.getActions();

        for( int i = 0; i < actions.length; i++ )
        {

            double actionValue = getValue( currentState, actions[i] );

            if( actionValue > max )
            {
               max = actionValue;
               bestAction = actions[i];
            }
            else if( actionValue == max )
            {
               // randomly select from between two
               // actions of equal value
               Action[] equalActions = new Action[]{ bestAction, actions[i] };
               bestAction = RandomPolicy.selectAction( equalActions );
            }

        }

       return bestAction;

    }

    protected abstract double getValue( State currentState, Action action );
    
    /**
     * This sets the amount that epsilon will decay 
     * after each call to selectAction(). This should 
     * be a very small number. As epsilon gets smaller,
     * this policy will select greedy actions more 
     * often and explore less.
     * 
     * @param decay
     */
    public void setDecay(double decay)
    {
        this.decay = decay;
    }

    public double getDecay()
    {
        return decay;
    }

   /**
    * @return the probability of a selection a 
    *         non-greedy action. 
    */
   public double getEpsilon()
   {
      return e;
   }

   /**
    * This sets the probability that an exploratory 
    * (non-greedy) action will be selected. 
    * The epsilon value must be between 0.0 and 1.0.
    * 
    * @param epsilon the probability of a selection a 
    *         non-greedy action. 
    */
   public void setEpsilon( double epsilon )
   {
      if( epsilon < 0.0 || epsilon > 1.0 )
      {
         throw new IllegalArgumentException( "The epsilon value ["+epsilon+
                                             "] must be between 0.0 and 1.0." );
      }
               
      this.e = epsilon;
   }

}
