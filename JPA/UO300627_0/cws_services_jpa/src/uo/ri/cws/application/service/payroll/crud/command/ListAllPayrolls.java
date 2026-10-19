package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryDto;
import uo.ri.cws.application.service.payroll.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class ListAllPayrolls implements Command<List<PayrollSummaryDto>> {

    private PayrollRepository pr = Factories.repository.forPayroll();

    @Override
    public List<PayrollSummaryDto> execute() throws BusinessException {
        return DtoAssembler.toSummaryDtoList(pr.findAll());
    }

}
