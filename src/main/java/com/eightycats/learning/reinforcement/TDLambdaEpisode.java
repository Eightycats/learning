package com.eightycats.learning.reinforcement;

import com.eightycats.learning.reinforcement.util.EligibilityConfig;
import com.eightycats.learning.reinforcement.util.EligibilityTrace;
import com.eightycats.litterbox.logging.Logger;

/**
 *
 */
public class TDLambdaEpisode extends Episode
{
    /**
     * The collection of previously visited states that are eligible for update.
     */
    protected EligibilityTrace<State> _trace;

    public TDLambdaEpisode (State initialState, Policy policy, ValueFunction valueFunction,
        RewardFunction rewardFunction)
    {
        this(initialState, policy, valueFunction, rewardFunction, new EligibilityConfig());
    }

    public TDLambdaEpisode (State initialState, Policy policy, ValueFunction valueFunction,
        RewardFunction rewardFunction, EligibilityConfig config)
    {
        super(initialState, policy, valueFunction, rewardFunction, config);
        _trace = new EligibilityTrace<State>(config);
    }

    /**
     * Clear out the eligibility trace.
     */
    public void reset ()
    {
        _trace.clear();
    }

    @Override
    public State next ()
    {
        // use policy to select the next action to perform
        Action action = _policy.selectAction(_currentState);

        // perform the selected action and get the next state
        State nextState = action.perform(_currentState);

        // get the current reward signal
        double reward = _rewardFunction.getReward(nextState);

        Logger.debug("\nReward: " + reward);

        Logger.debug("Next State: " + _valueFunction.getValue(nextState));
        Logger.debug("This State: " + _valueFunction.getValue(_currentState));

        double valueDelta = _valueFunction.getValue(nextState)
            - _valueFunction.getValue(_currentState);

        Logger.debug("Delta     : " + valueDelta);

        // double sigma = reward + discount * valueDelta;

        double sigma = reward + getDiscount() * _valueFunction.getValue(nextState)
            - _valueFunction.getValue(_currentState);

        /*
         * double sigma = reward + valueFunction.getValue( currentState) - discount *
         * valueFunction.getValue( nextState );
         */

        Logger.debug("Sigma     : " + sigma);

        // add the state to the eligibilty trace
        _trace.add(_currentState);

        // update the value function for all eligible states
        int eligibleCount = _trace.getLength();

        Logger.debug(eligibleCount + " states");

        for (int i = 0; i < eligibleCount; i++) {
            // calculate each particular state's update value
            // based on the state's eligibility and the learning rate
            State eligibleState = _trace.get(i);
            double error = getLearningRate() * sigma * _trace.getEligibility(i);
            Logger.debug("State     : " + eligibleState);
            Logger.debug("Error     : " + error);
            _valueFunction.update(eligibleState, error);
        }

        return nextState;
    }
}
