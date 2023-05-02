import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ClientLog clientLog = new ClientLog("Vasiliy", new Basket(), new ArrayList<>(), new ArrayList<>());
//        File basket = new File("basket.txt");
//        basket.createNewFile();

        File basketForJson = new File("basket.json");
        basketForJson.createNewFile();

        Scanner scanner = new Scanner(System.in);
        Basket myBasket = new Basket();
//        Basket myBasket = null;

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(basketForJson));
            JSONObject basketObject = (JSONObject) obj;


            JSONArray arrayProducts = (JSONArray) basketObject.get("products");
            for (Object o : arrayProducts) {
                System.out.println(o);
//                JSONObject arrayProducts1 = (JSONObject) o;
            }


            JSONArray arraySelectedPrices = (JSONArray) basketObject.get("selectedPrices");
            for (Object o : arraySelectedPrices) {
                System.out.println(o);
            }

            JSONArray arrayNumbersProductsArray = (JSONArray) basketObject.get("numbersProductsArray");
            for (Object o : arrayNumbersProductsArray) {
                System.out.println(o);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

//        JSONObject basketObject = new JSONObject();
//        if (basketForJson.exists()) {
//            GsonBuilder a = new GsonBuilder();
//            Gson gson = a.create();
//            System.out.println(gson.toJson(basketForJson));
//            myBasket = gson.fromJson(basketObject.toJSONString(), Basket.class);
//            System.out.println(myBasket.toString());
////            myBasket = Basket.loadFromTxtFile(basketForJson);
//        } else {
//            myBasket = new Basket();
//        }


//        if (basket.exists()) {
//            myBasket = Basket.loadFromTxtFile(basket);
//        } else {
//            myBasket = new Basket();
//        }

        while (true) {

            System.out.println("Список возможных товаров для покупки");
            for (int i = 1; i < myBasket.getProducts().length + 1; i++) {
                System.out.println(i + ". " + myBasket.getProducts()[i - 1] + " " + myBasket.getPrice()[i - 1] + " руб/шт");
            }
            System.out.println("Выберите товар и количество или введите `end`");

            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] purchases = input.split(" ");


            clientLog.log(Integer.parseInt(purchases[0]), Integer.parseInt(purchases[1]));

            int selectedProduct = Integer.parseInt(purchases[0]);  //выбранный продукт
            int numberProducts = Integer.parseInt(purchases[1]);//выбранное количество продукта
            myBasket.addToCart(selectedProduct, numberProducts);
        }
        clientLog.exportAsCSV(new File("log"));
        myBasket.printCart();
//        myBasket.saveTxt(basket);
        myBasket.saveTxtToJson(basketForJson);

//        Basket.loadFromTxtFile(basket);
//        System.out.println(Basket.loadFromTxtFile(basket));
    }
}