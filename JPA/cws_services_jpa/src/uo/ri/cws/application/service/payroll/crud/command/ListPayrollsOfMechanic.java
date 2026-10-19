package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsOfMechanic
        implements Command<List<PayrollSummaryDto>> {

    private String mechanicId;
    private MechanicRepository mr = Factories.repository.forMechanic();
    private PayrollRepository pr = Factories.repository.forPayroll();

    public ListPayrollsOfMechanic(String id) {
        ArgumentChecks.isNotNull(id, "The argument is null");
        ArgumentChecks.isNotBlank(id, "The argument is blank");
        ArgumentChecks.isNotEmpty(id, "The argument is empty");
        this.mechanicId = id;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        BusinessChecks.exists(mr.findById(mechanicId),
                "The mechanic with id " + mechanicId + " does not exits");
        return DtoAssembler.toSummaryDtoList(pr.findByMechanicId(mechanicId));
    }

}
