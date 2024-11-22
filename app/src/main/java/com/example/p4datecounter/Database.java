package com.example.p4datecounter;

import android.content.Context;
import android.content.SharedPreferences;

public class Database {
    private  static  final  String SP_NAME="Data Counter";
    public  static  final  String Date="date";
    public  static  final  String AGE="Age";
    public  static  void  saveName(Context context,String key, String value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static  String readName(Context context,String key){

        SharedPreferences preferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }
    public  static  void  saveDate(Context context,String keys, long values){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putLong(keys,values);
        editor.apply();
    }

    public static  long readDate(Context context,String keys){

        SharedPreferences preferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return preferences.getLong(keys,-1);
    }

    public  static void saveAge(Context context,String keys,int values){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(keys,values);
        editor.apply();
    }

    public  static int readAge(Context context,String keys){
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        return  preferences.getInt(keys,0);
    }

}
