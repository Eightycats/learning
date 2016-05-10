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
 * An Action that can be performed in a given State.
 */
public interface Action
{
    /**
     * Performs this action from the given State and returns the new State.
     */
    public State perform (State currentState);

    /**
     * Peeks at the next State if this action is performed.
     */
    public State getNextState (State currentState);

    @Override
    public boolean equals (Object object);

    @Override
    public String toString ();
}
