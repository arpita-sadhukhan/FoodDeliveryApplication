package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.OrderDTO;
import com.org.onlineFoodDelivery.entity.*;
import com.org.onlineFoodDelivery.exception.InvalidRequestException;
import com.org.onlineFoodDelivery.exception.ObjectNotFoundException;
import com.org.onlineFoodDelivery.respository.CartRepository;
import com.org.onlineFoodDelivery.respository.OrderHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.org.onlineFoodDelivery.entity.PaymentStatus.SUCCESS;
import com.org.onlineFoodDelivery.util.PopulateDTOFromEntity;

@Service
public class OrderHistoryService implements IOrderHistoryService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderHistoryRepository repo;

    @Autowired
    ModelMapper mapper;

    @Override
    public OrderDTO placeOrder(long userId, long restaurantId) {
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndRestaurantId(userId, restaurantId);
        Cart cart = cartOpt.orElseThrow(() -> new ObjectNotFoundException("No items exists in cart for user : "+userId+" and restaurant : "+restaurantId));
        OrderHistory order = createOrderRequest(cart);
        OrderHistory placedOrder = repo.save(order);
        if(placedOrder.getId() > 0)
            cartRepository.delete(cart);
        PopulateDTOFromEntity populator = new PopulateDTOFromEntity();
        return populator.populateOrderDTO(placedOrder);
    }

    @Override
    public OrderDTO confirmOrder(OrderDTO inputOrder, long orderId) {
        Optional<OrderHistory> orderOpt = repo.findById(orderId);
        OrderHistory order = orderOpt.orElseThrow(() -> new ObjectNotFoundException("No Order exists with id : "+orderId));
        switch (order.getConfirmation()) {
            case PENDING -> {
                if(inputOrder.getRestaurantConfirmation().equals(RestaurantConfirmation.ACCEPTED.getStatus())) {
                    order.setConfirmation(RestaurantConfirmation.ACCEPTED);
                    order = repo.save(order);
                    PopulateDTOFromEntity populator = new PopulateDTOFromEntity();
                    return populator.populateOrderDTO(order);
                }
            }
            case ACCEPTED -> throw new InvalidRequestException("Order status is already 'Accepted'");
        }
        return null;
    }

    @Override
    public List<OrderDTO> getPendingOrders(long restaurantId) {
        Optional<List<OrderHistory>> ordersOpt = repo.findByRestaurantIdAndConfirmation(restaurantId, RestaurantConfirmation.PENDING);

        List<OrderHistory> orderList = ordersOpt.get();
        if(orderList.isEmpty())
            throw new InvalidRequestException("No pending order exists for restaurant : "+restaurantId);
        PopulateDTOFromEntity populator = new PopulateDTOFromEntity();
        return populator.populateOrderDTOList(orderList);
    }

    private OrderHistory createOrderRequest(Cart cart) {
        OrderHistory order = new OrderHistory();
        order.setDishes(new ArrayList<>(cart.getDishes()));
        order.setRestaurant(cart.getRestaurant());
        order.setOrderTotal(cart.getCartValue());
        order.setUser(cart.getUser());
        order.setConfirmation(RestaurantConfirmation.PENDING);
        order.setPaymentStatus(SUCCESS);
        return order;
    }

}
