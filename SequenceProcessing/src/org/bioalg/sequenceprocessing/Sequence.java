/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.sequenceprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author faroq
 */
public class Sequence {

    ArrayList<Entry> entries = new ArrayList<Entry>();
    File file;

    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    
    public Sequence(File file) {
        this.file = file;
    }

    public boolean load() {

        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return false;
        }

        BufferedReader bFreader = null;
        try {
            bFreader = new BufferedReader(new FileReader(file));

            String nameline = null;
            String stringline = null;
            int linescounter = 0;
            // repeat until all lines are read
            // lines should be even so we starts form 1 to n

            // the file has the following structure
            // repeat number
            // sequence
            // empty line

            nameline = bFreader.readLine();
            while (true) {
                //now read the string lines
                if (nameline == null) {
                    break;
                }
                String value = "";
                if (nameline.charAt(0) == '>') { // it is a new entry collect all untill reach eith null or another >
                    linescounter++;
                    while (true) { // find the value of the sequence
                        stringline = bFreader.readLine();
                        if (stringline == null) {
                            break;
                        }// if is a vaild chars add them to the value
                        if (!stringline.equals("") && !stringline.startsWith(">")) {
                            value += stringline;
                        } else {
                            break;
                        }

                    }// if we got some values then add them to the entries
                    if (nameline != null) {
                        value = value.replaceAll("\n", "");  // remove the undersierable newlines
                        Entry ent = new Entry(nameline, value);
                        entries.add(ent);
                    }

                }
                //check if the last string was a sequence name
                if (stringline != null && stringline.startsWith(">")) {
                    nameline = stringline;
                } else {
                    nameline = bFreader.readLine();
                }





            }




        } catch (Exception ex) {
           // Logger.getLogger(SpaType.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error in file format");
        }

        return true;
    }

    public void print() {
        int size = entries.size();
        for (int i = 0; i < size; i++) {
            System.out.println(entries.get(i).toString());
        }
        System.out.println("Number of entries: " + size);

    }

    public Entry getEntry(int index) {

        return entries.get(index);
    }

    public int getlength() {
        return entries.size();
    }
    
    public void DNAComplement(){
        for(int i=0;i<entries.size();i++)
            entries.get(i).DNAComplement();
    }
}
