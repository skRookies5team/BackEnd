package com.AIagnet.agent.approval.repository;

import com.AIagnet.agent.approval.entity.ApprovalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 결재 이력 리포지토리.
 */
public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Integer> {

    /**
     * 문서 ID로 조회.
     */
    @Query("""
            SELECT h
            FROM ApprovalHistory h
            JOIN FETCH h.document
            JOIN FETCH h.approver
            WHERE h.document.documentId = :documentId
            ORDER BY h.createdAt ASC
            """)
    List<ApprovalHistory> findByDocumentId(@Param("documentId") Integer documentId);
}

