package org.example.aliditestproject.services;

import org.example.aliditestproject.exceptions.ProductIdValidationExceptions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PriceService implements PriceServiceInterface{
    @Cacheable("productPrice")
    public Float getPrice(Integer id){
        Float price=priceService();
        if(price<0) {
            throw new ProductIdValidationExceptions("Not found product with id "+id);
        }
        return price;
    }

    public Float priceService() {
        return new Random().nextFloat();
    }
}
