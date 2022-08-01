package com.project1.haruco.web.domain.pointHistory;

import com.project1.haruco.web.domain.posting.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    PointHistory findByPosting(Posting posting);
}
