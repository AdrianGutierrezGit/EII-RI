package uo.ri.cws.application.service.spare.supplies.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supplies.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindSupplyBySparePartCode implements Command<List<SupplyDto>> {

    private String code;
    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SupplyGateway sp = Factories.persistence.forSuppply();
    private ProviderGateway pg = Factories.persistence.forProvider();

    public FindSupplyBySparePartCode(String code) {
	ArgumentChecks.isNotBlank(code);
	this.code = code;
    }

    @Override
    public List<SupplyDto> execute() throws BusinessException {
	Optional<SparePartRecord> optionalSparePart = spg.findByCode(code);
	if (optionalSparePart.isEmpty()) {
	    List<SupplyDto> lista = new ArrayList<>();
	    return lista;
	}
	SparePartRecord sparePart = optionalSparePart.get();

	List<SupplyRecord> listaRecord = sp.findBySparePartId(sparePart.id);

	BusinessChecks.isTrue(listaRecord.size() > 0);
	asignaSparePart(listaRecord, sparePart);
	asignaProvider(listaRecord);
	List<SupplyDto> lista = DtoAssembler.toSupplyDtoList(listaRecord);

	return lista;
    }

    private void asignaSparePart(List<SupplyRecord> lista,
	    SparePartRecord sparePart) {
	for (SupplyRecord r : lista) {
	    r.sparePart = DtoAssembler.toSuppliedSparPartRecord(sparePart);
	}
    }

    private void asignaProvider(List<SupplyRecord> lista)
	    throws BusinessException {
	for (SupplyRecord r : lista) {
	    Optional<ProviderRecord> optionalProvider = pg
		    .findById(r.provider.id);
	    BusinessChecks.exists(optionalProvider);
	    ProviderRecord provider = optionalProvider.get();
	    r.provider = DtoAssembler.toSupplierProviderRecord(provider);
	}
    }
}
