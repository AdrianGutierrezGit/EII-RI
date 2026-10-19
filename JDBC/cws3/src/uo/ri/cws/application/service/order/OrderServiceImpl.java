package uo.ri.cws.application.service.order;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.order.commands.FindByCode;
import uo.ri.cws.application.service.order.commands.FindByProviderNif;
import uo.ri.cws.application.service.order.commands.GenerateOrders;
import uo.ri.cws.application.service.order.commands.ReceiveOrder;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.util.command.executor.JdbcCommandExecutor;
import uo.ri.util.exception.BusinessException;

public class OrderServiceImpl implements OrdersService {
	private JdbcCommandExecutor executor = new JdbcCommandExecutor();

	@Override
	public List<OrderDto> generateOrders() throws BusinessException {

		return executor.execute(new GenerateOrders());
	}

	@Override
	public List<OrderDto> findByProviderNif(String nif) throws BusinessException {
		return executor.execute(new FindByProviderNif(nif));

	}

	@Override
	public Optional<OrderDto> findByCode(String code) throws BusinessException {
		return executor.execute(new FindByCode(code));
	}

	@Override
	public OrderDto receive(String code) throws BusinessException {
		return executor.execute(new ReceiveOrder(code));
	}

}
