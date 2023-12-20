package kr.co.studyhubinu.studyhubserver.bookmark.service;

import kr.co.studyhubinu.studyhubserver.bookmark.domain.BookmarkEntity;
import kr.co.studyhubinu.studyhubserver.bookmark.repository.BookmarkRepository;
import kr.co.studyhubinu.studyhubserver.studypost.domain.StudyPostEntity;
import kr.co.studyhubinu.studyhubserver.studypost.repository.StudyPostRepository;
import kr.co.studyhubinu.studyhubserver.support.fixture.BookmarkEntityFixture;
import kr.co.studyhubinu.studyhubserver.support.fixture.StudyPostEntityFixture;
import kr.co.studyhubinu.studyhubserver.support.fixture.UserEntityFixture;
import kr.co.studyhubinu.studyhubserver.user.domain.UserEntity;
import kr.co.studyhubinu.studyhubserver.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudyPostRepository studyPostRepository;

    @InjectMocks
    private BookmarkService bookmarkService;

    @Test
    void 북마크_아이디로_북마크가_조회되지_않는다면_북마크를_생성한다() {
        // given
        Long bookmarkUserId = 1L;
        Long bookmarkPostId = 3L;
        UserEntity user = UserEntityFixture.DONGWOO.UserEntity_생성(bookmarkUserId);
        StudyPostEntity post = StudyPostEntityFixture.SQLD.studyPostEntity_생성(bookmarkPostId);

        given(userRepository.findById(bookmarkUserId)).willReturn(Optional.of(user));
        given(studyPostRepository.findById(bookmarkPostId)).willReturn(Optional.of(post));
        given(bookmarkRepository.findByUserIdAndPostId(bookmarkUserId, bookmarkPostId)).willReturn(Optional.empty());
        ArgumentCaptor<BookmarkEntity> captor = ArgumentCaptor.forClass(BookmarkEntity.class);

        // when
        boolean result = bookmarkService.doBookMark(bookmarkUserId, bookmarkPostId);
        // then
        verify(bookmarkRepository, times(1)).save(captor.capture());
        BookmarkEntity bookmark = captor.getValue();
        assertAll(
                () -> assertTrue(result),
                () -> assertEquals(bookmark.getUserId(), bookmarkUserId),
                () -> assertEquals(bookmark.getPostId(), bookmarkPostId)
        );
    }

    @Test
    void 북마크_아이디로_북마크_조회되면_북마크를_삭제한다() {
        // given
        Long bookmarkUserId = 1L;
        Long bookmarkPostId = 3L;
        UserEntity user = UserEntityFixture.DONGWOO.UserEntity_생성(bookmarkUserId);
        StudyPostEntity post = StudyPostEntityFixture.SQLD.studyPostEntity_생성(bookmarkPostId);
        BookmarkEntity bookmark = BookmarkEntityFixture.BOOKMARK_POST1.bookMarkEntity_생성(bookmarkPostId, bookmarkUserId);

        given(userRepository.findById(bookmarkUserId)).willReturn(Optional.of(user));
        given(studyPostRepository.findById(bookmarkPostId)).willReturn(Optional.of(post));
        given(bookmarkRepository.findByUserIdAndPostId(bookmarkUserId, bookmarkPostId)).willReturn(Optional.of(bookmark));
        ArgumentCaptor<BookmarkEntity> captor = ArgumentCaptor.forClass(BookmarkEntity.class);

        // when
        boolean result = bookmarkService.doBookMark(bookmarkUserId, bookmarkPostId);
        // then
        verify(bookmarkRepository, times(1)).delete(captor.capture());
        BookmarkEntity actualBookmark = captor.getValue();
        assertAll(
                () -> assertFalse(result),
                () -> assertEquals(actualBookmark.getUserId(), bookmarkUserId),
                () -> assertEquals(actualBookmark.getPostId(), bookmarkPostId)
        );
    }

}
