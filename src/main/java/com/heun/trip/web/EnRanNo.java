package com.heun.trip.web;

import java.util.Random;

public class EnRanNo {
  
   
  public static StringBuffer randomNo() { 
    Random rnd =new Random();

    StringBuffer buf =new StringBuffer();


    for(int i=0;i<10;i++){
        if(rnd.nextBoolean()){
            buf.append((char)((int)(rnd.nextInt(26))+97));
        }else{
            buf.append((rnd.nextInt(10))); 
        }
    }
    return buf;
  }
}



