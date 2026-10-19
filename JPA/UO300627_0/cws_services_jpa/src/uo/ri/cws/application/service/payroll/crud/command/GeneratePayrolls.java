package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class GeneratePayrolls implements Command<List<PayrollDto>> {

    private LocalDate date;
    private ContractRepository cr = Factories.repository.forContract();
    private PayrollRepository pr = Factories.repository.forPayroll();

    public GeneratePayrolls(LocalDate present) {
        ArgumentChecks.isNotNull(present, "The date cannot be null");
        this.date = present;
    }

    @Override
    public List<PayrollDto> execute() throws BusinessException {
        List<PayrollDto> payrolls = new ArrayList<>();
        if (!hasPayrollsGeneratedThatMonth()) {
            return payrolls;
        }
        for (Contract c : cr.findAllInForceThisMonth(date)) {
            Payroll p = new Payroll(c, calculateLastDay());
            pr.add(p);
            payrolls.add(DtoAssembler.toDto(p));
        }
        return payrolls;

    }

    private boolean hasPayrollsGeneratedThatMonth() {
        return pr.findLastMonthPayrolls()
                 .isEmpty();
    }

    private LocalDate calculateLastDay() {
        LocalDate previusMonth = date.minusMonths(1);
        return previusMonth.withDayOfMonth(previusMonth.lengthOfMonth());
    }

}
