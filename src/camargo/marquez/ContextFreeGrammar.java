/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camargo.marquez;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JesusCamargo
 */
public class ContextFreeGrammar {

    private HashMap<Character,ArrayList<String>> nonTerminals;
    
    public ContextFreeGrammar(){
        nonTerminals = new HashMap<>();
    }
    
    public void loadFromFile(File file){
        try {
            Scanner fScan = new Scanner(file);
            while(fScan.hasNext()){
                String temp = fScan.nextLine();
                if (nonTerminals.containsKey(temp.charAt(0))) {
                    nonTerminals.get(temp.charAt(0)).add(temp.substring(3));
                }else{
                    ArrayList<String> productions = new ArrayList<>();
                    productions.add(temp.substring(3));
                    nonTerminals.put(temp.charAt(0), productions);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContextFreeGrammar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the nonTerminals
     */
    public HashMap<Character,ArrayList<String>> getNonTerminals() {
        return nonTerminals;
    }

    /**
     * @param nonTerminals the nonTerminals to set
     */
    public void setNonTerminals(HashMap<Character,ArrayList<String>> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }
}
