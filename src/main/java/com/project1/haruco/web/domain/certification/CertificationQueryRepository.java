package com.project1.haruco.web.domain.certification;

import com.project1.haruco.web.dto.query.certification.CertificationQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CertificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CertificationQueryDto> findAllByPosting(Long challengeId){
        return queryFactory.select(new QCertificationQueryDto(
                certification.posting.postingId,
                certification.member.memberId))
                .from(certification)
                .join(certification.posting)
                .where(certification.posting.challengeId.eq(challengeId))
                .fetch();
    }
}
