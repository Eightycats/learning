package com.eightycats.learning.reinforcement;

/**
 *
 *
 *
 */
public abstract class ActionEpisode extends EpisodeBase
{

   protected ActionValueFunction valueFunction;

   public ActionEpisode(State initialState,
                  Policy policy,
                  ActionValueFunction valueFunction,
                  RewardFunction rewardFunction )
   {
      
      super( initialState, policy, rewardFunction );

      this.valueFunction = valueFunction;

   }

   protected void setActionValueFunction(ActionValueFunction valueFunction)
   {
      this.valueFunction = valueFunction;
   }

   public ActionValueFunction getValueFunction()
   {
      return valueFunction;
   }

}
