package uo.ri.cws.application.service.invoice.create.command;

import java.time.LocalDate;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderIds;
    private WorkOrderRepository wor = Factories.repository.forWorkOrder();
    private InvoiceRepository ir = Factories.repository.forInvoice();

    public CreateInvoiceFor(List<String> workOrderIds) {
        ArgumentChecks.isNotNull(workOrderIds);
        ArgumentChecks.isFalse(workOrderIds.isEmpty());
        ArgumentChecks.isFalse(workOrderIds.stream()
                                           .anyMatch(i -> i == null));

        this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
        List<WorkOrder> workOrders = wor.findByIds(workOrderIds);
        checkWorkOrders(workOrders);
        Invoice i = new Invoice(ir.getNextInvoiceNumber(), LocalDate.now(),
                workOrders);

        ir.add(i);
        return DtoAssembler.toDto(i);

    }

    private void checkWorkOrders(List<WorkOrder> workOrders)
            throws BusinessException {
        BusinessChecks.isTrue(workOrders.size() == workOrderIds.size(),
                "There is a work order that does not exits");
        BusinessChecks.isFalse(workOrders.stream()
                                         .allMatch(wo -> wo.getState()
                                                           .equals(WorkOrderState.ASSIGNED)),
                "There is a work order that is assigned");
        BusinessChecks.isFalse(workOrders.stream()
                                         .allMatch(wo -> wo.getState()
                                                           .equals(WorkOrderState.OPEN)),
                "There is a work order that is open");
        BusinessChecks.isFalse(workOrders.stream()
                                         .allMatch(wo -> wo.getState()
                                                           .equals(WorkOrderState.INVOICED)),
                "There is a work order that is invoiced");
        BusinessChecks.isTrue(workOrders.stream()
                                        .allMatch(wo -> wo.getState()
                                                          .equals(WorkOrderState.FINISHED)),
                "There is a work order that is not finished");
    }

}
