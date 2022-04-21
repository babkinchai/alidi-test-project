package org.example.aliditestproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aliditestproject.dto.BasketDto;
import org.example.aliditestproject.dto.ProductsDto;
import org.example.aliditestproject.services.PriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {


    @MockBean
    private PriceService priceService;

    @Autowired
    private MockMvc mvc;

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void basketSumTest() throws Exception {
        Mockito.when(priceService.getPrice(1)).thenReturn(10F);
        Mockito.when(priceService.getPrice(0)).thenReturn(7F);
        BasketDto basketDto = new BasketDto();
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProductId(1);
        productsDto.setCount(7);
        basketDto.getProductList().add(productsDto);
        ProductsDto productsDto1 = new ProductsDto();
        productsDto1.setProductId(0);
        productsDto1.setCount(5);
        basketDto.getProductList().add(productsDto1);

        MvcResult mvcResult = mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(basketDto))).andReturn();
        basketDto.getProductList().forEach(products -> {
                products.setProductSum(products.getCount()*priceService.getPrice(products.getProductId()));
                basketDto.addBasketSum(products.getProductSum());
        });
        assertEquals(mvcResult.getResponse().getContentAsString(),toJson(basketDto));
    }
    @Test
    public void basketProductListValid() throws Exception {
        BasketDto basketDto = new BasketDto();
        MvcResult mvcResult = mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(basketDto))).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }
    @Test
    public void basketProductValid() throws Exception {
        Mockito.when(priceService.priceService()).thenReturn(-1F);
        BasketDto basketDto = new BasketDto();
        ProductsDto productsDto = new ProductsDto();
        basketDto.getProductList().add(productsDto);
        ProductsDto productsDto1 = new ProductsDto();
        productsDto1.setProductId(0);
        productsDto1.setCount(5);
        basketDto.getProductList().add(productsDto1);
        MvcResult mvcResult = mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(basketDto))).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }
    @Test
    public void productIdNotFoundTest() throws Exception {
        Mockito.when(priceService.priceService()).thenReturn(-1F);
        Mockito.when(priceService.getPrice(1)).thenCallRealMethod();
        BasketDto basketDto = new BasketDto();
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProductId(1);
        productsDto.setCount(7);
        basketDto.getProductList().add(productsDto);
        ProductsDto productsDto1 = new ProductsDto();
        productsDto1.setProductId(0);
        productsDto1.setCount(5);
        basketDto.getProductList().add(productsDto1);

        MvcResult mvcResult = mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(basketDto))).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),400);
    }
    @Test
    public void invalidPaymentTest() throws Exception {
        BasketDto basketDto = new BasketDto();
        basketDto.setPaymentType("invalid");
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProductId(1);
        productsDto.setCount(7);
        basketDto.getProductList().add(productsDto);
        ProductsDto productsDto1 = new ProductsDto();
        productsDto1.setProductId(0);
        productsDto1.setCount(5);
        basketDto.getProductList().add(productsDto1);

        MvcResult mvcResult = mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(basketDto))).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),400);
    }

    @Test
    public void validPaymentTest() throws Exception {
        BasketDto basketDto = new BasketDto();
        basketDto.setPaymentType("card");
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProductId(1);
        productsDto.setCount(7);
        basketDto.getProductList().add(productsDto);
        ProductsDto productsDto1 = new ProductsDto();
        productsDto1.setProductId(0);
        productsDto1.setCount(5);
        basketDto.getProductList().add(productsDto1);

        MvcResult mvcResult = mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(basketDto))).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
    }
}