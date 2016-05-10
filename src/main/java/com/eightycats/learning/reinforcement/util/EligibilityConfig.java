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

package com.eightycats.learning.reinforcement.util;

import com.eightycats.learning.reinforcement.EpisodeConfig;

public class EligibilityConfig extends EpisodeConfig implements EligibilityConstants
{
    protected double _lambda = DEFAULT_LAMBDA;

    /**
     * If the eligibility of a state in this trace falls below this minimum value, then the state
     * will be removed from the trace.
     */
    protected double _minimumEligibility = DEFAULT_MIN_ELIGIBILITY;

    /**
     * This sets the maximum possible size of the trace. This is really only used in the case where
     * lambda is 1.0. When lambda is 1.0, the length of the trace can theoretically be infinite, so
     * this sets a hard limit on the trace length, so that we don't run out of memory.
     */
    protected int _traceSizeLimit = DEFAULT_SIZE_LIMIT;

    /**
     * The maximum length of the trace list based on the value of lambda. Has to be computed, so
     * this field saves off the current value.
     */
    protected int _maxLength;

    public double getLambda ()
    {
        return _lambda;
    }

    /**
     * Lambda controls two things: 1) the maximum length of this eligibility trace list, and 2) the
     * magnitude of the update for eligible states. A larger lambda value means that more past
     * states will get updated values. Must be a value between 0.0 and 1.0.
     */
    public void setLambda (double lambda)
    {
        if (lambda < 0.0 || lambda > 1.0) {
            throw new IllegalArgumentException("The lambda value [" + lambda
                + "] must be between 0.0 and 1.0.");
        }
        _lambda = lambda;
    }

    /**
     * Gets the product of the discount times lambda.
     */
    public double getDiscountedLambda ()
    {
        return getDiscount() * getLambda();
    }

    /**
     * Sets the minimum eligibility below which we don't care about keeping track of an element in a
     * trace.
     */
    public void setMinimumEligibility (double minimumEligibility)
    {
        _minimumEligibility = minimumEligibility;
        updateMaxTraceLength();
    }

    public double getMinimumEligibility ()
    {
        return _minimumEligibility;
    }

    /**
     * This sets the largest possible size of the trace. This is really only used in the case where
     * lambda is 1.0. When lambda is 1.0, the length of the trace can theoretically be infinite, so
     * this sets a hard limit on the trace length, so we don't run out of memory.
     */
    public void setTraceSizeLimit (int traceSizeLimit)
    {
        _traceSizeLimit = traceSizeLimit;
    }

    public int getTraceSizeLimit ()
    {
        return _traceSizeLimit;
    }

    protected void updateMaxTraceLength ()
    {
        _maxLength = getMaxTraceLength();
    }

    protected int getMaxTraceLength ()
    {
        double discountedLambda = getDiscountedLambda();

        // if lambda is 1.0, then the trace extends all the way
        // back to the beginning of the episode, and
        // the trace can get very large. This returns
        // a large but finite upper bounds to keep us
        // from running out of memory.
        if (discountedLambda >= 1) {
            return getTraceSizeLimit();
        }

        int length = 0;

        double eligibility = 1;

        // decay the eligibility until it is
        // smaller than the minimum eligibility
        while (eligibility > getMinimumEligibility() && length < getTraceSizeLimit()) {
            eligibility *= discountedLambda;
            length++;
        }

        return length;
    }
}
