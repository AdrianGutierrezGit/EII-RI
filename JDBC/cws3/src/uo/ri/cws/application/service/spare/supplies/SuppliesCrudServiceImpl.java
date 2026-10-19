package uo.ri.cws.application.service.spare.supplies;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.supplies.commands.AddSupply;
import uo.ri.cws.application.service.spare.supplies.commands.DeleteSupply;
import uo.ri.cws.application.service.spare.supplies.commands.FindByNifAndCode;
import uo.ri.cws.application.service.spare.supplies.commands.FindByProviderNif;
import uo.ri.cws.application.service.spare.supplies.commands.FindSupplyBySparePartCode;
import uo.ri.cws.application.service.spare.supplies.commands.UpdateSupply;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class SuppliesCrudServiceImpl implements SuppliesCrudService {
    private JdbcCommandExecutor executor = new JdbcCommandExecutor();

    @Override
    public SupplyDto add(SupplyDto dto) throws BusinessException {
	return executor.execute(new AddSupply(dto));
    }

    @Override
    public void delete(String nif, String code) throws BusinessException {
	executor.execute(new DeleteSupply(nif, code));

    }

    @Override
    public void update(SupplyDto dto) throws BusinessException {
	executor.execute(new UpdateSupply(dto));
    }

    @Override
    public Optional<SupplyDto> findByNifAndCode(String nif, String code)
	    throws BusinessException {
	return executor.execute(new FindByNifAndCode(nif, code));
    }

    @Override
    public List<SupplyDto> findByProviderNif(String nif)
	    throws BusinessException {
	return executor.execute(new FindByProviderNif(nif));
    }

    @Override
    public List<SupplyDto> findBySparePartCode(String code)
	    throws BusinessException {
	return executor.execute(new FindSupplyBySparePartCode(code));
    }

}
