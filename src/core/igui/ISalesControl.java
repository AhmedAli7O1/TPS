package core.igui;


import core.Item;
import core.SalesViewStyle;
import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.List;

public interface ISalesControl {
    List<Item> getItems(LocalDate date, SalesViewStyle style);
}
