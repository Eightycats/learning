package com.eightycats.learning.reinforcement.value;

import com.eightycats.litterbox.math.Range;
import com.eightycats.learning.reinforcement.State;

/**
 *
 *
 */
public abstract class StateSerializerBase implements StateSerializer
{
   private int count;

   private Range[] ranges;        
      
   public StateSerializerBase(int outputCount)
   {
      count = outputCount;
      
      // default the output range values from 0.0 to 1.0
      ranges = new Range[count];
      
      for( int i = 0; i < ranges.length; i++ )
      {
         ranges[i] = Range.UNIT_RANGE;
      }      
      
   }
   
   /**
    * Returns the length of the array returned by this
    * class's serialize method.
    * 
    * @see mjensen.learning.reinforcement.value.StateSerializer#getCount()
    */
   public int getCount()
   {
      return count;
   }

   public void setValueRange( int index, double minValue, double maxValue )
   {
      setValueRange( index, new Range( minValue, maxValue ) );
   }
   
   public void setValueRange( int index, Range range )
   {
      ranges[index] = range;
   }   
   
   /**
    * @see mjensen.learning.reinforcement.value.StateSerializer#getValueRange(int)
    */
   public Range getValueRange( int index )
   {
      return ranges[index];
   }

   /**
    * @see mjensen.learning.reinforcement.value.StateSerializer#serialize(mjensen.learning.reinforcement.State)
    */
   public abstract double[] serialize( State state );

}
