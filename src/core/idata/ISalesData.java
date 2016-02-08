package core.idata;


import core.Order;
import core.DataViewStyle;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

public interface ISalesData {

    List<Order> getOrders(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
    boolean addNewOrder(Order order) throws WSConnException, NoDataException;
}
