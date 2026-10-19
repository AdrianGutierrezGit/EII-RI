package uo.ri.cws.application.service.invoice.create.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>> {

    private long number;

    public FindInvoiceByNumber(Long number) {
	this.number = number;
    }

    @Override
    public Optional<InvoiceDto> execute() throws BusinessException {

	BusinessChecks.isNotNull(number, "El numero introducido no es válido");
	Optional<InvoiceRecord> r = Factories.persistence.forInvoice()
		.findByNumber(number);
	BusinessChecks.isNotNull(r);
	return r.map(DtoAssembler::toDto);
    }

}
