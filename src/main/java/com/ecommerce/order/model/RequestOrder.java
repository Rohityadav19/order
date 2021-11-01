package com.ecommerce.order.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestOrder {
	@NotNull(message="Payment Method Should Not be Null")
	@NotEmpty(message="Payment Method Should Not be Emply")
	@NotBlank(message="Payment Method Should Not be Blank")
	private String paymentMethod;
	@NotNull(message="Delivery Address Method Should Not be Null")
	@NotEmpty(message="Delivery Address Method Should Not be Emply")
	@NotBlank(message="Delivery Address Method Should Not be Blank")
	private String deliveryAddress;

}
