package uo.ri.cws.application.service.provider.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.provider.impl.commands.AddProvider;
import uo.ri.cws.application.service.provider.impl.commands.Delete;
import uo.ri.cws.application.service.provider.impl.commands.FindByName;
import uo.ri.cws.application.service.provider.impl.commands.FindByNif;
import uo.ri.cws.application.service.provider.impl.commands.FindBySparePartCode;
import uo.ri.cws.application.service.provider.impl.commands.Update;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class ProvidersCrudServiceImpl implements ProvidersCrudService {
	private JdbcCommandExecutor executor = new JdbcCommandExecutor();

	@Override
	public ProviderDto add(ProviderDto dto) throws BusinessException {
		return executor.execute(new AddProvider(dto));
	}

	@Override
	public void delete(String nif) throws BusinessException {
		executor.execute(new Delete(nif));
	}

	@Override
	public void update(ProviderDto dto) throws BusinessException {
		executor.execute(new Update(dto));

	}

	@Override
	public Optional<ProviderDto> findByNif(String nif) throws BusinessException {
		return executor.execute(new FindByNif(nif));
	}

	@Override
	public List<ProviderDto> findByName(String name) throws BusinessException {
		return executor.execute(new FindByName(name));
	}

	@Override
	public List<ProviderDto> findBySparePartCode(String code) throws BusinessException {
		return executor.execute(new FindBySparePartCode(code));
	}

}
