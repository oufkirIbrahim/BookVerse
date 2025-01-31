package com.BookVerse.BookVerse.book;

import com.BookVerse.BookVerse.common.PageResponse;
import com.BookVerse.BookVerse.feedback.Feedback;
import com.BookVerse.BookVerse.file.FileUtils;
import com.BookVerse.BookVerse.history.BookTransactionHistory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toBook(BookRequest bookRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromRequest(BookRequest bookRequest, Book book);

    @Mapping(target = "rating", expression = "java(calculateRate(book.getFeedbacks()))")
    @Mapping(target = "cover", expression = "java(getImageBytes(book.getCover()))")
    BookResponse toBookResponse(Book book);

    // Custom method to calculate the average rating
    default Double calculateRate(List<Feedback> feedbacks) {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0; // Default rating if there are no feedbacks
        }
        double rate =  feedbacks.stream()
                .mapToDouble(Feedback::getRating) // Assuming Feedback has a `getRating()` method
                .average()
                .orElse(0.0);
        return Math.round(rate * 100.0) / 100.0; // Round to 2 decimal places
    }

    // Custom method to convert the image to a byte array
    default byte[] getImageBytes(String cover) {
        return FileUtils.readFileFromLocation(cover);
    }

    List<BookResponse> toBookResponses(List<Book> content);

    default PageResponse<BookResponse> toPageResponse(Page<Book> page) {
        return PageResponse.<BookResponse>builder()
                .content(toBookResponses(page.getContent()))
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }

    @Mapping(source = "book.id", target = "id")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.description", target = "description")
    @Mapping(source = "book.isbn", target = "isbn")
    BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory bookTransactionHistory);

    default PageResponse<BorrowedBookResponse> toBorrowedBookPageResponse(Page<BookTransactionHistory> page) {
        return PageResponse.<BorrowedBookResponse>builder()
                .content(page.getContent().stream()
                        .map(this::toBorrowedBookResponse)
                        .collect(Collectors.toList()))
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
