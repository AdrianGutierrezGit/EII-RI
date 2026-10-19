package uo.ri.cws.application.ui.manager;

import uo.ri.cws.application.ui.manager.spares.order.OrdersMenu;
import uo.ri.cws.application.ui.manager.spares.provider.ProvidersMenu;
import uo.ri.cws.application.ui.manager.spares.sparepart.SparePartsMenu;
import uo.ri.cws.application.ui.manager.spares.supply.SuppliesMenu;
import uo.ri.util.menu.BaseMenu;

public class SparePartMenu extends BaseMenu {

    public SparePartMenu() {
	menuOptions = new Object[][] {
		{ "Manager > Spare parts management", null },

		{ "Order menu", OrdersMenu.class },
		{ "Provider menu", ProvidersMenu.class },
		{ "Spare part menu", SparePartsMenu.class },
		{ "Supply menu", SuppliesMenu.class }, };
    }

}
