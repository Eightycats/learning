package com.eightycats.learning.reinforcement.value.tiling;

import java.util.*;

import com.eightycats.litterbox.logging.Logger;
import com.eightycats.litterbox.math.ArrayUtils;
import com.eightycats.litterbox.math.Range;
import com.eightycats.litterbox.math.tiling.*;

import com.eightycats.learning.reinforcement.Action;
import com.eightycats.learning.reinforcement.ActionValueFunction;
import com.eightycats.learning.reinforcement.State;
import com.eightycats.learning.reinforcement.value.StateSerializer;
import com.eightycats.learning.reinforcement.value.ValueTableBase;

/**
 *
 *
 */
public class TilingActionValueFunction implements ActionValueFunction
{

   /**
    * This maps each unique tile ID to 
    * a table of action values.
    */
   private Map actionValueTables = new Hashtable();         
   
   /**
    * Used to convert states into an array of
    * double values to be used as inputs to the 
    * tiling function.
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
   
   public TilingActionValueFunction( StateSerializer serializer,
                                     int tilingCount,
                                     long tilesPerDimension )
   {
      this.serializer = serializer;
    
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
    * @see mjensen.learning.reinforcement.ActionValueFunction#getValue(mjensen.learning.reinforcement.State, mjensen.learning.reinforcement.Action)
    */
   public double getValue( State s, Action a )
   {
      double[] inputs = serializer.serialize( s );
      return getValue( inputs, a );  
   }


   /**
    * @see mjensen.learning.reinforcement.ActionValueFunction#update(mjensen.learning.reinforcement.State, mjensen.learning.reinforcement.Action, double)
    */
   public void update( State state, Action action, double deltaValue )
   {
      double[] inputs = serializer.serialize( state );      
   
      Logger.debug("Inputs  : "+ ArrayUtils.toString(inputs) ); 
      
      long[] tiles = tilings.getTiles( inputs );       
   
      // The action's value is the sum of 
      // each tile's value, so the update value
      // should be divided evenly among each tile.
      double updateValue = deltaValue / (double) tiles.length;      
      
      Logger.debug("Updating: "+state+"-"+action);
      Logger.debug("Tiles   : "+ ArrayUtils.toString(tiles) );      
      Logger.debug("From    : "+getValue(state,action) );      
      
      for( int tileIndex = 0; tileIndex < tiles.length; tileIndex++ )
      {      
         ActionTable actionValues = getActionValueTable( tiles[tileIndex] );
         actionValues.update( action, updateValue );
      }      

      Logger.debug("To     : "+getValue(state,action) );      
      
   }   
   
   public double getValue( double[] inputs, Action action )
   {

      long[] tiles = tilings.getTiles( inputs ); 
      
      // add up the values of each of the tiles
      double sum = 0.0;
      
      for( int i = 0; i < tiles.length; i++ )
      {
         sum += getValue( tiles[i], action );
      }
      
      return sum;      
   }   
   
   /**
    * Gets the value of an action for a particular tile. 
    * 
    * @param tileIndex - the unique tile ID. 
    * @param action
    * @return
    */
   protected double getValue( long tileIndex, Action action )
   {
      ActionTable actionValues = getActionValueTable( tileIndex );
      return actionValues.getValue( action );
   }
   
   /**
    * Resets (clears) all action values.
    * 
    * @see mjensen.learning.reinforcement.ActionValueFunction#reset()
    */
   public void reset()
   {
      actionValueTables.clear();      
   }  

   /**
    * @return the defaultValue
    */
   public double getDefaultValue()
   {
      return defaultValue;
   }

   /**
    * @param defaultValue the defaultValue to set
    */
   public void setDefaultValue( double defaultValue )
   {
      this.defaultValue = defaultValue;
   }
   
   protected ActionTable getActionValueTable( long tileIndex )
   {
      ActionTable actionValues;      
      
      Long key = new Long( tileIndex );      
      actionValues = (ActionTable) actionValueTables.get(key); 
      
      if( actionValues == null )
      {
         actionValues = new ActionTable();
         actionValueTables.put( key, actionValues );
      }
      
      return actionValues;
      
   }
   
   protected class ActionTable extends ValueTableBase
   {
      public double getValue( Action action )
      {
         return super.getValue( action );
      }

      public void update( Action action, double deltaValue )
      {
         super.update( action, deltaValue );         
      }
            
      /**
       * Returns the default action value  
       * 
       * @see mjensen.learning.reinforcement.value.ValueTableBase#getDefaultValue()
       */
      public double getDefaultValue()
      {
         return TilingActionValueFunction.this.getDefaultValue();
      }
      
   }

}
