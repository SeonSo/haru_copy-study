package com.project1.haruco.web.domain.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {


    List<Posting> findByChallengeAndPostingStatusTrueOrderByCreatedAtDesc(Challenge challenge, Pageable pageable);
}
