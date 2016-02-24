package core.idata;


import core.Item;
import core.Order;
import core.DataViewStyle;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

public interface ISalesData {

    List<Order> getOrders(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
    boolean addNewOrder(Order order) throws WSConnException, NoDataException;
    List<Item> searchItems(String searchFor) throws WSConnException, NoDataException;
}
