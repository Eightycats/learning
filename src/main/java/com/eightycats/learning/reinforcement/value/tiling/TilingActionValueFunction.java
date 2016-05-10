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

package com.eightycats.learning.reinforcement.value.tiling;

import java.util.HashMap;
import java.util.Map;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.value.StateSerializer;
import com.eightycats.learning.reinforcement.value.ValueTableBase;
import com.eightycats.math.tiling.TileCoding;
import com.eightycats.math.util.Range;

/**
 *
 *
 */
public class TilingActionValueFunction
    implements ActionValueFunction
{
    /**
     * This maps each unique tile ID to a table of action values.
     */
    private Map<Long, ActionTable> actionValueTables = new HashMap<Long, ActionTable>();

    /**
     * Used to convert states into an array of double values to be used as inputs to the tiling
     * function.
     */
    protected StateSerializer serializer;

    /**
     * Used to calculate tile IDs.
     */
    private TileCoding tilings;

    /**
     * The default value to return if no action value is found.
     */
    private double defaultValue = 0.0;

    public TilingActionValueFunction (StateSerializer serializer, int tilingCount,
        long tilesPerDimension)
    {
        this.serializer = serializer;

        tilings = new TileCoding(tilingCount, tilesPerDimension);

        // add one dimension to the tile coding for
        // each input parameter that the serializer
        // returns
        int paramCount = serializer.getCount();

        for (int i = 0; i < paramCount; i++) {
            Range possibleValues = serializer.getValueRange(i);
            addDimension(possibleValues.getMin(), possibleValues.getMax());
        }

    }

    protected void addDimension (double minValue, double maxValue)
    {
        tilings.addDimension(minValue, maxValue);
    }

    @Override
    public double getValue (State s, Action a)
    {
        double[] inputs = serializer.serialize(s);
        return getValue(inputs, a);
    }

    @Override
    public void update (State state, Action action, double deltaValue)
    {
        double[] inputs = serializer.serialize(state);

        long[] tiles = tilings.getTiles(inputs);

        // The action's value is the sum of
        // each tile's value, so the update value
        // should be divided evenly among each tile.
        double updateValue = deltaValue / tiles.length;
        for (int tileIndex = 0; tileIndex < tiles.length; tileIndex++) {
            ActionTable actionValues = getActionValueTable(tiles[tileIndex]);
            actionValues.update(action, updateValue);
        }
    }

    public double getValue (double[] inputs, Action action)
    {
        long[] tiles = tilings.getTiles(inputs);

        // add up the values of each of the tiles
        double sum = 0.0;
        for (int i = 0; i < tiles.length; i++) {
            sum += getValue(tiles[i], action);
        }
        return sum;
    }

    /**
     * Gets the value of an action for a particular tile.
     *
     * @param tileIndex the unique tile ID.
     * @param action
     */
    protected double getValue (long tileIndex, Action action)
    {
        ActionTable actionValues = getActionValueTable(tileIndex);
        return actionValues.getValue(action);
    }

    /**
     * Resets (clears) all action values.
     */
    @Override
    public void reset ()
    {
        actionValueTables.clear();
    }

    public double getDefaultValue ()
    {
        return defaultValue;
    }

    public void setDefaultValue (double defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    protected ActionTable getActionValueTable (long tileIndex)
    {
        ActionTable actionValues;

        Long key = new Long(tileIndex);
        actionValues = actionValueTables.get(key);

        if (actionValues == null) {
            actionValues = new ActionTable();
            actionValueTables.put(key, actionValues);
        }

        return actionValues;
    }

    protected class ActionTable extends ValueTableBase<Action>
    {
        /**
         * Returns the default action value
         */
        @Override
        public double getDefaultValue ()
        {
            return TilingActionValueFunction.this.getDefaultValue();
        }
    }
}
