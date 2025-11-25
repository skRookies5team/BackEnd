package com.AIagnet.agent.spend.controller;

import com.AIagnet.agent.spend.dto.request.SpendTransactionSearchRequest;
import com.AIagnet.agent.spend.dto.response.SpendTransactionResponse;
import com.AIagnet.agent.spend.service.SpendTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 지출 거래 컨트롤러.
 */
@RestController
@RequestMapping("/api/spend/transactions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "지출 관리", description = "지출 거래 조회 API")
public class SpendTransactionController {

    private final SpendTransactionService spendTransactionService;

    /**
     * 지출 거래 목록 조회 (검색).
     */
    @GetMapping
    public ResponseEntity<Page<SpendTransactionResponse>> searchSpendTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String buyer,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        log.info("GET /api/spend/transactions - startDate={}, endDate={}, category={}, supplier={}, buyer={}, page={}, size={}",
                startDate, endDate, category, supplier, buyer, page, size);

        SpendTransactionSearchRequest request = SpendTransactionSearchRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .category(category)
                .supplier(supplier)
                .buyer(buyer)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(spendTransactionService.searchSpendTransactions(request));
    }

    /**
     * 지출 거래 상세 조회.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SpendTransactionResponse> getSpendTransactionById(@PathVariable Long id) {
        log.info("GET /api/spend/transactions/{}", id);
        return ResponseEntity.ok(spendTransactionService.getSpendTransactionById(id));
    }

    /**
     * 거래 ID로 조회.
     */
    @GetMapping("/transaction-id/{transactionId}")
    public ResponseEntity<SpendTransactionResponse> getSpendTransactionByTransactionId(
            @PathVariable String transactionId
    ) {
        log.info("GET /api/spend/transactions/transaction-id/{}", transactionId);
        return ResponseEntity.ok(spendTransactionService.getSpendTransactionByTransactionId(transactionId));
    }

    /**
     * 카테고리별 조회.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SpendTransactionResponse>> getSpendTransactionsByCategory(
            @PathVariable String category
    ) {
        log.info("GET /api/spend/transactions/category/{}", category);
        return ResponseEntity.ok(spendTransactionService.getSpendTransactionsByCategory(category));
    }
}

