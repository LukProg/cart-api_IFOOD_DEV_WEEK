package me.dio.cart.resource;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.cart.model.Cart;
import me.dio.cart.model.Item;
import me.dio.cart.resource.dto.ItemDto;
import me.dio.cart.service.CartService;
import org.springframework.web.bind.annotation.*;

@Api(value = "/ifood-devweek/carts")
@RestController
@RequestMapping("/ifood-devweek/carts")
@RequiredArgsConstructor
public class CartResource {
    private final CartService cartService;

    @PostMapping
    public Item addItemToCart(@RequestBody ItemDto itemDto) {
        return cartService.addItemToCart(itemDto);
    }

    @GetMapping("/{id}")
    public Cart openCart(@PathVariable("id") Long id) {
        return cartService.openCart(id);
    }

    @PatchMapping("/closeCart/{cartId}")
    public Cart closeCart(@PathVariable("cartId") Long cartId, @RequestParam("paymentMethod") int paymentMethod) {
        return cartService.closeCart(cartId, paymentMethod);
    }


}
