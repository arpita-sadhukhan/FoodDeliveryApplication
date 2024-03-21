package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.CartDTO;
import com.org.onlineFoodDelivery.dto.DishDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;
import com.org.onlineFoodDelivery.dto.UserDTO;
import com.org.onlineFoodDelivery.entity.Cart;
import com.org.onlineFoodDelivery.entity.Dishes;
import com.org.onlineFoodDelivery.entity.Restaurant;
import com.org.onlineFoodDelivery.entity.User;
import com.org.onlineFoodDelivery.exception.ObjectCreationException;
import com.org.onlineFoodDelivery.exception.ObjectNotFoundException;
import com.org.onlineFoodDelivery.respository.CartRepository;
import com.org.onlineFoodDelivery.respository.DishesRepository;
import com.org.onlineFoodDelivery.respository.RestaurantRepository;
import com.org.onlineFoodDelivery.respository.UserRegistrationRepository;
import com.org.onlineFoodDelivery.util.PopulateDTOFromEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService implements ICartService {

    @Autowired
    CartRepository repo;

    @Autowired
    RestaurantRepository restaurantRepo;

    @Autowired
    UserRegistrationRepository userRepo;

    @Autowired
    DishesRepository dishRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CartDTO addToCart(CartDTO dto, long userId, long restaurantId) {

        Optional<Cart> cartOpt = repo.findByUserId(userId);
        List<DishDTO> dishDTOList = dto.getDishes();
        Cart cart = modelMapper.map(dto, Cart.class);
        Cart savedCart = new Cart();
        if (cartOpt.isPresent()) {
            Cart existingCart = cartOpt.get();
            if (existingCart.getRestaurant().getId() == restaurantId) {
                List<Dishes> dishes = getDishes(dishDTOList, restaurantId);
                if (!dishes.isEmpty()) {
                    dishes.forEach(existingCart::addDish);
                    calculateCartValue(existingCart);
                    savedCart = repo.save(existingCart);
                }
            } else {
                throw new ObjectCreationException("Cart already contains dishes from restaurant : " + existingCart.getRestaurant().getName());
            }
        } else {
            Optional<User> userOpt = userRepo.findById(userId);
            User user = userOpt.orElseThrow(() -> new ObjectNotFoundException("No user exists with id : " + userId));
            cart.setUser(user);

            Optional<Restaurant> restaurantOpt = restaurantRepo.findById(restaurantId);
            Restaurant restaurant = restaurantOpt.orElseThrow(() -> new ObjectNotFoundException("No Restaurant exists with id : " + restaurantId));
            cart.setRestaurant(restaurant);

            List<Dishes> dishes = getDishes(dishDTOList, restaurantId);
            cart.setDishes(dishes);

            calculateCartValue(cart);
            savedCart = repo.save(cart);
        }
        return populateCartDto(savedCart);
    }

    @Override
    public CartDTO viewCart(long userId, long restaurantId) {
        Optional<Cart> cartOpt = repo.findByUserIdAndRestaurantId(userId, restaurantId);
        Cart cart = cartOpt.orElseThrow(() -> new ObjectNotFoundException("No cart exists for user : " + userId + " and restaurant : " + restaurantId));
        return populateCartDto(cart);
    }

    private void calculateCartValue(Cart cart) {
        List<Dishes> dishes = cart.getDishes();
        double cartValue = dishes.stream().map(Dishes::getPrice).reduce(Double::sum).get();
        cart.setCartValue(cartValue);
    }

    private List<Dishes> getDishes(List<DishDTO> dishDTOList, long restaurantId) {
        List<Long> dishIdList = dishDTOList.stream().map(DishDTO::getId).toList();
        List<Dishes> dishes = new ArrayList<>();
        for (Long id : dishIdList) {
            Dishes dish = dishRepo.findByIdAndRestaurantIdAndAvailable(id, restaurantId, Boolean.TRUE)
                    .orElseThrow(() -> new ObjectNotFoundException("This restaurant doesn't serve the food with id : " + id));
            dishes.add(dish);
        }
        return dishes;
    }

    private CartDTO populateCartDto(Cart cart) {
        CartDTO dto = new CartDTO();
        PopulateDTOFromEntity populatator = new PopulateDTOFromEntity();
        dto.setId(cart.getId());
        List<DishDTO> dishes = populatator.populateDishes(cart.getDishes());
        dto.setDishes(dishes);
        UserDTO user = populateUser(cart.getUser());
        dto.setUser(user);
        RestaurantDTO restaurant = populatator.populateRestaurant(cart.getRestaurant());
        dto.setRestaurant(restaurant);
        dto.setCartValue(cart.getCartValue());
        return dto;
    }


    private UserDTO populateUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }


}
