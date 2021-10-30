# 키친포스

## 요구 사항
### MenuGroup
- [x] MenuGroup을 만들어 저장한다.
- [x] 모든 MenuGroup을 불러와 반환한다.

### Menu
- [x] Menu는 name과 price, menuGroup, menuProducts를 갖고 있다.
  
- [x] Menu를 만들어 저장한다.
    - [x] menu의 가격이 null이거나 0보다 작은 경우 예외를 던진다.
    - [x] menu의 menuGroup을 찾을 수 없는 경우 예외를 던진다.
    - [x] menuProducts를 찾을 수 없는 경우 예외를 던진다.
    - [x] menu의 모든 Product를 가격과 수량을 곱해 더한다.
      - [x] 존재하지 않는 product가 있다면 예외를 던진다.
    - [x] menu의 가격이 menuProducts 가격의 총합을 넘는 경우 예외를 던진다.
  - [x] menu를 저장한다.
    - [x] menuproduct를 저장한다
    - [x] 저장된 menu에 저장된 menuProducts를 저장한다.
  
- [x] 모든 menu를 불러와 반환한다.
  - [x] 모든 menu에는 menuProduct가 존재한다.

### Order
- [x] Order를 만든다.
  - [x] orderLineItems를 불러온다
    - [x] 존재하지 않는 경우 예외를 던진다.
  - [x] orderLineItems의 갯수와 저장된 menuIds의 갯수가 다른경우 예외를 던진다.
  - [x] order의 orderTableId를 이용해 orderTable을 불러온다.
    - [x] 존재하지 않는 경우 예외를 던진다.
  - [x] orderTable이 비어있는 경우 예외를 던진다.
  - [x] order는 orderTableId를 가진다.
  - [x] order의 OrderStatus는 Cooking으로 설정한다.
  - [x] order는 orderTime을 현재로 설정한다.
- [x] order를 저장한다.
  - [x] orderLineItem은 저장된 orderId를 갖고 있다.
  - [x] orderLineItem을 저장한 후 새로운 orderLineItems에 더한다.
- [x] 저장된 order는 저장된 orderLineItems를 가진다.
  
- [x] Order를 반환한다.
  - [x] 모든 order를 불러와 반환한다.
  - [x] 모든 order에는 orderLineItem이 존재한다.
  
- [x] OrderStatus를 변경한다.
  - [x] 기존 order를 불러온다.
    - [x] 존재하지 않는 경우 예외를 던진다.
    - [x]  orderStatus가 완성인 경우 예외를 던진다.
    - [x] 기존 order의 orderStatus를 넘겨받은 order의 상태로 변경한다.
    - [x] orderStatus가 변경된 order를 저장한다.
    - [x] 저장된 order는 orderLineItems를 갖고 있다.
  
### Product
- [x] Product를 만든다.
  - [x] product의 가격이 null이거나 0보다 작은 경우 예외를 던진다
  - [x] product를 저장한다.

- [x] 모든 Product를 반환한다.
  - [x] 모든 product를 찾아 반환한다.
  
###  TableGroup
- [x] tableGroup을 만든다.
  - [x] orderTable를 불러온다.
  - [x] 불러온 orderTables가 비어있지 않거나 사이즈가 1이하인 경우 예외를 던진다.
  - [x] orderTables에서 orderTableIds를 모아 orderTables 사이즈와 비교해 다른 경우 예외를 던진다.
  - [x] orderTableIds를 이용해 저장된 orderTables를 불러온다.
  - [x] 저장된 orderTable 중 비어있거나, orderTable의 TableGroupId가 존재하는 경우 예외를 던진다.
  - [x] tableGroup에 현재 날짜를 설정한다.
  - [x] tableGroup을 저장한다.
    - [x] 저장된 orderTable은 tableGroupId를 지닌다.
    - [x] 저장된 orderTable은 비어있지 않다.
    - [x] orderTable을 저장한다.
  - [x] 저장된 tableGroup은 orderTable를 갖고있다.

- [x] tableGroup을 해체한다.
  - [x] tableGroupId에 해당하는 모든 orderTable을 불러온다.
  - [x] 불러온 orderTables의 아이디를 모아 리스트로 만든다.
  - [x] ordertablesIds에 속하고, orderStatus가 {cooking, meal}에 속한 경우 예외를 던진다.
  - [x] 모든 orderTable을 다음 상태로 만든다.
    - [x] tableGroupId가 null
    - [x] orderTable은 비어있지 않음
  - [x] 변경된 상태의 orderTable을 저장
  
### Table
- [x] table을 만든다.
  - [x] orderTable의 id를 null로 지정한다.
  - [x] orderTable의 TableGroupId를 null로 지정한다.
  - [x] orderTable을 저장한다.
  
- [x] 저장된 table을 반환한다.
  
- [x] table을 비어있는 상태로 만든다.
  - [x] orderTable을 불러온다
    - [x] 존재하지 않는 경우 예외를 던진다.
    - [x] tableGroupId가 존재하는 경우 예외를 던진다.
  - [x] 변경하려는 orderTableId의 orderStatus가 {cooking, meal}인 경우 예외를 던진다.
  - [x] 불러온 orderTable을 전달받은 orderTable의 empty값으로 변경한다.
  - [x] 불러온 orderTable을 저장한다.
  
- [x] table의 게스트 숫자를 변경한다.
  - [x] orderTable의 numberOfGuests를 불러온다.
    - [x] numberOfGuests가 0 이하인 경우 예외를 던진다.
  - [x] 전달받은 orderTableId로 저장된 orderTable을 불러온다.
    - [x] 존재하지 않는 경우 예외를 던진다.
    - [x] 저장된 orderTable이 비어있는 경우 예외를 던진다.
  - [x] 저장된 orderTable에 numberOfGuests를 지정한다.
  - [x] 저장된 orderTable을 저장한다.


## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
