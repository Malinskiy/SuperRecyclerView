package com.malinskiy.util;

/**
 * Created by ayman on 1/28/16.
 */
public class FloatUtil {
    private final static float EPSILON =  0.00000001F;
    
    public static boolean compareFloats(float f1, float f2){
        return Math.abs(f1 - f2) <= EPSILON ;
    } 
}
