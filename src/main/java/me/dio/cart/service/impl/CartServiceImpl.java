package me.dio.cart.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.cart.enumeration.PaymentMethod;
import me.dio.cart.model.Cart;
import me.dio.cart.model.Item;
import me.dio.cart.model.Restaurant;
import me.dio.cart.repository.CartRepository;
import me.dio.cart.repository.ProductRepository;
import me.dio.cart.resource.dto.ItemDto;
import me.dio.cart.service.CartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public Item addItemToCart(ItemDto ItemDto) {
        Cart cart = openCart(ItemDto.getCartId());
        if (cart.isClosed()) {
            throw new RuntimeException("Cart is closed");
        }
        Item itemToBeInserted = Item.builder()
                .quantity(ItemDto.getQuantity())
                .cart(cart)
                .product(productRepository.findById(ItemDto.getProductId()).orElseThrow(
                        () -> new RuntimeException("Product not found")
                ))
                .build();
        List<Item> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            cartItems.add(itemToBeInserted);
        } else {
            Restaurant currentRestaurant = cartItems.get(0).getProduct().getRestaurant();
            Restaurant restaurantOfItemToAdd = itemToBeInserted.getProduct().getRestaurant();
            if (currentRestaurant.equals(restaurantOfItemToAdd)) {
                cartItems.add(itemToBeInserted);
            } else {
                throw new RuntimeException("Not possible to add Products of different restaurant. Close the cart");
            }
        }
        List<Double> itemsValues = new ArrayList<>();
        for (Item cartItem : cartItems) {
            double itemTotalValue = cartItem.getProduct().getUnitValue() * cartItem.getQuantity();
            itemsValues.add(itemTotalValue);
        }
        double cartTotalValue = itemsValues.stream()
                .mapToDouble(totalValueOfEachItem -> totalValueOfEachItem)
                .sum();
        cart.setTotalValue(cartTotalValue);
        cartRepository.save(cart);
        return itemToBeInserted;
    }

    @Override
    public Cart openCart(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Cart not found");
                }
        );
    }

    @Override
    public Cart closeCart(Long id, int numberPaymentMethod) {
        Cart cart = openCart(id);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        PaymentMethod paymentMethod = numberPaymentMethod == 0 ? PaymentMethod.MONEY : PaymentMethod.CARDREADER;
        cart.setPaymentMethod(paymentMethod);
        cart.setClosed(true);
        return cartRepository.save(cart);

    }
}
