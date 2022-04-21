package org.example.aliditestproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.example.aliditestproject.dto.enums.ValueOfEnum;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BasketDto {

    private Float basketSum=0F;
    @Valid
    @NotEmpty(message = "Product list is empty")
    private List<ProductsDto> productList=new ArrayList<>();
    @ValueOfEnum(enumClass = PaymentType.class,ignoreCase = true)
    private String paymentType;
    private Integer idAddr;

    public void addBasketSum(Float productSum) {
        basketSum += productSum;
    }
}
