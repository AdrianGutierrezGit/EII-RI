package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class ListPayrollsOfProfGroup
        implements Command<List<PayrollSummaryDto>> {

    private String name;
    private ProfessionalGroupRepository pgr =
                                            Factories.repository.forProfessionalGroup();
    private PayrollRepository pr = Factories.repository.forPayroll();

    public ListPayrollsOfProfGroup(String name) {
        ArgumentChecks.isNotNull(name, "The argument is null");
        ArgumentChecks.isNotBlank(name, "The argument is blank");
        ArgumentChecks.isNotEmpty(name, "The argument is empty");
        this.name = name;
    }

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        BusinessChecks.exists(pgr.findByName(name),
                "The professional group with name " + name + " does not exits");
        return DtoAssembler.toSummaryDtoList(
                pr.findByProfessionalGroupName(name));
    }

}
