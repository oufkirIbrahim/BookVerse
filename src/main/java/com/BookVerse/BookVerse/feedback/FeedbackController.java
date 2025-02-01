package com.BookVerse.BookVerse.feedback;

import com.BookVerse.BookVerse.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Feedback API")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<Long> createFeedback(@Valid @RequestBody CreateFeedbackDTO createFeedbackDTO, Authentication connectedUser){
        return ResponseEntity.ok(feedbackService.createFeedback(createFeedbackDTO, connectedUser));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<FeedbackResponseDTO>> getFeedbacksByBookId(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByBookId(bookId, page, size, connectedUser));
    }
}
