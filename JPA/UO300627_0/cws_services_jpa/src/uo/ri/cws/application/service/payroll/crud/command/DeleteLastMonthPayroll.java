package uo.ri.cws.application.service.payroll.crud.command;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayroll implements Command<Integer> {

    private PayrollRepository pr = Factories.repository.forPayroll();

    @Override
    public Integer execute() throws BusinessException {
        int numberOfPayrollsDeleted = 0;
        for (Payroll p : pr.findLastMonthPayrolls()) {
            pr.remove(p);
            numberOfPayrollsDeleted++;
        }
        return numberOfPayrollsDeleted;
    }

}
