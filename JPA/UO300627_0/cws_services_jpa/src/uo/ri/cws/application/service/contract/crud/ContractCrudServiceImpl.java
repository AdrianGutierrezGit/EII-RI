package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.contract.ContractCrudService;
import uo.ri.cws.application.service.contract.crud.command.ListContractsInForce;
import uo.ri.cws.application.util.command.CommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ContractCrudServiceImpl implements ContractCrudService {

    private CommandExecutor executor = Factories.executor.forExecutor();

    @Override
    public ContractDto create(ContractDto c) throws BusinessException {      
        return null;
    }

    @Override
    public void update(ContractDto dto) throws BusinessException {

    }

    @Override
    public void delete(String id) throws BusinessException {

    }

    @Override
    public void terminate(String contractId) throws BusinessException {

    }

    @Override
    public Optional<ContractDto> findById(String id) throws BusinessException {
        return Optional.empty();
    }

    @Override
    public List<ContractSummaryDto> findByMechanicNif(String nif)
            throws BusinessException {
        return null;
    }

    @Override
    public List<ContractDto> findInforceContracts() throws BusinessException {
        return executor.execute(new ListContractsInForce());
    }

    @Override
    public List<ContractSummaryDto> findAll() throws BusinessException {
        return null;
    }

}
