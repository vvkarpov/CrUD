package ru.vkarpov.dev;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
CrUD for a table inside a file.
Read file name for CrUD operations from console.
The program starts with the following set of parameters: -c productName price quantity
*/

public class Solution {

    public static String fileName;

    public static void main(String[] args) throws IOException {

        if (args[0].equals("-c") && args[1] != null && args[2] != null && args[3] != null ){//Check parameters

            Lines lines = new Lines (newID(), args[1], args[2], args[3]); //Create new line
            addLine(lines); //Writing to file
        }
    }

    private static void addLine(Lines obj) throws IOException {//Writing

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        fileName = reader.readLine();
        FileWriter fileWriter = new FileWriter(fileName, true);
        String str = obj.getId() + obj.getName() + obj.getPrice() + obj.getQuantity() + "\n";
        fileWriter.write(str);

        fileWriter.close();
        reader.close();
    }

    private static String newID() throws IOException {//Create new ID or increase old one;

        String id = "1";
        String line = null;

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while (reader.ready()){
            line = reader.readLine();
        }
        if (line != null){
            Matcher matcher = Pattern.compile("^.{8}").matcher(line); //Cut old ID
            if (matcher.find()){
                String str = matcher.group().trim(); //Clearing spaces
                int intID = Integer.parseInt(str);//Increase old ID
                intID++;
                id = Integer.toString(intID);
            }
        }
        reader.close();

        id = addSpaces(id ,Lines.NUMBER_CHAR_ID);
        return id;
    }

    public static String addSpaces(String elements, int numberCharacters){//Add spaces for formatting

        int count = (numberCharacters - elements.length());
        if (elements.length() < numberCharacters){
            for (int i = 0; i < count; i++) {
                elements += " ";
            }
        }
        return elements;
    }

}