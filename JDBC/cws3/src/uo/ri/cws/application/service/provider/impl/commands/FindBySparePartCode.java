package uo.ri.cws.application.service.provider.impl.commands;

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

public class FindBySparePartCode implements Command<List<ProviderDto>> {

    private String code;
    private ProviderGateway pg = Factories.persistence.forProvider();

    public FindBySparePartCode(String code) {
	ArgumentChecks.isNotBlank(code);
	this.code = code;
    }

    @Override
    public List<ProviderDto> execute() throws BusinessException {
	List<ProviderRecord> lista = pg.findBySparePartCode(code);
	BusinessChecks.isTrue(lista.size() != 0);
	List<ProviderDto> resultado = DtoAssembler
		.toOrderedProviderDtoList(lista);

	return resultado;
    }

}
