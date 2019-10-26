/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camargo.marquez;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JesusCamargo
 */
public class ContextFreeGrammar {

    private LinkedHashMap<String,ArrayList<String>> nonTerminals;
    private LinkedHashMap<String,LinkedHashSet<String>> firstMap;
    private LinkedHashMap<String,LinkedHashSet<String>> followMap;
    
    public ContextFreeGrammar(){
        nonTerminals = new LinkedHashMap<>();
        firstMap = new LinkedHashMap<>();
        followMap = new LinkedHashMap<>();
    }
    
    public void loadFromFile(File file){
        try {
            Scanner fScan = new Scanner(file);
            while(fScan.hasNext()){
                String temp = fScan.nextLine();
                if (nonTerminals.containsKey(temp.charAt(0)+"")) {
                    nonTerminals.get(temp.charAt(0)+"").add(temp.substring(3));
                }else{
                    ArrayList<String> productions = new ArrayList<>();
                    productions.add(temp.substring(3));
                    nonTerminals.put(temp.charAt(0)+"", productions);
                }
            }
            this.validate();
            this.first();
            System.out.println("first: "+ firstMap);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContextFreeGrammar.class.getName()).log(Level.SEVERE, "File not found", ex);
        }
    }
    
    private void validate(){
       /*nonTerminals.forEach((key,prods)->{
           if (!key.endsWith("'")) {
                System.out.println("Before: " + prods);
                ArrayList<String> temp = checkRecursion(key, prods);
                System.out.println("After recursion: " + prods + "->" + temp);
                if (temp == null) {
                    temp = checkFactorization(key, prods);
                    System.out.println("After factorization: " + prods + "->" + temp);
               }
                if (temp != null) {
                   nonTerminals.put(key + "'", temp); 
                }
           }
       });*/
        for (String key : nonTerminals.keySet().toArray(new String[nonTerminals.size()])) {
           ArrayList<String> prods  = nonTerminals.get(key);
           System.out.println("Before: " + prods);
           ArrayList<String> temp = checkRecursion(key, prods);
           System.out.println("After recursion: " + prods + "->" + temp);
           if (temp == null) {
               temp = checkFactorization(key, prods);
               System.out.println("After factorization: " + prods + "->" + temp);
           }
            if (temp != null) {
                nonTerminals.put(key + "'", temp);
            } 
        }
    }
    
    private ArrayList<String> checkFactorization(String header,ArrayList<String> prods){
        if (prods.size()>1) {
            String factor = "";
            int i = 0;
            while(i < prods.size()) {
                for (int j = i; j < prods.size(); j++) {
                    String temp;
                    if (factor.isEmpty()) {
                        temp = commonString(prods.get(i),prods.get(j));
                    }else{
                        temp = commonString(factor,prods.get(j));
                    }
                    if (!temp.isEmpty()) {
                        factor = temp;
                    }
                }
                if (!factor.isEmpty()) {
                    //System.out.println("Factor: " + factor);
                    break;
                }
                i++;
            }
            if (!factor.isEmpty()) {
                ArrayList<String> tProds = new ArrayList<>();
                i=0;
                while(i<prods.size()){
                    String prod = prods.get(i);
                    if (prod.startsWith(factor)) {
                        String gamma = prod.substring(factor.length(), prod.length());
                        tProds.add(gamma.isEmpty() ? "&" : gamma);
                        System.out.println("Factoring: " + (gamma.isEmpty() ? "&" : gamma));
                        prods.remove(i);
                    }else{
                        i++;
                    }
                }
                prods.add(factor + header + "'");
                return tProds;
            }
        }
        return null;
    }
    
    private String commonString(String str1, String str2){
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
    
    private ArrayList<String> checkRecursion(String header, ArrayList<String> prods){
        ArrayList<String> alpha = new ArrayList<>();
        ArrayList<String> removing= new ArrayList<>();
        prods.forEach((prod) -> {
            if (prod.startsWith(header)) {
                alpha.add(prod.substring(1)+header+"'");
                removing.add(prod);
            }
        });
        if (!alpha.isEmpty()) {
            prods.removeAll(removing);
            prods.forEach((prod)->prod.concat(header+"'"));
            prods.replaceAll((String t) -> t+header+"'");
            alpha.add("&");
            return alpha;
        }
        //prods.
        return null;
    }
    
    /*private LinkedHashMap<String,ArrayList<String>> first(){
       LinkedHashMap<String,ArrayList<String>> firstHashMap = new LinkedHashMap<>();
       for (String key : nonTerminals.keySet().toArray(new String[nonTerminals.size()])) {
        ArrayList<String> firstArray = new ArrayList<>();
         firstArray = searchTerminal(nonTerminals.get(key),0);
         firstHashMap.put(key,firstArray);
       }
       return firstHashMap;
   }
   
   private ArrayList<String> searchTerminal(ArrayList<String> auxArray, int j){
        int i = 0;
        ArrayList<String> firstArray = new ArrayList<>();
        j = 0;
        if(j<auxArray.size()){
            String temp = auxArray.get(j).length() > 1 && auxArray.get(j).charAt(i+1) == (char)39 ?
                    auxArray.get(j).substring(0, i+2) : auxArray.get(j).charAt(i)+"";
            //System.out.println("temp: " + temp);
            if(!nonTerminals.containsKey(temp)){
                firstArray.add(temp);
                //searchTerminal(nonTerminals.get(auxArray.get(j).charAt(i)+""),j++);
            }else{
                System.out.println("switch to: " +temp);
                firstArray.addAll(searchTerminal(nonTerminals.get(temp),j));
            }
        }
       return firstArray;
   }*/
    private void first(){
        Stack<String[]> update = new Stack<>();
        nonTerminals.forEach((header, prods) -> {
            LinkedHashSet<String> first = new LinkedHashSet<>();
            prods.forEach((prod) -> {
                String start = prod.charAt(0)+"";
                if (start.toLowerCase().equals(start)) {
                    first.add(start);
                }else{
                    if (prod.length() > 1 && prod.charAt(1) == (char)39) {
                        start+="'";
                    }
                    if (firstMap.containsKey(start)) {
                        first.addAll(firstMap.get(start));
                    }else{
                        update.add(new String[]{header,start});//header contains start
                    }
                }
            });
            firstMap.put(header, first);
        });
        while(!update.isEmpty()){
            String[] reference = update.pop();
            firstMap.get(reference[0]).addAll(firstMap.get(reference[1]));
        }
    }
    
    /**
     * @return the nonTerminals
     */
    public LinkedHashMap<String,ArrayList<String>> getNonTerminals() {
        return nonTerminals;
    }

    /**
     * @param nonTerminals the nonTerminals to set
     */
    public void setNonTerminals(LinkedHashMap<String,ArrayList<String>> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    /**
     * @return the firstMap
     */
    public LinkedHashMap<String,LinkedHashSet<String>> getFirstMap() {
        return firstMap;
    }

    /**
     * @return the followMap
     */
    public LinkedHashMap<String,LinkedHashSet<String>> getFollowMap() {
        return followMap;
    }
    
}
