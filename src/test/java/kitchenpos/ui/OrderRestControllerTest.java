package kitchenpos.ui;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import kitchenpos.application.OrderService;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.fixture.OrderFixture;
import kitchenpos.fixture.OrderLineItemFixture;
import kitchenpos.fixture.TableFixture;

@WebMvcTest(controllers = OrderRestController.class)
class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderLineItem item;
    private Order order;

    @BeforeEach
    void setUp() {
        item = OrderLineItemFixture.createWithoutId(1L, OrderFixture.ID1);
        order = OrderFixture.createWithId(OrderFixture.ID1, OrderFixture.STATUS1,
            TableFixture.ID1, Collections.singletonList(item));
    }

    @DisplayName("Order 생성")
    @Test
    void create() throws Exception {
        when(orderService.create(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(order))
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("location", "/api/orders/" + order.getId()))
            .andExpect(jsonPath("id").value(order.getId()));
    }

    @DisplayName("Find all order")
    @Test
    void list() throws Exception {
        when(orderService.list()).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/api/orders")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("[0].id").value(order.getId()));
    }

    @DisplayName("Order Status 수정")
    @Test
    void changeOrderStatus() throws Exception {
        Order order2 = OrderFixture.createWithId(OrderFixture.ID1, OrderFixture.STATUS2,
            TableFixture.ID1, Collections.singletonList(item));
        when(orderService.changeOrderStatus(anyLong(), any(Order.class))).thenReturn(order2);

        mockMvc.perform(put("/api/orders/{orderId}/order-status", order.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(order))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("orderStatus").value(order2.getOrderStatus()));
    }
}
