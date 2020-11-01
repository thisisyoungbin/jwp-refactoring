package kitchenpos.application;

import static kitchenpos.helper.EntityCreateHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuDao;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuGroupDao;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.dto.OrderCreateRequest;
import kitchenpos.order.dto.OrderEditRequest;
import kitchenpos.order.dto.OrderLineItemDto;
import kitchenpos.order.service.OrderService;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductDao;
import kitchenpos.table.domain.Table;
import kitchenpos.table.domain.TableDao;

@SpringBootTest
@Sql(value = "/truncate.sql")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TableDao tableDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuGroupDao menuGroupDao;

    @Autowired
    private ProductDao productDao;

    @DisplayName("주문 생성 시 주문항목이 없을 시 예외가 발생한다.")
    @Test
    void createWithNoOrderItem() {
        OrderCreateRequest request = new OrderCreateRequest(1L, null);

        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 생성 시 주문 항목이 중복되거나 없는 주문 항목일 시 예외가 발생한다.")
    @Test
    void createWithInvalidOrderItem() {
        MenuGroup savedMenuGroup = getMenuGroup();
        Product savedProduct = saveProduct();
        Menu savedMenu = saveMenu(savedMenuGroup, savedProduct);

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto(savedMenu.getId(), 2L);
        OrderCreateRequest request = new OrderCreateRequest(1L, Arrays.asList(orderLineItemDto, orderLineItemDto));

        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 생성 시 주문 테이블이 없는 경우 예외가 발생한다.")
    @Test
    void createWithInvalidOrderTable() {
        MenuGroup savedMenuGroup = getMenuGroup();
        Product savedProduct = saveProduct();
        Menu savedMenu = saveMenu(savedMenuGroup, savedProduct);

        Table table = createTable(null, false, null, 5);
        tableDao.save(table);

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto(savedMenu.getId(), 2L);
        OrderCreateRequest request = new OrderCreateRequest(null, Arrays.asList(orderLineItemDto, orderLineItemDto));

        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 생성 시 주문테이블의 손님이 없을 경우 예외가 발생한다.")
    @Test
    void createWithEmptyTable() {
        MenuGroup savedMenuGroup = getMenuGroup();
        Product savedProduct = saveProduct();
        Menu savedMenu = saveMenu(savedMenuGroup, savedProduct);

        Table table = createTable(null, true, null, 5);
        Table savedTable = tableDao.save(table);

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto(savedMenu.getId(), 2L);
        OrderCreateRequest request = new OrderCreateRequest(savedTable.getId(), Arrays.asList(orderLineItemDto));

        assertThatThrownBy(() -> orderService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 생성한다.")
    @Test
    void create() {
        MenuGroup savedMenuGroup = getMenuGroup();
        Product savedProduct = saveProduct();
        Menu savedMenu = saveMenu(savedMenuGroup, savedProduct);
        Table savedTable = saveOrderTable();

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto(savedMenu.getId(), 2L);
        OrderCreateRequest request = new OrderCreateRequest(savedTable.getId(),
            Arrays.asList(orderLineItemDto));

        Long id = orderService.create(request);

        assertThat(id).isNotNull();
    }

    @DisplayName("주문 리스트를 조회한다")
    @Test
    void list() {
        MenuGroup savedMenuGroup = getMenuGroup();
        Product savedProduct = saveProduct();
        Menu savedMenu = saveMenu(savedMenuGroup, savedProduct);

        OrderLineItem 주문된_치킨세트 = createOrderLineItem(null, null, savedMenu.getId(), 3);

        Table savedTable = saveOrderTable();

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto(savedMenu.getId(), 2L);
        OrderCreateRequest request = new OrderCreateRequest(savedTable.getId(),
            Arrays.asList(orderLineItemDto));

        Long id = orderService.create(request);

        List<Order> actual = orderService.list();

        assertAll(
            () -> assertThat(actual).hasSize(1),
            () -> assertThat(actual.get(0).getId()).isEqualTo(id)
        );
    }

    private Table saveOrderTable() {
        Table table = createTable(null, false, null, 5);
        return tableDao.save(table);
    }

    @DisplayName("주문 상태를 변경할 때 주문이 없을 시 예외가 발생한다.")
    @Test
    void changeOrderStatusWithInvalidOrder() {
        OrderEditRequest request = new OrderEditRequest(OrderStatus.COOKING);

        assertThatThrownBy(() -> orderService.changeOrderStatus(1L, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문상태를 변경할 때 주문상태가 'COMPLETION' 인 경우 예외가 발생한다.")
    @Test
    void changeOrderStatusWhenCompletion() {
        OrderEditRequest request = new OrderEditRequest(OrderStatus.COMPLETION);
        assertThatThrownBy(() -> orderService.changeOrderStatus(1L, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 상태를 변경한다.")
    @Test
    void changeOrderStatus() {
        MenuGroup savedMenuGroup = getMenuGroup();
        Product savedProduct = saveProduct();
        Menu savedMenu = saveMenu(savedMenuGroup, savedProduct);
        Table savedTable = saveOrderTable();

        OrderLineItemDto orderLineItemDto = new OrderLineItemDto(savedMenu.getId(), 2L);
        OrderCreateRequest request = new OrderCreateRequest(savedTable.getId(),
            Arrays.asList(orderLineItemDto));
        Long id = orderService.create(request);

        OrderEditRequest editRequest = new OrderEditRequest(OrderStatus.MEAL);
        orderService.changeOrderStatus(id, editRequest);
    }

    private Menu saveMenu(MenuGroup savedMenuGroup, Product savedProduct) {
        MenuProduct menuProduct = createMenuProduct(null, null, savedProduct.getId(), 1L);
        Menu 치킨세트 = createMenu(null, savedMenuGroup.getId(), Arrays.asList(menuProduct), "둘둘치킨",
            BigDecimal.valueOf(1900L));

        return menuDao.save(치킨세트);
    }

    private Product saveProduct() {
        Product product = createProduct(null, "양념치킨", BigDecimal.valueOf(2000L));
        return productDao.save(product);
    }

    private MenuGroup getMenuGroup() {
        MenuGroup menuGroup = createMenuGroup(null, "치킨류");
        return menuGroupDao.save(menuGroup);
    }
}