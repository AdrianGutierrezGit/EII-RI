package uo.ri.cws.application.service.invoice.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.math.Round;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderId;
    private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
    private InvoiceGateway ig = Factories.persistence.forInvoice();

    public CreateInvoiceFor(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);
	ArgumentChecks.isFalse(workOrderIds.isEmpty());
	ArgumentChecks.isFalse(workOrderIds.contains(null));
	this.workOrderId = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
	List<WorkOrderRecord> workOrders = wog.findByIds(workOrderId);
	check(workOrders);
	InvoiceDto dto = new InvoiceDto();
	dto.id = UUID.randomUUID().toString();
	dto.date = LocalDate.now();
	dto.number = ig.getNextInvoiceNumber();
	double amount = calculateTotalInvoice(workOrders);
	double iva = vatPercentage(amount, dto.date);
	double total = amount * (1 + (iva / 100d));
	dto.amount = total;
	dto.version = 1L;
	dto.vat = Round.twoCents(dto.amount * 0.21 / 1.21);
	dto.state = "NOT_YET_PAID";

	InvoiceRecord r = DtoAssembler.toRecord(dto);
	ig.add(r);
	setInvoiceIdToWorkOrders(workOrders, dto.id);
	changeWorkOrdersToInvoicedAndUpdate(workOrders);
	updateWorkOrders(workOrders);
	return dto;
    }

    private void setInvoiceIdToWorkOrders(List<WorkOrderRecord> workOrders,
	    String invoiceId) {
	for (WorkOrderRecord workOrder : workOrders) {
	    workOrder.invoiceId = invoiceId;
	}
    }

    private void updateWorkOrders(List<WorkOrderRecord> workOrders) {
	for (WorkOrderRecord workOrder : workOrders) {
	    wog.update(workOrder);
	}
    }

    private void changeWorkOrdersToInvoicedAndUpdate(
	    List<WorkOrderRecord> workOrders) {
	for (WorkOrderRecord workOrder : workOrders) {
	    workOrder.state = "INVOICED";

	}
    }

    private double vatPercentage(double amount, LocalDate date) {
	return LocalDate.parse("2012-07-01").isBefore(date) ? 21.0 : 18.0;
    }

    private double calculateTotalInvoice(List<WorkOrderRecord> workOrders) {
	double totalInvoice = 0.0;
	for (WorkOrderRecord workOrder : workOrders) {
	    totalInvoice += workOrder.amount;
	}
	return totalInvoice;
    }

    private void check(List<WorkOrderRecord> lista) throws BusinessException {
	BusinessChecks.isNotNull(lista,
		"There was a problem searching the work orders");
	BusinessChecks.isFalse(lista.contains(null),
		"There was a problem searching the work orders");

	List<WorkOrderRecord> workOrdersAsignados = wog.findByState("FINISHED");
	List<String> idsAsignados = new ArrayList<String>();
	for (WorkOrderRecord order : workOrdersAsignados) {
	    idsAsignados.add(order.id);
	}
	for (WorkOrderRecord order : lista) {
	    BusinessChecks.isTrue(idsAsignados.contains(order.id));
	}

    }

}