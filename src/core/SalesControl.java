package core;

import core.idata.ISalesData;
import core.igui.ISalesControl;
import datalayer.SalesData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SalesControl implements ISalesControl {
    private ISalesData salesData;
    private List<Order> orders;
    private Queue<Order> newOrdersQueue;

    public SalesControl(){

        newOrdersQueue = new PriorityQueue<>();

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

    @Override
    public void addOrder(Order order) {
        newOrdersQueue.offer(order);
    }

    @Override
    public boolean addNewOrders(){
        for (int i = 0; i < newOrdersQueue.size(); i++) {
            salesData.addNewOrder(newOrdersQueue.poll());
        }

        if(newOrdersQueue.size() == 0)
            return true;
        else
            return false;
    }
}
