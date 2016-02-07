package core.igui;


import core.Item;
import core.Order;
import core.SalesViewStyle;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.List;

public interface ISalesControl {
    void addOrder(Order order);
    boolean addNewOrders();
    List<Item> getItems(LocalDate date, SalesViewStyle style);
}
