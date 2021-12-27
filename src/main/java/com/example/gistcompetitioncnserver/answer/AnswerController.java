package com.example.gistcompetitioncnserver.answer;

import com.example.gistcompetitioncnserver.exception.CustomException;
import com.example.gistcompetitioncnserver.exception.ErrorCase;
import com.example.gistcompetitioncnserver.user.User;
import com.example.gistcompetitioncnserver.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/v1") // not used in rest api like v1 url
public class AnswerController {

    private final AnswerService answerService;
    private final UserService userService;

    @PostMapping("/posts/{postId}/answer")
    public ResponseEntity<Object> createAnswer(@PathVariable Long postId, @RequestBody AnswerRequestDto answerRequestDto,
                                             @AuthenticationPrincipal String email) {
        User user = userService.findUserByEmail2(email);

        if (!isRequestBodyValid(answerRequestDto)) {
            throw new CustomException(ErrorCase.INVAILD_FILED_ERROR);
        }

        Long answerId = answerService.createAnswer(postId, answerRequestDto, user.getId());
        return ResponseEntity.created(URI.create("/posts/" + postId + "/answer/" + answerId)).build();
    }

    private boolean isRequestBodyValid(AnswerRequestDto answerRequestDto) {
        return answerRequestDto.getContent() != null ;
    }

    @GetMapping("/posts/{postId}/answer")
    public ResponseEntity<Answer> retrieveAnswer(@PathVariable Long postId){
        return ResponseEntity.ok().body(answerService.retrieveAnswerByPostId(postId));
    }


//    @DeleteMapping("/posts/{postId}/answer/{answerId}")
//    public void deleteAnswer(@PathVariable Long postId,@PathVariable Long answerId)){
//        answerService.deleteAnswer(answerId);
//    }




}
