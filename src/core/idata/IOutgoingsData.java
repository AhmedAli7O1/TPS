package core.idata;

import core.DataViewStyle;
import core.Outgoing;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;


public interface IOutgoingsData {
    List<Outgoing> getOutgoings(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
    boolean addOutgoings(List<Outgoing> outgoings) throws WSConnException, NoDataException;
}
