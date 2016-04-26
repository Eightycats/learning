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
