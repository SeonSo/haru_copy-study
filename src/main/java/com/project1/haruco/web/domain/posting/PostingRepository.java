package com.project1.haruco.web.domain.posting;

import com.project1.haruco.web.domain.challenge.Challenge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Posting p set p.postingModifyOk = false where p.postingId in :postingIdList")
    int updatePostingModifyOk(List<Long> postingIdList);

    @Modifying(clearAutomatically = true)
    @Query("update Posting p set p.postingApproval = true  where p in :updatePostingId")
    int updatePostingApproval(List<Posting> updatePostingId);
}
