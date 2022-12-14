package com.project1.haruco.web.domain.challenge;

import com.project1.haruco.util.RepositoryHelper;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project1.haruco.web.domain.challenge.QChallenge.challenge;
import static com.project1.haruco.web.domain.challengeRecord.QChallengeRecord.challengeRecord;

@RequiredArgsConstructor
@Repository
public class ChallengeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<Challenge> findAllByWord(String words, Pageable page){
        List<Challenge> challengeList = queryFactory
                .selectFrom(challenge)
                .distinct()
                .join(challengeRecord).on(challenge.challengeId.eq(challengeRecord.challenge.challengeId),
                        challengeRecord.challengeRecordStatus.isTrue())
                .where(
                        challengeRecord.challengeRecordStatus.isTrue(),
                        challenge.challengeStatus.isTrue(),
                        challenge.challengeProgress.lt(3L),
                        challenge.categoryName.ne(CategoryName.OFFICIAL),
                        challenge.challengeTitle.contains(words))
                .orderBy(challenge.challengeStartDate.asc())
                .offset(page.getOffset())
                .limit(page.getPageSize() + 1)
                .fetch();

        return RepositoryHelper.toSlice(challengeList, page);
    }

    public List<Challenge> findAllByOfficialChallenge() {
        return queryFactory
                .selectFrom(challenge)
                .where(challenge.challengeStatus.isTrue(),
                        challenge.challengeProgress.eq(1L),
                        challenge.categoryName.eq(CategoryName.OFFICIAL),
                        challenge.challengePassword.eq(""))
                .fetch();
    }

    public Slice<Challenge> findAllBySearch(String word,
                                            String categoryName,
                                            int period,
                                            int progress,
                                            Pageable page) {
        List<Challenge> challengeList = queryFactory
                .selectFrom(challenge)
                .distinct()
                .join(challengeRecord).on(challengeRecord.challengeRecordStatus.isTrue(),
                        challenge.challengeId.eq(challengeRecord.challenge.challengeId))
                .where(
                        predicateByCategoryNameAndPeriod(word, categoryName, progress, String.valueOf(period)))
                .orderBy(challenge.challengeProgress.asc(),
                        challenge.challengeStartDate.asc())
                .offset(page.getOffset())
                .limit(page.getPageSize() + 1)
                .fetch();

        return RepositoryHelper.toSlice(challengeList, page);
    }
    private Predicate[] predicateByCategoryNameAndPeriod(String word,
                                                         String categoryName,
                                                         int progress,
                                                         String period) {
        Predicate[] predicates;
        if (word.equals("ALL")) {
            if (progress == 0) {
                if (!categoryName.equals("ALL") && period.equals("0")) { // ????????????o ??????x
                    predicates = new Predicate[]{
                            challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName))
                    };
                } else if (!categoryName.equals("ALL")) { // ????????????o, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName)),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period))
                    };
                } else if (period.equals("0")) { // ????????????x, ??????x
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.ne(CategoryName.OFFICIAL)
                    };
                } else { // ????????????x, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period))
                    };
                }
            } else {
                Long longNum = (long) progress;
                if (!categoryName.equals("ALL") && period.equals("0")) { // ????????????o ??????x
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName))};
                } else if (!categoryName.equals("ALL")) { // ????????????o, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName)),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period))};
                } else if (period.equals("0")) { // ????????????x, ??????x
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.ne(CategoryName.OFFICIAL)};
                } else { // ????????????x, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period))};
                }
            }
        } else {
            if (progress == 0) {
                if (!categoryName.equals("ALL") && period.equals("0")) { // ????????????o ??????x
                    predicates = new Predicate[]{
                            challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName)),
                            challenge.challengeTitle.contains(word)
                    };
                } else if (!categoryName.equals("ALL")) { // ????????????o, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName)),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period)),
                            challenge.challengeTitle.contains(word)
                    };
                } else if (period.equals("0")) { // ????????????x, ??????x
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.challengeTitle.contains(word)
                    };
                } else { // ????????????x, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.lt(3L),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period)),
                            challenge.challengeTitle.contains(word)
                    };
                }
            } else {
                Long longNum = (long) progress;
                if (!categoryName.equals("ALL") && period.equals("0")) { // ????????????o ??????x
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName)),
                            challenge.challengeTitle.contains(word)
                    };
                } else if (!categoryName.equals("ALL")) { // ????????????o, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.eq(CategoryName.valueOf(categoryName)),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period)),
                            challenge.challengeTitle.contains(word)
                    };
                } else if (period.equals("0")) { // ????????????x, ??????x
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.challengeTitle.contains(word)
                    };
                } else { // ????????????x, ??????o
                    predicates = new Predicate[]{challenge.challengeStatus.isTrue(),
                            challenge.challengeProgress.eq(longNum),
                            challenge.categoryName.ne(CategoryName.OFFICIAL),
                            challenge.tag.eq(getPeriodString(period)),
                            challenge.challengeTitle.contains(word)
                    };
                }
            }
        }
        return predicates;
    }

    private String getPeriodString(String period) {
        return period + "???";
    }

    public Optional<Challenge> findById(Long challengeId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(challenge)
                .join(challenge.member).fetchJoin()
                .where(challenge.challengeId.eq(challengeId))
                .fetchOne());
    }
}
