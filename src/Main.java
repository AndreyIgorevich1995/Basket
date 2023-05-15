import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        File basketForJson = new File("basket.json");
        basketForJson.createNewFile();

        XMLSettingsReader settings = new XMLSettingsReader(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);

        Basket myBasket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog clientLog = new ClientLog("Vasiliy", new Basket(), new ArrayList<>(), new ArrayList<>());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Список возможных товаров для покупки");
            for (int i = 1; i < myBasket.getProducts().length + 1; i++) {
                System.out.println(i + ". " + myBasket.getProducts()[i - 1] + " " + myBasket.getPrice()[i - 1] + " руб/шт");
            }
            System.out.println("Выберите товар и количество или введите `end`");

            String input = scanner.nextLine();
            if (input.equals("end")) {
                if (settings.isLog) {
                    clientLog.exportAsCSV(logFile);
                }
                break;
            }
            String[] purchases = input.split(" ");


            clientLog.log(Integer.parseInt(purchases[0]), Integer.parseInt(purchases[1]));

            int selectedProduct = Integer.parseInt(purchases[0]);  //выбранный продукт
            int numberProducts = Integer.parseInt(purchases[1]);//выбранное количество продукта
            myBasket.addToCart(selectedProduct, numberProducts);
            if (settings.isLog) {
                clientLog.log(selectedProduct, numberProducts);
            }
            if (settings.isSave) {
                switch (settings.saveFormat) {
                    case "json" -> myBasket.saveJson(saveFile);
                    case "txt" -> myBasket.saveTxt(saveFile);
                }
            }
        }
        clientLog.exportAsCSV(new File("log"));
        myBasket.printCart();
        myBasket.saveJson(basketForJson);
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) throws IOException {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            switch (loadFormat) {
                case "json":
                    basket = Basket.loadFromJson(loadFile);
                    break;
                case "txt":
                    basket = Basket.loadFromTxtFile(loadFile);
                    break;
                default:
                    basket = new Basket();
            }
        } else {
            basket = new Basket();
        }
        return basket;
    }
}