package kitchenpos.helper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.product.domain.Product;
import kitchenpos.table.domain.Table;
import kitchenpos.table.domain.TableGroup;

public class EntityCreateHelper {
    public static MenuGroup createMenuGroup(Long id, String name) {
        return new MenuGroup(id, name);
    }

    public static Menu createMenu(Long id, Long menuGroupId, List<MenuProduct> menuProducts, String name,
        BigDecimal price) {
        return new Menu(id, name, price, menuGroupId, menuProducts);
    }

    public static MenuProduct createMenuProduct(Long seq, Long menuId, Long productId, long quantity) {
        return new MenuProduct(seq, menuId, productId, quantity);
    }

    public static OrderLineItem createOrderLineItem(Long seq, Long orderId, Long menuId, long quantity) {
        return new OrderLineItem(seq, orderId, menuId, quantity);
    }

    public static Table createTable(Long id, boolean isEmpty, Long tableGroupId, int numberOfGuests) {
        return new Table(id, tableGroupId, numberOfGuests, isEmpty);
    }

    public static Product createProduct(Long id, String name, BigDecimal price) {
        return new Product(id, name, price);
    }

    public static TableGroup createTableGroup(Long id, LocalDateTime createdDate, List<Table> tables) {
        return new TableGroup(id, createdDate, tables);
    }

    public static Order createOrder(Long id, LocalDateTime orderedTime, List<OrderLineItem> orderLineItems,
        OrderStatus orderStatus, Long orderTableId) {
        return new Order(id, orderTableId, orderStatus.name(), orderedTime, orderLineItems);
    }
}
