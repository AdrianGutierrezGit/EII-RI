package uo.ri.cws.application.service.provider.impl.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

import uo.ri.cws.application.service.provider.impl.DtoAssembler;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class Update implements Command<Void> {

    private ProviderDto dto;
    private ProviderGateway pg = Factories.persistence.forProvider();

    public Update(ProviderDto dto) throws BusinessException {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotBlank(dto.email);
	ArgumentChecks.isNotBlank(dto.id);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isNotBlank(dto.nif);
	ArgumentChecks.isNotBlank(dto.phone);
	ArgumentChecks.isNotNull(dto.version);
	ArgumentChecks.isTrue(dto.email.contains("@"));
	BusinessChecks.isTrue(dto.version >= 0);
	this.dto = dto;
    }

    @Override
    public Void execute() throws BusinessException {

	Optional<ProviderRecord> optional = pg.findProviderByNif(dto.nif);
	BusinessChecks.exists(optional);
	ProviderRecord record = DtoAssembler.toOrderedProviderRecord(dto);
	List<ProviderRecord> list = pg.findProviderByEmailPhoneName(record);

	ProviderRecord recordUpdated = DtoAssembler
		.toOrderedProviderRecord(dto);
	BusinessChecks.isTrue(list.size() == 0);
	pg.update(recordUpdated);
	return null;
    }

}
