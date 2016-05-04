package com.eightycats.learning.reinforcement.value.tiling;

import com.eightycats.litterbox.math.Range;
import com.eightycats.litterbox.math.tiling.TileCoding;
import com.eightycats.litterbox.math.tiling.TilingDimension;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.ValueFunction;
import com.eightycats.learning.reinforcement.value.StateSerializer;
import com.eightycats.learning.reinforcement.value.ValueTableBase;

/**
 *
 *
 */
public class TilingValueFunction implements ValueFunction
{
    /**
     * This table will contain the values of each tile.
     */
    protected ValueTableBase<Long> _tileValues = new ValueTableBase<Long>();

    protected StateSerializer _serializer;

    protected TileCoding _tilings;

    // TODO
    protected double _resolution;

    public TilingValueFunction (StateSerializer serializer, int tilingCount, long tilesPerDimension)
    {
        _serializer = serializer;

        _tilings = new TileCoding(tilingCount, tilesPerDimension);

        // add one dimension to the tile coding for each input parameter that the serializer returns
        int paramCount = serializer.getCount();
        for (int i = 0; i < paramCount; i++) {
            Range possibleValues = serializer.getValueRange(i);
            addDimension(possibleValues.getMin(), possibleValues.getMax());
        }
    }

    protected void addDimension (double minValue, double maxValue)
    {
        _tilings.addDimension(minValue, maxValue);
    }

    @Override
    public double getValue (State s)
    {
        return getValue(_serializer.serialize(s));
    }

    public double getValue (double[] inputs)
    {
        long[] tiles = _tilings.getTiles(inputs);

        // add up the values of each of the tiles
        double sum = 0.0;

        for (int i = 0; i < tiles.length; i++) {
            sum += _tileValues.getValue(tiles[i]);
        }

        return sum;
    }

    @Override
    public void reset ()
    {
        _tileValues.reset();
    }

    @Override
    public void update (State state, double deltaValue)
    {
        double[] inputs = _serializer.serialize(state);

        long[] tiles = _tilings.getTiles(inputs);

        double updateValue = deltaValue / tiles.length;

        for (int i = 0; i < tiles.length; i++) {
            _tileValues.update(tiles[i], updateValue);
        }
    }

    public void setDefaultValue (double defaultValue)
    {
        // TODO: should the default value be divided by the number of tiles that are returned
        _tileValues.setDefaultValue(defaultValue);
    }

    public double getDefaultValue ()
    {
        return _tileValues.getDefaultValue();
    }

    public double getResolution ()
    {
        return _resolution;
    }

    public void setResolution (double resolution)
    {
        _resolution = resolution;
    }

    @Override
    public String toString ()
    {
        StringBuffer result = new StringBuffer();

        int inputCount = _tilings.getTiling(0).getDimensionCount();
        double[] inputs = new double[inputCount];

        generateString(result, inputs, 0);

        return result.toString();
    }

    private void generateString (StringBuffer result, double[] inputs, int inputIndex)
    {
        if (inputIndex >= inputs.length) {
            result.append("(");

            for (int i = 0; i < inputs.length; i++) {
                if (i > 0) {
                    result.append(", ");
                }

                result.append(inputs[i]);
            }

            result.append(") : ");
            result.append(getValue(inputs));
            result.append("\n");

        } else {
            TilingDimension dimension = _tilings.getTiling(0).getDimension(inputIndex);

            int count = (int) dimension.getTileCount();

            // TODO: fix hard-coded values
            // double value = dimension.getMinValue();
            // double increment = dimension.getTileWidth();
            double value = 0.0;
            double max = 1.0;
            double increment = max / count;

            for (int i = 0; i < count; i++) {
                inputs[inputIndex] = value;

                generateString(result, inputs, inputIndex + 1);

                value += increment;
            }

            inputs[inputIndex] = max;
            generateString(result, inputs, inputIndex + 1);
        }

    }
}
