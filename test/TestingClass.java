
import java.util.HashMap;

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
        /*String t1 = "E,S",t2 = "abb";
        System.out.println(commonString("E",t1));
        System.out.println(commonString("E",t2));*/
        HashMap<String,String> a = new HashMap();
        a.put("monda", "verga");
        System.out.println("a\nb");
        System.out.println(a.get("monda"));
    }
    static String commonString(String str1, String str2){
        char[] chars1 = str1.toCharArray();
            char[] chars2 = str2.toCharArray();
            int i = 0;
            while(i < chars1.length && i<chars2.length){
                if (chars1[i] != chars2[i]) {
                    break;
                }
                i++;
            }
        return str1.substring(0, i);
    }
}
