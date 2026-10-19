package uo.ri.cws.application.service.contract.crud.command;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.service.contract.ContractCrudService.ContractDto;
import uo.ri.cws.application.service.contract.crud.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class ListContractsInForce implements Command<List<ContractDto>> {

    private ContractRepository cr = Factories.repository.forContract();

    @Override
    public List<ContractDto> execute() throws BusinessException {
        return DtoAssembler.toDtoList(cr.findAllInForce());
    }

}
