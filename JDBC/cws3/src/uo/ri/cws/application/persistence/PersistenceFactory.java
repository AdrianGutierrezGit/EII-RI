package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.impl.InterventionGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.impl.OrderGatewayImpl;
import uo.ri.cws.application.persistence.orderLine.OrderLineGateway;
import uo.ri.cws.application.persistence.orderLine.impl.OrderLineGatewayImpl;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.impl.ProviderGatewayImpl;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.impl.SparePartGatewayImpl;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.impl.SupplyGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;

public class PersistenceFactory {

    public MechanicGateway forMechanic() {
	return new MechanicGatewayImpl();
    }

    public InvoiceGateway forInvoice() {
	return new InvoiceGatewayImpl();
    }

    public WorkOrderGateway forWorkOrder() {
	return new WorkOrderGatewayImpl();
    }

    public OrderGateway forOrder() {
	return new OrderGatewayImpl();
    }

    public InterventionGateway forIntervention() {
	return new InterventionGatewayImpl();
    }

    public OrderLineGateway forOrderLine() {
	return new OrderLineGatewayImpl();
    }

    public SparePartGateway forSparePart() {
	return new SparePartGatewayImpl();
    }

    public ProviderGateway forProvider() {
	return new ProviderGatewayImpl();
    }

    public SupplyGateway forSuppply() {
	return new SupplyGatewayImpl();
    }
}
