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
   private TileValueTable tileValues = new TileValueTable();

   protected StateSerializer serializer;   
   
   private TileCoding tilings;
   
   // TODO
   private double resolution;
   
   public TilingValueFunction( StateSerializer serializer,
                               int tilingCount,
                               long tilesPerDimension )
   {
      this.serializer = serializer;

      // this.resolution = resolution;         
      
      tilings = new TileCoding( tilingCount, tilesPerDimension );
      
      // add one dimension to the tile coding for
      // each input parameter that the serializer 
      // returns
      int paramCount = serializer.getCount();
      
      for( int i = 0; i < paramCount; i++ )
      {
         Range possibleValues = serializer.getValueRange(i);
         addDimension( possibleValues.getMin(), possibleValues.getMax() );
      }
      
   }
   
   protected void addDimension(double minValue, double maxValue)
   {
      tilings.addDimension(minValue, maxValue);
   }
   
   /**
    * @see mjensen.learning.reinforcement.ValueFunction#getValue(mjensen.learning.reinforcement.State)
    */
   public double getValue( State s )
   {
      double[] inputs = serializer.serialize( s );
      return getValue( inputs );  
   }

   public double getValue( double[] inputs )
   {

      long[] tiles = tilings.getTiles( inputs ); 
      
      // add up the values of each of the tiles
      double sum = 0.0;
      
      for( int i = 0; i < tiles.length; i++ )
      {
         sum += tileValues.getValue( tiles[i] );
      }
      
      return sum;      
   }
   
   /**
    * @see mjensen.learning.reinforcement.ValueFunction#reset()
    */
   public void reset()
   {
      tileValues.reset();      
   }

   /**
    * @see mjensen.learning.reinforcement.ValueFunction#update(mjensen.learning.reinforcement.State, double)
    */
   public void update( State state, double deltaValue )
   {
      
      double[] inputs = serializer.serialize( state );   
      
      long[] tiles = tilings.getTiles( inputs );      
      
      double updateValue = deltaValue / (double) tiles.length;
      
      for( int i = 0; i < tiles.length; i++ )
      {         
         tileValues.update( tiles[i], updateValue );         
      }
      
   }
   
   public void setDefaultValue(double defaultValue)
   {
      // TODO: should the default value be divided by the 
      // number of tiles that are returned
      tileValues.setDefaultValue( defaultValue );
   }

   public double getDefaultValue()
   {
       return tileValues.getDefaultValue();
   }

   /**
    * @return the resolution
    */
   public double getResolution()
   {
      return resolution;
   }

   /**
    * @param resolution the resolution to set
    */
   public void setResolution( double resolution )
   {
      this.resolution = resolution;
   }   
   
   public String toString()
   {
      StringBuffer result = new StringBuffer();
             
      int inputCount = tilings.getTiling(0).getDimensionCount();
      double[] inputs = new double[inputCount];
      
      generateString( result, inputs, 0 );
      
      return result.toString();
      
   }
   
   private void generateString( StringBuffer result, 
                                double[] inputs,
                                int inputIndex )
   {
      if( inputIndex >= inputs.length )
      {
         result.append("(");
         
         for( int i = 0; i < inputs.length; i++ )
         {
            if( i > 0 )
            {
               result.append(", ");
            }
            
            result.append( inputs[i] );     
         }

         result.append(") : ");         
         result.append( getValue(inputs) );
         result.append("\n");         
         
      }
      else
      {
      
         TilingDimension dimension = tilings.getTiling(0).getDimension(inputIndex);

         int count = (int) dimension.getTileCount();
                  
         // TODO: fix hard-coded values
         // double value = dimension.getMinValue();                  
         // double increment = dimension.getTileWidth();
         double value = 0.0;
         double max = 1.0;
         double increment = max / (double) count; 
         
         for( int i = 0; i < count; i++ )
         {
            inputs[inputIndex] = value;
            
            generateString( result, inputs, inputIndex+1 );
            
            value += increment;               
         }
         
         inputs[inputIndex] = max;   
         generateString( result, inputs, inputIndex+1 );         
         
      }
      
   }
   
   private class TileValueTable extends ValueTableBase
   {
      public double getValue( long tileID )
      {
         return super.getValue( new Long(tileID) );
      }

      public void update( long tileID, double deltaValue )
      {
         super.update( new Long(tileID), deltaValue );         
      }
      
   }
   
}
