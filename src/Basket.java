import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private int sum;
    private int[] price;
    private String[] products;
    private int[] numbersProductsArray;  //массив количества выбраных продуктов
    private String[] selectedProductArray;  //массив выбраных продуктов
    private int[] selectedPrices;  //массив цен выбранных продуктов


    public Basket() {
        sum = 0;
        price = new int[]{200, 50, 75, 40, 15, 115, 55};
        products = new String[]{"Колбаса", "Хлеб", "Молоко", "Сахар", "Соль", "Консервы", "Шоколад"};
        numbersProductsArray = new int[products.length];
        selectedProductArray = new String[products.length];
        selectedPrices = new int[products.length];
    }


    public void addToCart(int productNum, int amount) {
        selectedProductArray[productNum - 1] = products[productNum - 1];
        selectedPrices[productNum - 1] = price[productNum - 1];
        numbersProductsArray[productNum - 1] = numbersProductsArray[productNum - 1] + amount;
    }


    public void saveBin(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void loadFromBinFile(File file) {
        Basket basket = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(basket);
    }


    public int[] getPrice() {
        return price;
    }

    public String[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "sum=" + sum +
                ", price=" + Arrays.toString(price) +
                ", products=" + Arrays.toString(products) +
                ", numbersProductsArray=" + Arrays.toString(numbersProductsArray) +
                ", selectedProductArray=" + Arrays.toString(selectedProductArray) +
                ", selectedPrices=" + Arrays.toString(selectedPrices) +
                '}';
    }
}
