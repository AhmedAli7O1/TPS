package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IOutgoingsControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OutgoingsControl extends CoreMain implements IOutgoingsControl {
    List<Outgoing> outgoings;

    public OutgoingsControl(){
        outgoings = new ArrayList<>();
    }

    @Override
    public List<Outgoing> getOutgoings(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        outgoings.clear();
        outgoings.addAll(outgoingsData.getOutgoings(date, style));
        return this.outgoings;
    }
}
