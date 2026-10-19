package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteLastMonthPayrollOfMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository mr = Factories.repository.forMechanic();
    private PayrollRepository pr = Factories.repository.forPayroll();

    public DeleteLastMonthPayrollOfMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId, "The argument is null");
        ArgumentChecks.isNotBlank(mechanicId, "The argument is blank");
        ArgumentChecks.isNotEmpty(mechanicId, "The argument is empty");
        this.mechanicId = mechanicId;
    }

    @Override
    public Void execute() throws BusinessException {
        BusinessChecks.exists(mr.findById(mechanicId),
                "Mechanic does not exits");
        Optional<Payroll> p = pr.findLastPayrollByMechanicId(mechanicId);
        if (p.isPresent()) {
            pr.remove(p.get());
        }

        return null;
    }

}
