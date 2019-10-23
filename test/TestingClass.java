
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JesusCamargo
 */
public class TestingClass {
    public static void main(String[] args) {
        String t1 = "S->E,S",t2 = "S->E";
        String str = identifyCommonSubStrOfNStr(new String[]{"S->E,S","S->E","E","a"});
        System.out.println(str);
        /*char[] ct1 = t1.toCharArray();
        char[] ct2 = t2.toCharArray();
        int i = 0;
        while(i < ct1.length && i<ct2.length){
            if (ct1[i] != ct2[i]) {
                System.out.println("different at " + i + ": " + ct1[i] + ", " + ct2[i]);
                break;
            }
            i++;
        }
        System.out.println(t1.substring(0, i));
        t2 = t1.replaceFirst(t1.substring(0, i), "REPLACED");
        System.out.println(t1);
        System.out.println(t2);
        if (!(i<ct1.length || i<ct2.length)) {
            System.out.println(t1 + " equals " + t2);
        }*/
        /*while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());*/
    }
    public static String identifyCommonSubStrOfNStr(String [] strArr){

        String commonStr="";
        String smallStr ="";        

        //identify smallest String      
        for (String s :strArr) {
            if(smallStr.length()< s.length()){
                smallStr=s;
            }
        }

        String tempCom="";
        char [] smallStrChars=smallStr.toCharArray();               
        for (char c: smallStrChars){
            tempCom+= c;

            for (String s :strArr){
                if(!s.contains(tempCom)){
                    tempCom=c+"";
                    for (String t : strArr){
                        if(!t.contains(tempCom)){
                            tempCom="";
                            break;
                        }
                    }
                    break;
                }               
            }

            if(tempCom!="" && tempCom.length()>commonStr.length()){
                commonStr=tempCom;  
            }                       
        }   

        return commonStr;
    }
}
