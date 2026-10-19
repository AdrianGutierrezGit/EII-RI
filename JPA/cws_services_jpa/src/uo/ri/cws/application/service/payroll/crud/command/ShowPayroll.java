package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class ShowPayroll implements Command<Optional<PayrollDto>> {

    private String payrollId;
    private PayrollRepository pr = Factories.repository.forPayroll();

    public ShowPayroll(String id) {
        ArgumentChecks.isNotNull(id, "The argument is null");
        ArgumentChecks.isNotBlank(id, "The argument is blank");
        ArgumentChecks.isNotEmpty(id, "The argument is empty");
        this.payrollId = id;
    }

    @Override
    public Optional<PayrollDto> execute() throws BusinessException {
        return DtoAssembler.toOptionalDto(pr.findById(payrollId));
    }

}
