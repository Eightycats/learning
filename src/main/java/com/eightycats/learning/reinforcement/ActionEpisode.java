package com.eightycats.learning.reinforcement;

/**
 *
 *
 *
 */
public abstract class ActionEpisode extends EpisodeBase
{

   protected ActionValueFunction _valueFunction;

   public ActionEpisode(State initialState,
                  Policy policy,
                  ActionValueFunction valueFunction,
                  RewardFunction rewardFunction )
   {
      
      super( initialState, policy, rewardFunction );

      this._valueFunction = valueFunction;

   }

   protected void setActionValueFunction(ActionValueFunction valueFunction)
   {
      this._valueFunction = valueFunction;
   }

   public ActionValueFunction getValueFunction()
   {
      return _valueFunction;
   }

}
