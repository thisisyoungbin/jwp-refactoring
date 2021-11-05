package kitchenpos.fixture;

import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TableGroupFixture {

    private static final Long ID = 1L;
    private static final LocalDateTime CREATED_DATE = LocalDateTime.now();

    public static TableGroup create(OrderTable... orderTables) {
        TableGroup tableGroup = new TableGroup();

        tableGroup.setId(ID);
        tableGroup.setCreatedDate(CREATED_DATE);
        tableGroup.setOrderTables(Arrays.asList(orderTables));

        return tableGroup;
    }
}
