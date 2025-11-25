package com.AIagnet.agent.approval.repository;

import com.AIagnet.agent.approval.entity.ApprovalDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 결재 문서 리포지토리.
 */
public interface ApprovalDocumentRepository extends JpaRepository<ApprovalDocument, Integer> {

    /**
     * 직원 ID로 조회.
     */
    @Query("""
            SELECT d
            FROM ApprovalDocument d
            JOIN FETCH d.employee
            WHERE d.employee.employeeId = :employeeId
            ORDER BY d.createdAt DESC
            """)
    List<ApprovalDocument> findByEmployeeId(@Param("employeeId") Integer employeeId);

    /**
     * 상태로 조회.
     */
    @Query("""
            SELECT d
            FROM ApprovalDocument d
            JOIN FETCH d.employee
            WHERE d.status = :status
            ORDER BY d.createdAt DESC
            """)
    Page<ApprovalDocument> findByStatus(@Param("status") String status, Pageable pageable);

    /**
     * 현재 결재자로 조회.
     */
    @Query("""
            SELECT d
            FROM ApprovalDocument d
            JOIN FETCH d.employee
            JOIN FETCH d.currentApprover
            WHERE d.currentApprover.employeeId = :approverId
            ORDER BY d.createdAt DESC
            """)
    List<ApprovalDocument> findByCurrentApproverId(@Param("approverId") Integer approverId);

    /**
     * 상세 조회 (관계 엔터티 포함).
     */
    @Query("""
            SELECT d
            FROM ApprovalDocument d
            JOIN FETCH d.employee
            LEFT JOIN FETCH d.currentApprover
            WHERE d.documentId = :documentId
            """)
    Optional<ApprovalDocument> findByIdWithRelations(@Param("documentId") Integer documentId);
}

