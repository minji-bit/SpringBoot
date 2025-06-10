package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import web.mvc.domain.Board;
import web.mvc.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

//@SpringBootTest //통합테스트 +기본 commit
@DataJpaTest //기본적으로 내장 DB를 자동 설정해서 테스트를 수행한다. + 기본 rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Spring Boot에서 테스트 시 사용하는 DB 설정 방식을 .properties 설정에서 변경하지 말라는 의미
@Rollback(false)
@Slf4j
public class AssertTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    @DisplayName("사전처리...")
    public void beforeEach(){

        log.info("@BeforeEach ....");
    }

    @AfterEach
    @DisplayName("사후처리...")
    public void afterEach(){
        log.info("@AfterEach ....");
    }

    @Test
    @DisplayName("1. 게시글 저장 테스트")
    void saveBoardTest() {
        Board board = Board.builder()
                .title("첫 번째 글")
                .content("내용입니다.")
                .author("관리자")
                .build();

        Board saved = boardRepository.save(board);

        Assertions.assertNotNull(saved.getBno());
        Assertions.assertEquals("첫 번째 글", saved.getTitle());
//        Assertions.assertEquals("두 번째 글", saved.getTitle());
    }

    @Test
    @DisplayName("2. 게시글 단건 조회 테스트")
    void findByIdTest() {
        Board board = boardRepository.save(Board.builder()
                .title("조회 테스트")
                .content("내용")
                .author("사용자")
                .build());

        Optional<Board> result = boardRepository.findById(board.getBno());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("조회 테스트", result.get().getTitle());
    }

    @Test
    @DisplayName("3. 게시글 전체 조회 테스트")
    void findAllTest() {
        boardRepository.save(Board.builder().title("A").content("C").author("W1")
                .build());
        boardRepository.save(Board.builder().title("B").content("D").author("W2")
                .build());

        List<Board> boards = boardRepository.findAll();

        Assertions.assertEquals(12, boards.size());
    }

    @Test
    @DisplayName("4. 게시글 수정 테스트")
    void updateBoardTest() {
        Board board = boardRepository.save(Board.builder()
                .title("수정 전")
                .content("내용")
                .author("작성자")
                .build());

        board.setTitle("수정 후");
        Board updated = boardRepository.save(board);

        Assertions.assertEquals("수정 전", updated.getTitle());
    }

    @Test
    @DisplayName("5. 게시글 삭제 테스트")
    void deleteBoardTest() {
        Board board = boardRepository.save(Board.builder()
                .title("삭제할 글")
                .content("삭제 내용")
                .author("지우개")
                .build());

        boardRepository.delete(board);

        Optional<Board> deleted = boardRepository.findById(board.getBno());

        Assertions.assertFalse(deleted.isPresent());
    }

}
