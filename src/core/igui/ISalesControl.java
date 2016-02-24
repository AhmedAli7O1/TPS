package core.igui;


import core.Item;
import core.Order;
import core.DataViewStyle;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

public interface ISalesControl {
    void addOrder(Order order);
    boolean addNewOrders()throws WSConnException, NoDataException;
    List<Item> getItems(LocalDate date, DataViewStyle style)throws WSConnException, NoDataException;
    LocalDate getItemDate(Item item);
    List<Item> searchItems(String searchFor) throws WSConnException, NoDataException;
}
