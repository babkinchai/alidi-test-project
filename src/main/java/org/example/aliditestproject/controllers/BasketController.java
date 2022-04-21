package org.example.aliditestproject.controllers;

import org.example.aliditestproject.dto.BasketDto;
import org.example.aliditestproject.services.BasketService;
import org.example.aliditestproject.services.BasketServiceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BasketController {

    private final BasketServiceInterface basketServiceInterface;

    public BasketController(BasketServiceInterface basketServiceInterface) {
        this.basketServiceInterface = basketServiceInterface;
    }


    @PostMapping(value = "/basket")
    public BasketDto calculateBasket( @Valid @RequestBody  BasketDto basketDto){
        return basketServiceInterface.calculateBasket(basketDto);
    }
}
