package com.example.gistcompetitioncnserver.post;


import com.example.gistcompetitioncnserver.comment.Comment;
import com.example.gistcompetitioncnserver.comment.CommentRepository;
import com.example.gistcompetitioncnserver.user.User;
import com.example.gistcompetitioncnserver.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long createPost(PostRequestDto postRequestDto, Long userId) {
        Long result = postRepository.save(
                new Post(postRequestDto.getTitle(),
                        postRequestDto.getDescription(),
                        postRequestDto.getCategory(),
                        userId)
        ).getId();

        return result;
    }

    public List<Post> retrieveAllPost() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Post> retrievePostsByUserId(Long user_id) {
        return postRepository.findByUserId(Sort.by(Sort.Direction.DESC, "id"), user_id);
    }

    public Optional<Post> retrievePost(Long id) {
        return postRepository.findById(id);
    }

    public Long getPageNumber() {
        return postRepository.count();
    }

    public List<Post> getPostsByCategory(String categoryName) {
        return postRepository.findByCategory(Sort.by(Sort.Direction.DESC, "id"), categoryName);
    }

    public void updateAnsweredPost(Long id) {
        Post post = postRepository.getById(id);
        post.setAnswered(true);
        postRepository.save(post);
    }

    @Transactional // like도 지워야함
    public void deletePost(Long id) {
        List<Long> commentIds = new ArrayList<>();

        for (Comment comment : commentRepository.findByPostId(id)) {
            commentIds.add(comment.getId());
        }

        if (!commentIds.isEmpty()) {
            commentRepository.deleteAllByPostIdInQuery(commentIds);
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public boolean like(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        return post.applyLike(user);
    }

    public int countOfLike(Long postId) {
        Post post = postRepository.getById(postId);
        return post.getLikes().size();
    }

    public boolean checkLikePost(Long postId, Long userId) {
        Post post = postRepository.getById(postId);
        List<LikeToPost> likes = post.getLikes();
        for (LikeToPost like : likes) {
            if (like.isLikedBy(userId)) {
                return true;
            }
        }
        return false;
    }
}
