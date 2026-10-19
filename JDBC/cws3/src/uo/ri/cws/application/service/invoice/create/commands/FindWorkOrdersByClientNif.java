package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindWorkOrdersByClientNif
	implements Command<List<InvoicingWorkOrderDto>> {

    private String nif;

    public FindWorkOrdersByClientNif(String nif) {
	ArgumentChecks.isNotNull(nif);
	this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute() {
	WorkOrderGateway wog = new WorkOrderGatewayImpl();

	List<WorkOrderRecord> notInvoiced = wog.findNotInvoicedByClientNif(nif);

	List<InvoicingWorkOrderDto> r = DtoAssembler
		.toInvoicingWorkOrderList(notInvoiced);
	return r;

    }
}
