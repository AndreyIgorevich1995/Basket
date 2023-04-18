import java.io.*;
import java.util.Arrays;

public class Basket {
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

    public void printCart() {
        for (int i = 0; i < numbersProductsArray.length; i++) {
            sum = sum + price[i] * numbersProductsArray[i];
        }
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < selectedProductArray.length; i++) {
            if (selectedProductArray[i] != null) {
                System.out.println(selectedProductArray[i] + " " + numbersProductsArray[i] + " шт " + price[i] + " руб/шт " + numbersProductsArray[i] * price[i] + " руб в сумме");
            }
        }
        System.out.println("Итого " + sum + " руб");
    }

    public void saveTxt(File textFile) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(textFile))) {
            for (int i = 0; i < products.length; i++) {
                if (products[i] != null) {
                    bufferedWriter.write(products[i] + " ");
                }
            }
            bufferedWriter.newLine();
            for (int i = 0; i < selectedPrices.length; i++) {
                bufferedWriter.write(selectedPrices[i] + " ");
            }
            bufferedWriter.newLine();
            for (int i = 0; i < numbersProductsArray.length; i++) {
                bufferedWriter.write(numbersProductsArray[i] + " ");
            }

        }
    }

    static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String selectedProductArrayStr = bufferedReader.readLine();
            String priceStr = bufferedReader.readLine();
            String numbersProductsArrayStr = bufferedReader.readLine();

            basket.selectedProductArray = selectedProductArrayStr.split(" ");

            basket.selectedPrices = Arrays.stream(priceStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.numbersProductsArray = Arrays.stream(numbersProductsArrayStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
        } catch (IOException a) {
            throw new RuntimeException(a);
        }
        return basket;
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

    public int getSum() {
        return sum;
    }

    public int[] getNumbersProductsArray() {
        return numbersProductsArray;
    }

    public String[] getSelectedProductArray() {
        return selectedProductArray;
    }

    public int[] getSelectedPrices() {
        return selectedPrices;
    }
}