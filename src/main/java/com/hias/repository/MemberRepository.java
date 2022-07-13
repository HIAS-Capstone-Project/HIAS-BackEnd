package com.hias.repository;

import com.hias.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.isDeleted = false AND (m.memberName LIKE %?1% OR m.staffID LIKE %?1%)")
    Page<Member> findByMember(String keyOne, Pageable pageable);
}