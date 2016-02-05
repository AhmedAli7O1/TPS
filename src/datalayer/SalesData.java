package datalayer;

import core.Item;
import core.Order;
import core.SalesViewStyle;
import core.idata.ISalesData;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesData implements ISalesData {

    @Override
    public List<Order> getOrders(LocalDate date, SalesViewStyle style) {
        JSONObject obj = new JSONObject(WebService.getJson("sales", "getOrders", "date=" + date, "style=" + style));
        JSONArray jsonOrdersArray = obj.getJSONArray("Orders");
        JSONArray jsonItemsArray = obj.getJSONArray("Items");

        // parse Orders array from json to Orders List
        List<Order> orders = new ArrayList<>();
        for(int i = 0; i < jsonOrdersArray.length(); i++){
            Order order = new Order(
                    jsonOrdersArray.getJSONObject(i).getInt("ID"),
                    jsonOrdersArray.getJSONObject(i).getString("Customer"),
                    jsonOrdersArray.getJSONObject(i).getDouble("Discount"),
                    jsonOrdersArray.getJSONObject(i).getDouble("TotalPrice"),
                    jsonOrdersArray.getJSONObject(i).getDouble("Paid"),
                    DateTime.parse(jsonOrdersArray.getJSONObject(i).getString("Date")),
                    new ArrayList<>());
            orders.add(order);
        }

        // parse Items array from json into HashMap
        HashMap<Integer, List<Item>> itemsMap = new HashMap<>();   // items list with OrderID as a Key
        for(int i = 0; i < jsonItemsArray.length(); i++){

            int id = jsonItemsArray.getJSONObject(i).getInt("ID");
            String name = jsonItemsArray.getJSONObject(i).getString("Name");
            int amount = jsonItemsArray.getJSONObject(i).getInt("Amount");
            double price = jsonItemsArray.getJSONObject(i).getDouble("Price");
            double discount = jsonItemsArray.getJSONObject(i).getDouble("Discount");
            double soldPrice = jsonItemsArray.getJSONObject(i).getDouble("SoldPrice");
            double purchaseValue = jsonItemsArray.getJSONObject(i).getDouble("PurchasesValue");
            double paid = jsonItemsArray.getJSONObject(i).getDouble("Paid");
            LocalDate soldDate = LocalDate.parse(jsonItemsArray.getJSONObject(i).getString("Date"));
            int orderID = jsonItemsArray.getJSONObject(i).getInt("OrderID");

            // create item key if doesn't exist
            if(!itemsMap.containsKey(orderID)){
                itemsMap.put(orderID, new ArrayList<>());
            }

            itemsMap.get(orderID).add(new Item(id, name, amount, price, discount, soldPrice, paid, purchaseValue, soldDate));
        }

        // add all items to Orders
        for(int i = 0; i < orders.size(); i++){
            orders.get(i).setItems(itemsMap.get(orders.get(i).getId()));
        }

        return orders;
    }
}
