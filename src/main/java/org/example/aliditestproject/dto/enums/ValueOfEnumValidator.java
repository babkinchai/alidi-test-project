package org.example.aliditestproject.dto.enums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum,String> {

    private ValueOfEnum annotation;

    @Override
    public void initialize(ValueOfEnum annotation) {
        this.annotation=annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (value == null) {
            return true;
        }

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString())))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
