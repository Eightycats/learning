package com.eightycats.learning.reinforcement;

public class EpisodeConfig
{
    public static final double DEFAULT_LEARNING_RATE = 0.2;

    public static final double NO_DISCOUNT = 1.0;

    /**
     * The learning rate or step value (alpha).
     */
    protected double _learningRate = DEFAULT_LEARNING_RATE;

    /**
     * The factor by which the reward signal will fade as we get farther back in the eligibility
     * trace (gamma).
     */
    protected double _discount = NO_DISCOUNT;

    /**
     * The learning rate determines how quickly the value function will change based on observed
     * errors.
     */
    public void setLearningRate (double learningRate)
    {
        _learningRate = learningRate;
    }

    public double getLearningRate ()
    {
        return _learningRate;
    }

    /**
     * This discount is used to determine how valuable future rewards are. The discount must be a
     * value between 0.0 and 1.0.
     */
    public void setDiscount (double discount)
    {
        if (discount < 0.0 || discount > 1.0) {
            throw new IllegalArgumentException("The discount value [" + discount
                + "] must be between 0.0 and 1.0.");
        }
        _discount = discount;
    }

    public double getDiscount ()
    {
        return _discount;
    }
}
