package core.idata;


import core.Order;
import core.SalesViewStyle;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.List;

public interface ISalesData {

    List<Order> getOrders(LocalDate date, SalesViewStyle style);
}
