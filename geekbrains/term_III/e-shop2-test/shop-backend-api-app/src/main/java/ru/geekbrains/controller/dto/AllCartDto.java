package ru.geekbrains.controller.dto;

import ru.geekbrains.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.List;

public class AllCartDto {

    private List<LineItem> lineItems;

    private BigDecimal subtotal;

    public AllCartDto() {
    }

    public AllCartDto(List<LineItem> lineItems, BigDecimal subtotal) {
        this.lineItems = lineItems;
        this.subtotal = subtotal;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
