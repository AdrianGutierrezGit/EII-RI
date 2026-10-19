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
import uo.ri.cws.application.persistence.supply.SupplyGateway.SupplyRecord.SuppliedSparePartRecord;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.spare.supplies.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindByProviderNif implements Command<List<SupplyDto>> {

    private SparePartGateway spg = Factories.persistence.forSparePart();
    private SupplyGateway sp = Factories.persistence.forSuppply();
    private ProviderGateway pg = Factories.persistence.forProvider();

    private String nif;

    public FindByProviderNif(String nif) {
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    @Override
    public List<SupplyDto> execute() throws BusinessException {
	Optional<ProviderRecord> optionalProviderRecord = pg
		.findProviderByNif(nif);
	if (optionalProviderRecord.isEmpty()) {
	    List<SupplyDto> lista = new ArrayList<>();
	    return lista;
	}
	ProviderRecord provider = optionalProviderRecord.get();

	List<SupplyRecord> listaRecord = sp.findByProviderId(provider.id);

	BusinessChecks.isTrue(listaRecord.size() > 0);
	asignaSparePart(listaRecord);
	asignaProvider(listaRecord);
	List<SupplyDto> lista = DtoAssembler.toSupplyDtoList(listaRecord);

	return lista;
    }

    private void asignaSparePart(List<SupplyRecord> lista)
	    throws BusinessException {
	for (SupplyRecord r : lista) {
	    Optional<SparePartRecord> optionalSparePart = spg
		    .findById(r.sparePart.id);
	    BusinessChecks.exists(optionalSparePart);
	    SparePartRecord sparePart = optionalSparePart.get();

	    SuppliedSparePartRecord suplied = DtoAssembler
		    .toSuppliedSparPartRecord(sparePart);
	    r.sparePart = suplied;
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
