package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Test
    public void 주문취소() {
        //given
        Member member = new Member();
        member.setName("김진욱");
        Long memberId = memberService.join(member);
        Item item = new Book();
        item.setName("제3의물결");
        item.setPrice(10000);
        item.setStockQuantity(50);
        Long itemId = itemService.saveItem(item);

        Long orderId = orderService.order(memberId, itemId, 10);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderService.findOne(orderId);

        // 검증
        // 1. OrderStatus가 CANCEL로 변경
        assertEquals(order.getStatus(), OrderStatus.CANCEL);
        //
        assertEquals(item.getStockQuantity(), 10);
        // 2. 주문 취소된 상품은 그만큼 재고가 다시 증가해야 한다.
    }

    @Test
    public void 주문등록() {
        //given
        Member member = new Member();
        member.setName("");
        //when

        //then


    }

}
