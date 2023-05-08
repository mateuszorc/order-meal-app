package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.OrderItem;
import com.javaproject.eLaunchApp.models.enums.PriceType;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemService {
	List<OrderItem> getAll();
	void put(UUID uuid, OrderItem orderItem);
	void delete(UUID uuid);
	Optional<OrderItem> getByUuid(UUID uuid);

	BigDecimal calculatePrice(List<OrderItem> orderItemList, BigDecimal startPrice, PriceType priceType) throws UnsupportedDataTypeException;
	BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderBruttoPrice) throws UnsupportedDataTypeException;
}
