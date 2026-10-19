	package uo.ri.cws.application.service.provider.impl.commands;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

import uo.ri.cws.application.service.provider.impl.DtoAssembler;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProvider implements Command<ProviderDto> {
    private ProviderDto dto;
    private ProviderGateway pg = Factories.persistence.forProvider();

    public AddProvider(ProviderDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.email);
	ArgumentChecks.isNotBlank(dto.id);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.phone);
	ArgumentChecks.isNotNull(dto.version);
	ArgumentChecks.isTrue(dto.email.contains("@"));
	this.dto = dto;
    }

    @Override
    public ProviderDto execute() throws BusinessException {
	ProviderRecord record = DtoAssembler.toOrderedProviderRecord(dto);
	Optional<ProviderRecord> optionalByNif = pg
		.findProviderByNif(record.nif);
	Optional<ProviderRecord> optionalById = pg.findById(record.id);

	List<ProviderRecord> lista = pg.findProviderByEmailPhoneName(record);
	BusinessChecks.isTrue(lista.size() == 0 || lista == null);
	BusinessChecks.doesNotExist(optionalByNif,
		"There is a provider withe same nif");
	BusinessChecks.doesNotExist(optionalById,
		"There is a provider withe same id");
	if (record.id == null)
	    record.id = UUID.randomUUID().toString();
	record.version = 1L;
	pg.add(record);

	return DtoAssembler.toOrderedProviderDto(record);
    }

}
