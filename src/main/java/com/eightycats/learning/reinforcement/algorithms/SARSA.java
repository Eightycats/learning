package com.eightycats.learning.reinforcement.algorithms;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionEpisode;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.Policy;
import com.eightycats.learning.reinforcement.RewardFunction;
import com.eightycats.learning.reinforcement.State;

/**
 * 
 * SARSA stands for State-Action-Reward-State-Action.
 * 
 *
 *
 */
public class SARSA extends ActionEpisode
{
   
   /**
    * @param initialState
    * @param policy
    * @param valueFunction
    * @param rewardFunction
    */
   public SARSA( State initialState, 
                 Policy policy, 
                 ActionValueFunction valueFunction, 
                 RewardFunction rewardFunction )
   {
      super( initialState, policy, valueFunction, rewardFunction );
   }
   
   /**
    * @see mjensen.learning.reinforcement.EpisodeBase#next()
    */
   public State next()
   {
      
      State state = getCurrentState();
      Action action = _policy.selectAction( state );
         
      double currentValue = valueFunction.getValue( state, action );         

      State nextState = action.perform( state ); 
      
      // get the reward for performing the action
      double reward = _rewardFunction.getReward( nextState );      
      
      Action nextAction = _policy.selectAction( nextState );
      double nextValue = valueFunction.getValue( nextState, nextAction );  

      double valueUpdate = reward + 
                           getDiscount() * nextValue -
                           currentValue;
      
      // mutliply the update value times the learning factor
      valueUpdate *= getLearningRate();  
      
      valueFunction.update( state, action, valueUpdate );
         
      return nextState;
         
   }

}
