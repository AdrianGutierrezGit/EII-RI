package uo.ri.cws.application.service;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.order.OrderServiceImpl;
import uo.ri.cws.application.service.provider.impl.ProvidersCrudServiceImpl;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.SparePartCrudService;

import uo.ri.cws.application.service.spare.SparePartReportService;

import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.supplies.SuppliesCrudServiceImpl;
import uo.ri.cws.application.service.sparepart.crud.SparePartCrudServiceImpl;
import uo.ri.cws.application.service.sparepartreport.SparePartReportServiceImpl;

public class ServiceFactory {

    public MechanicCrudService forMechanicService() {
	return new MechanicCrudServiceImpl();
    }

    public InvoicingService forInvoicingService() {
	return new InvoicingServiceImpl();
    }

    public OrdersService forOrdersService() {

	return new OrderServiceImpl();
    }

    public ProvidersCrudService forProvidersService() {

	return new ProvidersCrudServiceImpl();
    }

    public SuppliesCrudService forSuppliesCrudService() {

	return new SuppliesCrudServiceImpl();
    }

    public SparePartReportService forSparePartReportService() {
	return new SparePartReportServiceImpl();
    }

    public SparePartCrudService forSparePartCrudService() {
	return new SparePartCrudServiceImpl();
    }

}
