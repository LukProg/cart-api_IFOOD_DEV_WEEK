package me.dio.cart.service;

import me.dio.cart.model.Cart;
import me.dio.cart.model.Item;
import me.dio.cart.resource.dto.ItemDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Item addItemToCart(ItemDto ItemDto);

    Cart openCart(Long id);

    Cart closeCart(Long id, int paymentMethod);
}
