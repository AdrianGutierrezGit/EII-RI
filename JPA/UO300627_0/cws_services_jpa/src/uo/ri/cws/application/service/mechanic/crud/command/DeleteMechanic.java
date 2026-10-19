package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;
    private MechanicRepository mr = Factories.repository.forMechanic();

    public DeleteMechanic(String mechanicId) {
        ArgumentChecks.isNotNull(mechanicId);
        this.mechanicId = mechanicId;
    }

    public Void execute() throws BusinessException {
        Mechanic m = checkExits(mechanicId);
        checkCanBeDeleted(m);

        mr.remove(m);
        return null;
    }

    private void checkCanBeDeleted(Mechanic m) throws BusinessException {
        BusinessChecks.isTrue(m.getAssigned()
                               .isEmpty(),
                "Mechanic has work orders");
        BusinessChecks.isTrue(m.getInterventions()
                               .isEmpty(),
                "Mechanic has interventions");
        BusinessChecks.isTrue(m.getContracts()
                               .isEmpty(),
                "Mechanic has a contract not terminated");

    }

    private Mechanic checkExits(String mechanicId) throws BusinessException {

        Optional<Mechanic> o = mr.findById(mechanicId);

        BusinessChecks.exists(o, "Mechanic does not exist");
        return o.get();

    }

}
