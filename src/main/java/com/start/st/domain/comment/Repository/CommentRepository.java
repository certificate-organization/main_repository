package com.start.st.domain.comment.Repository;

import com.start.st.domain.comment.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByParentCommentOrderByRefAscReStepAscReLevelAsc(Comment parentComment);

    @Query("SELECT COALESCE(MAX(c.ref), 0) FROM Comment c")
    Long findMaxRef();
}
