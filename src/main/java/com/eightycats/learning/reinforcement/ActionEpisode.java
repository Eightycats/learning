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
