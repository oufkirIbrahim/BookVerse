package com.BookVerse.BookVerse.feedback;

import com.BookVerse.BookVerse.book.Book;
import com.BookVerse.BookVerse.book.BookRepository;
import com.BookVerse.BookVerse.common.PageResponse;
import com.BookVerse.BookVerse.exceptions.OperationNotPermittedException;
import com.BookVerse.BookVerse.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{

    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    @Override
    public Long createFeedback(CreateFeedbackDTO createFeedbackDTO, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookRepository.findById(createFeedbackDTO.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("No book found with id: " + createFeedbackDTO.getBookId())
        );
        if(book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("You can't give feedback for this book");
        }
        if(user.getId().equals(book.getOwner().getId())){
            throw new OperationNotPermittedException("You can't give feedback for your own book");
        }
        Feedback feedback = Feedback.builder()
                .book(book)
                .user(user)
                .rating(createFeedbackDTO.getRating())
                .comment(createFeedbackDTO.getComment())
                .build();
        return feedbackRepository.save(feedback).getId();
    }

    @Override
    public PageResponse<FeedbackResponseDTO> getFeedbacksByBookId(Long bookId, Integer page, Integer size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("No book found with id: " + bookId)
        );
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        return feedbackMapper.toPageResponse(feedbacks, user);
    }



}
