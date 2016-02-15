package core.igui;

import core.DataViewStyle;
import core.Income;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import java.time.LocalDate;
import java.util.List;

public interface IIncomesControl {
    List<Income> getIncomes(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
    void addIncomes(List<Income> incomes);
    boolean syncNewIncomes()throws WSConnException, NoDataException;
}
