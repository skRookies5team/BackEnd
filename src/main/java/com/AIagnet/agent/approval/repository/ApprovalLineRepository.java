package com.AIagnet.agent.approval.repository;

import com.AIagnet.agent.approval.entity.ApprovalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 결재 라인 리포지토리.
 */
public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Integer> {

    /**
     * 문서 ID로 조회 (순서대로).
     */
    @Query("""
            SELECT l
            FROM ApprovalLine l
            JOIN FETCH l.document
            JOIN FETCH l.approver
            WHERE l.document.documentId = :documentId
            ORDER BY l.sequenceNo ASC
            """)
    List<ApprovalLine> findByDocumentIdOrderBySequenceNo(@Param("documentId") Integer documentId);
}

