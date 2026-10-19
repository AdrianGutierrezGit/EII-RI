package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

    private String id;

    public DeleteMechanic(String idMechanic) {
	ArgumentChecks.isNotBlank(idMechanic);
	this.id = idMechanic;
    }

    public Void execute() throws BusinessException {

	MechanicGateway mg = Factories.persistence.forMechanic();
	WorkOrderGateway wog = Factories.persistence.forWorkOrder();
	InterventionGateway ig = Factories.persistence.forIntervention();

	List<InterventionRecord> interventions = ig.findByMechanicId(id);

	BusinessChecks.isTrue(interventions.isEmpty());

	List<WorkOrderRecord> workOrders = wog.findByMechanicId(id);

	BusinessChecks.isTrue(workOrders.isEmpty());

	Optional<MechanicRecord> om = mg.findById(id);
	BusinessChecks.exists(om);

	mg.remove(id);
	return null;
    }

}
