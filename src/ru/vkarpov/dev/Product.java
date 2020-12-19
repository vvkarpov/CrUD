package ru.vkarpov.dev;

public class Product{

    private String id;
    private String name;
    private String price;
    private String quantity;
    public static final int NUMBER_CHAR_ID = 8;
    public static final int NUMBER_CHAR_NAME = 30;
    public static final int NUMBER_CHAR_PRICE = 8;
    public static final int NUMBER_CHAR_QUANTITY = 4;

    public Product(String id, String name, String price, String quantity){

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        formatProductName();
        formatPrice();
        formatQuantity();
    }

    private void formatProductName(){

        name = Solution.addSpaces(name, NUMBER_CHAR_NAME);
    }

    private void formatPrice(){

        price = Solution.addSpaces(price, NUMBER_CHAR_PRICE);
    }

    private void formatQuantity(){

        quantity = Solution.addSpaces(quantity, NUMBER_CHAR_QUANTITY);
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getPrice() {

        return price;
    }

    public String getQuantity() {

        return quantity;
    }

}