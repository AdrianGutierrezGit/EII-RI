package uo.ri.cws.application.service.provider.impl.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

import uo.ri.cws.application.service.provider.impl.DtoAssembler;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindByName implements Command<List<ProviderDto>> {

    private String name;

    private ProviderGateway pg = Factories.persistence.forProvider();

    public FindByName(String name) {
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    @Override
    public List<ProviderDto> execute() throws BusinessException {
	List<ProviderRecord> lista = new ArrayList<ProviderRecord>();
	lista.addAll(pg.findByName(name));
	BusinessChecks.isTrue(lista.size() > 0);

	return DtoAssembler.toOrderedProviderDtoList(lista);
    }

}
