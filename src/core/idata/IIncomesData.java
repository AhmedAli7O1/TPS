package core.idata;

import core.DataViewStyle;
import core.Income;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import java.time.LocalDate;
import java.util.List;

public interface IIncomesData {
    List<Income> getIncomes(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
}
