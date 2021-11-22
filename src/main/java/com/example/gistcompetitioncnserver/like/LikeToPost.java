package com.example.gistcompetitioncnserver.like;

import com.example.gistcompetitioncnserver.post.Post;
import com.example.gistcompetitioncnserver.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LikeToPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @JsonIgnore
    private Post post;

    private Long userId;

    protected LikeToPost() {
    }

    public LikeToPost(Post post, Long userId) {
        this(null, post, userId);
    }

    private LikeToPost(Long likeId, Post post, Long userId) {
        this.likeId = likeId;
        this.post = post;
        this.userId = userId;
    }

    public boolean isLikedBy(User user) {
        return userId.equals(user.getId());
    }

    public Long getUserId() {
        return userId;
    }
}
