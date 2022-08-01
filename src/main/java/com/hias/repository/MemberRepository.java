package com.hias.repository;

import com.hias.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.isDeleted = false AND (m.memberName LIKE %?1% OR m.staffID LIKE %?1%)")
    Page<Member> findMember(String keyOne, Pageable pageable);

    List<Member> findMemberByClientNo(Long clientNo);

    List<Member> findByClientNoAndStaffIDAndIsDeletedIsFalse(Long clientNo, String staffID);
}
