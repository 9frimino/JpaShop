package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final OrderService orderService;

    private final EntityManager em;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems() {

        return itemRepository.findAll();
    }

    public Item findOne(Long itemID) {

        return itemRepository.findOne(itemID);
    }
    @Transactional
    public void delete(Item item) {

        for (Order order : orderService.searchOrders(new OrderSearch())) {
            for (OrderItem orderItem : order.getOrderItems()) {
                if ( orderItem.getItem().getId() == item.getId()) {
                    em.remove(orderItem.getItem());
                }
            }
        }

        itemRepository.delete(item);
    }

}
