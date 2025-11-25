package com.AIagnet.agent.spend.dto.response;

import com.AIagnet.agent.spend.entity.SpendTransaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 지출 거래 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpendTransactionResponse {
    private Long id;
    private String transactionId;
    private String itemName;
    private String category;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private LocalDate purchaseDate;
    private String supplier;
    private String buyer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SpendTransactionResponse from(SpendTransaction transaction) {
        return SpendTransactionResponse.builder()
                .id(transaction.getId())
                .transactionId(transaction.getTransactionId())
                .itemName(transaction.getItemName())
                .category(transaction.getCategory())
                .quantity(transaction.getQuantity())
                .unitPrice(transaction.getUnitPrice())
                .totalAmount(transaction.getTotalAmount())
                .purchaseDate(transaction.getPurchaseDate())
                .supplier(transaction.getSupplier())
                .buyer(transaction.getBuyer())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}

