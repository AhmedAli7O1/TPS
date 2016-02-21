package datalayer;

import core.Item;
import core.Order;
import core.DataViewStyle;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.ISalesData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesData implements ISalesData {

    @Override
    public List<Order> getOrders(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        // put params into a json object to send
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("date", date);
        jsonToSend.put("style", style);

        WebService webService = new WebService();

        JSONObject obj = webService.getJson("sales", "getOrders", jsonToSend);
        JSONArray jsonOrdersArray = obj.getJSONArray("Orders");
        JSONArray jsonItemsArray = obj.getJSONArray("Items");

        // parse Orders array from json to Orders List
        List<Order> orders = new ArrayList<>();
        for(int i = 0; i < jsonOrdersArray.length(); i++){
            Order order = new Order(
                    jsonOrdersArray.getJSONObject(i).getInt("ID"),
                    jsonOrdersArray.getJSONObject(i).getString("CUSTOMER"),
                    jsonOrdersArray.getJSONObject(i).getDouble("PRICE"),
                    jsonOrdersArray.getJSONObject(i).getDouble("PAID"),
                    LocalDate.parse(jsonOrdersArray.getJSONObject(i).getString("DATE")),
                    new ArrayList<>(),
                    jsonOrdersArray.getJSONObject(i).getInt("ACCOUNT_ID"));
            orders.add(order);
        }

        //loop through all items
        for(int i = 0; i < jsonItemsArray.length(); i++){
            //get item data
            int id = jsonItemsArray.getJSONObject(i).getInt("ID");
            String item = jsonItemsArray.getJSONObject(i).getString("ITEM_NAME");
            int amount = jsonItemsArray.getJSONObject(i).getInt("AMOUNT");
            double price = jsonItemsArray.getJSONObject(i).getDouble("PRICE");
            double paid = jsonItemsArray.getJSONObject(i).getDouble("PAID");
            double purchasesValue = jsonItemsArray.getJSONObject(i).getDouble("PURCHASES_VALUE");
            int orderId = jsonItemsArray.getJSONObject(i).getInt("ORDER_ID");
            //add this item to corresponding order
            orders.stream().filter(ord -> ord.getId() == orderId)
                    .findFirst().get().getItems().add(
                    new Item(
                            id, item, amount, price, paid, purchasesValue, orderId
                    )
            );
        }
        return orders; //return orders list
    }

    @Override
    public boolean addNewOrder(Order order)throws WSConnException, NoDataException{
        WebService webService = new WebService();
        JSONObject jsonObj = webService.getJson("sales", "addOrder", order);

        if(jsonObj.getBoolean("result")){
            return true;
        }
        else return false;
    }
}
