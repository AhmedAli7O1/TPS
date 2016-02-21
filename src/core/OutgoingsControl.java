package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IOutgoingsControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class OutgoingsControl extends CoreMain implements IOutgoingsControl {
    List<Outgoing> outgoings;
    List<Outgoing> newOutgoings;

    public OutgoingsControl(){
        outgoings = new ArrayList<>();
        newOutgoings = new ArrayList<>();
    }

    @Override
    public List<Outgoing> getOutgoings(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        outgoings.clear();
        outgoings.addAll(outgoingsData.getOutgoings(date, style));
        return this.outgoings;
    }

    @Override
    public void addOutgoings(List<Outgoing> newOutgoings){
        this.newOutgoings.addAll(newOutgoings);
    }

    @Override
    public boolean saveChanges() throws WSConnException, NoDataException {
        if(newOutgoings.size() < 1)
            return true;

        if(outgoingsData.addNewOutgoings(newOutgoings)){
            newOutgoings.clear();
            return true;
        }
        else
            return false;

    }


}
