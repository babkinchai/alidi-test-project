package org.example.aliditestproject.services;

import org.example.aliditestproject.dto.BasketDto;
import org.springframework.stereotype.Service;

@Service
public class BasketService implements BasketServiceInterface{

    private final PriceServiceInterface priceServiceInterface;

    public BasketService(PriceServiceInterface priceServiceInterface) {
        this.priceServiceInterface = priceServiceInterface;
    }

    @Override
    public BasketDto calculateBasket(BasketDto basketDto) {
        basketDto.getProductList().forEach(
                productsDto -> {
                    productsDto.setProductSum(priceServiceInterface.getPrice(productsDto.getProductId()) * productsDto.getCount());
                    basketDto.addBasketSum(productsDto.getProductSum());
                }
        );

        return basketDto;
    }


}
