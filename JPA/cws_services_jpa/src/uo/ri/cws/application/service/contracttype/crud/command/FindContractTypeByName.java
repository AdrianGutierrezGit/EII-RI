package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.application.service.contracttype.ContractTypeCrudService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindContractTypeByName
        implements Command<Optional<ContractTypeDto>> {

    private String name;
    private ContractTypeRepository ctr = Factories.repository.forContractType();

    public FindContractTypeByName(String name) {
        ArgumentChecks.isNotNull(name, "The name is null");
        ArgumentChecks.isNotBlank(name, "The name is blank");
        ArgumentChecks.isNotEmpty(name, "The name is empty");
        this.name = name;
    }

    @Override
    public Optional<ContractTypeDto> execute() throws BusinessException {
        return DtoAssembler.toOptionalDto(ctr.findByName(name));
    }

}
