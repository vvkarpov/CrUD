package ru.vkarpov.dev;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
CrUD for a table inside a file.
Read file name for CrUD operations from console.
The program starts with the following set of parameters:
-c productName price quantity; [create]
-u id productName price quantity; [update]
-d id; [delete]
id - 8 characters
productName - 30 characters
price - 8 characters
quantity - 4 characters
*/

public class Solution {

    public static String fileName;
    //public static String fileName = "/Users/Vlad/Downloads/db.txt";//for testing

    public static void main(String[] args) throws IOException {

        System.out.println("Enter file name:");
        readFileName();//Read file name from console;

        if (args[0].equals("-c") && args[1] != null && args[2] != null && args[3] != null ){//Check -c parameters

            System.out.println("Create product is progress...");
            Product product = new Product (newID(), args[1], args[2], args[3]); //Create new product
            addProduct(product); //Writing to file
        }
        else if (args[0].equals("-u") && args[1] != null && args[2] != null && args[3] != null && args[4] != null){ //Check -u parameters

            System.out.println("Update product is progress...");
            updateProduct(readFileToArray(), args[1], args[2], args[3], args[4]);
        }
        else if (args[0].equals("-d") && args[1] != null){ //Check -u parameters

            System.out.println("Delete product is progress...");
            deleteProduct(readFileToArray(), args[1]);
        }
        else
            System.out.println("Error! Check parameters!");
    }

    private static void readFileName(){

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            fileName = reader.readLine();
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    private static void addProduct(Product obj){//Writing

        try (FileWriter fileWriter = new FileWriter(fileName, true)){
            String str = obj.getId() + obj.getName() + obj.getPrice() + obj.getQuantity() + System.lineSeparator();
            fileWriter.write(str);
            System.out.println("New product added!");
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    private static String newID(){//Create new ID or increase old one;

        String id = "1";
        String line = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            while (reader.ready()){
                line = reader.readLine();
            }
        }catch (Exception exp){
            exp.printStackTrace();
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
        id = addSpaces(id ,Product.NUMBER_CHAR_ID);

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

    private static ArrayList<String> readFileToArray(){
        ArrayList<String> linesFromFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            while (reader.ready()){
                linesFromFile.add(reader.readLine());
            }
        }
        catch (Exception exp){
            exp.printStackTrace();
        }

        return linesFromFile;
    }

    private static void updateProduct(ArrayList<String> linesFromFile, String id, String name, String price, String quantity){

        String str = addSpaces(id, Product.NUMBER_CHAR_ID) + addSpaces(name, Product.NUMBER_CHAR_NAME) +
                     addSpaces(price, Product.NUMBER_CHAR_PRICE) + addSpaces(quantity, Product.NUMBER_CHAR_QUANTITY);
        boolean isIDFind = false;

        for(String line : linesFromFile) {
            Matcher matcher = Pattern.compile("^.{8}").matcher(line); //Cut ID
            if (matcher.find()) {
                String checkedID = matcher.group().trim();
                if (checkedID.equals(id)){
                    System.out.println("ID find!");
                    int index = linesFromFile.indexOf(line); //Update string
                    linesFromFile.set(index, str);
                    isIDFind = true;
                }
            }
        }
        if (isIDFind){
            try (FileWriter fileWriter = new FileWriter(fileName)){ //Update file
                for(String line : linesFromFile) {
                    fileWriter.write(line + "\n");
                }
                System.out.println("Product updated!");
            }catch (Exception exp){
                exp.printStackTrace();
            }
        }
        else
            System.out.println("Error, ID not find...");
    }

    private static void deleteProduct(ArrayList<String> linesFromFile, String id){

        boolean isIDFind = false;

        for(String line : linesFromFile) {
            Matcher matcher = Pattern.compile("^.{8}").matcher(line); //Cut ID
            if (matcher.find()) {
                String checkedID = matcher.group().trim();
                if (checkedID.equals(id)) {
                    System.out.println("ID find!");
                    linesFromFile.remove(line);//Delete string
                    isIDFind = true;
                    break;
                }
            }
            else System.out.println("Error, ID not find...");
        }
        if (isIDFind){
            try (FileWriter fileWriter = new FileWriter(fileName)){ //Update file
                for(String line : linesFromFile) {
                    fileWriter.write(line + "\n");
                }
                System.out.println("Product deleted!");
            }catch (Exception exp){
                exp.printStackTrace();
            }
        }
        else
            System.out.println("Error, ID not find...");
    }

}