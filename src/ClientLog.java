import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;

public class ClientLog {

    private String Client;
    private Basket basket;
    private ArrayList<Integer> selectedProductList;  //список выбраных продуктов
    private ArrayList<Integer> numbersProductsList;  //список количества выбраных продуктов


    public ClientLog(String client, Basket basket, ArrayList<Integer> selectedProductList, ArrayList<Integer> numbersProductsList) {
        Client = client;
        this.basket = basket;
        this.selectedProductList = selectedProductList;
        this.numbersProductsList = numbersProductsList;
    }

    public void log(int productNum, int amount) {
        selectedProductList.add(productNum);
        numbersProductsList.add(amount);
    }

    public void exportAsCSV(File txtFile) throws IOException {
        if (!txtFile.exists()) {
            File log = new File("log.csv");
            log.createNewFile();
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("log.csv"))) {
            StringJoiner e = new StringJoiner(",")
                    .add("productNum")
                    .add("amount");
            String[] b = e.toString().split(",");
            writer.writeNext(b);

            StringJoiner f = new StringJoiner(",");

            for (int i = 0; i < selectedProductList.size(); i++) {
                f.add(Integer.toString(selectedProductList.get(i)))
                        .add(Integer.toString(numbersProductsList.get(i)))
                        .add("\n");
            }
            String[] y = f.toString().split(",");
            writer.writeNext(y);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ClientLog{" +
                "Client='" + Client + '\'' +
                ", basket=" + basket +
                ", selectedProductList=" + selectedProductList +
                ", numbersProductsList=" + numbersProductsList +
                '}';
    }

    public String getClient() {
        return Client;
    }

    public Basket getBasket() {
        return basket;
    }

    public ArrayList<Integer> getSelectedProductList() {
        return selectedProductList;
    }

    public ArrayList<Integer> getNumbersProductsList() {
        return numbersProductsList;
    }

}

