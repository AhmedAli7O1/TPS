package core;

import core.idata.ISalesData;
import core.igui.ISalesControl;
import datalayer.SalesData;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesControl implements ISalesControl {
    private ISalesData salesData;
    private List<Order> orders;


    public SalesControl(){
        salesData = new SalesData();
    }


    private void getOrders(LocalDate date, SalesViewStyle style){
        orders= new ArrayList<>(salesData.getOrders(date, style));
    }

    @Override
    public List<Item> getItems(LocalDate date, SalesViewStyle style) {
        getOrders(date, style);
        List<Item> items = new ArrayList<>();
        for(Order order : orders){
            items.addAll(order.getItems());
        }
        return items;
    }
}
