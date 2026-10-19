package uo.ri.cws.application.service.provider.impl.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

import uo.ri.cws.application.service.provider.impl.DtoAssembler;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByNif implements Command<Optional<ProviderDto>> {

    private String nif;

    private ProviderGateway pg = Factories.persistence.forProvider();

    public FindByNif(String nif) {
	ArgumentChecks.isNotBlank(nif);
	this.nif = nif;
    }

    @Override
    public Optional<ProviderDto> execute() throws BusinessException {
	Optional<ProviderRecord> optional = pg.findProviderByNif(nif);
	if (optional.isEmpty())
	    return Optional.empty();
	ProviderRecord record = optional.get();

	ProviderDto dto = DtoAssembler.toOrderedProviderDto(record);

	return Optional.of(dto);

    }

}
