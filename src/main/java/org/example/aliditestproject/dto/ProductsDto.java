package org.example.aliditestproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductsDto {

    @NotNull(message = "Product id is empty")
    private Integer productId;
    @Min(value = 1,message = "count must be greater than 0")
    @NotNull(message = "Product count is empty")
    private Integer count;
    private Float productSum;
}