package com.AIagnet.agent.spend.service;

import com.AIagnet.agent.spend.dto.request.SpendTransactionSearchRequest;
import com.AIagnet.agent.spend.dto.response.SpendTransactionResponse;
import com.AIagnet.agent.spend.entity.SpendTransaction;
import com.AIagnet.agent.spend.repository.SpendTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 지출 거래 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SpendTransactionService {

    private final SpendTransactionRepository spendTransactionRepository;

    /**
     * 지출 거래 목록 조회 (검색).
     */
    public Page<SpendTransactionResponse> searchSpendTransactions(SpendTransactionSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage() != null ? request.getPage() : 0,
                request.getSize() != null ? request.getSize() : 20
        );

        Page<SpendTransaction> transactions = spendTransactionRepository.search(
                request.getStartDate(),
                request.getEndDate(),
                request.getCategory(),
                request.getSupplier(),
                request.getBuyer(),
                pageable
        );

        return transactions.map(SpendTransactionResponse::from);
    }

    /**
     * 지출 거래 상세 조회.
     */
    public SpendTransactionResponse getSpendTransactionById(Long id) {
        SpendTransaction transaction = spendTransactionRepository.findById(id)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "지출 거래를 찾을 수 없습니다."
                ));
        return SpendTransactionResponse.from(transaction);
    }

    /**
     * 거래 ID로 조회.
     */
    public SpendTransactionResponse getSpendTransactionByTransactionId(String transactionId) {
        SpendTransaction transaction = spendTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new com.AIagnet.agent.exception.BusinessException(
                        com.AIagnet.agent.exception.ErrorCode.INVALID_INPUT_VALUE,
                        "지출 거래를 찾을 수 없습니다."
                ));
        return SpendTransactionResponse.from(transaction);
    }

    /**
     * 카테고리별 조회.
     */
    public List<SpendTransactionResponse> getSpendTransactionsByCategory(String category) {
        return spendTransactionRepository.findByCategory(category).stream()
                .map(SpendTransactionResponse::from)
                .collect(Collectors.toList());
    }
}

