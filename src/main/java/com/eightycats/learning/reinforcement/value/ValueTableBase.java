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

package com.eightycats.learning.reinforcement.value;

import java.util.HashMap;
import java.util.Map;

/**
 * A table of double values with a default and a method to increment the values.
 */
public class ValueTableBase<K>
{
    protected Map<K, Double> _table = new HashMap<K, Double>();

    protected double _defaultValue = 0;

    public void reset ()
    {
        _table.clear();
    }

    public void setValue (K key, double value)
    {
        _table.put(key, value);
    }

    public double getValue (K key)
    {
        Double value = _table.get(key);
        if (value == null) {
            return getDefaultValue();
        }
        return value.doubleValue();
    }

    public void update (K key, double deltaValue)
    {
        double value = getValue(key);
        value += deltaValue;
        setValue(key, value);
    }

    public boolean hasValue (K key)
    {
        return _table.containsKey(key);
    }

    public void setDefaultValue (double defaultValue)
    {
        _defaultValue = defaultValue;
    }

    public double getDefaultValue ()
    {
        return _defaultValue;
    }

    @Override
    public String toString ()
    {
        return _table.toString();
    }
}
