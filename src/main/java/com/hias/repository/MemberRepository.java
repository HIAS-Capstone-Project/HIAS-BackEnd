package com.hias.repository;

import com.hias.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.isDeleted = false AND (m.memberName LIKE %?1% OR m.staffID LIKE %?1%)")
    Page<Member> findMember(String keyOne, Pageable pageable);

    List<Member> findByIsDeletedIsFalse();

    List<Member> findMemberByClientNoAndIsDeletedIsFalse(Long clientNo);

    List<Member> findByClientNoAndStaffIDAndIsDeletedIsFalse(Long clientNo, String staffID);

    Optional<Member> findByMemberNoAndIsDeletedIsFalse(Long memberNo);

    @Query("select m from Member m " +
            "where m.isDeleted = false " +
            "and (:searchValue is null " +
            "or lower(m.staffID) like concat('%',lower(trim(:searchValue)),'%') " +
            "or lower(m.memberName) like concat('%',lower(trim(:searchValue)),'%'))")
    Page<Member> findAllBySearchValue(String searchValue, Pageable pageable);
}
