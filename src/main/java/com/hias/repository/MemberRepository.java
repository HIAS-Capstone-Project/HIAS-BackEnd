package com.hias.repository;

import com.hias.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByMemberNameContainingAndIsDeletedIsFalseOrStaffIDContainingAndIsDeletedIsFalse(String keyOne, String keyTwo, Pageable pageable);
}
