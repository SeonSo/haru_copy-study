package com.project1.haruco.web.domain.certification;

import com.project1.haruco.web.domain.member.Member;
import com.project1.haruco.web.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    boolean existsByPostingAndMember(Posting posting, Member member);
}
