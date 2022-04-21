package org.example.aliditestproject.services;

import org.example.aliditestproject.dto.BasketDto;
import org.springframework.stereotype.Service;

@Service
public class BasketService implements BasketServiceInterface{

    private final PriceServiceInterface priceServiceInterface;

    public BasketService(PriceService priceServiceInterface) {
        this.priceServiceInterface = priceServiceInterface;
    }

    @Override
    public BasketDto calculateBasket(BasketDto basketDto) {
        basketDto.getProductList().forEach(productsDto -> {

                Float price=priceServiceInterface.getPrice(productsDto.getProductId());
                productsDto.setProductSum(price * productsDto.getCount());
                basketDto.addBasketSum(productsDto.getProductSum());

        });
        return basketDto;
    }


}
