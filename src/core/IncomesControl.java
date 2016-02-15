package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IIncomesControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class IncomesControl extends CoreMain implements IIncomesControl {
    List<Income> incomes;
    List<Income> newIncomes;

    public IncomesControl(){
        incomes = new ArrayList<>();
        newIncomes = new ArrayList<>();
    }

    @Override
    public List<Income> getIncomes(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        incomes.clear();
        incomes.addAll(incomesData.getIncomes(date, style));
        return this.incomes;
    }

    @Override
    public void addIncomes(List<Income> incomes){
        newIncomes.addAll(incomes);
    }

    @Override
    public boolean syncNewIncomes()throws WSConnException, NoDataException {
        if(incomesData.addNewIncomes(newIncomes)){
            newIncomes.clear();
            return true;
        }
        else return false;
    }
}
