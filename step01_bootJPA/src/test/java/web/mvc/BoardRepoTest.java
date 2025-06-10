package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import web.mvc.domain.Board;
import web.mvc.repository.BoardRepository;

import java.util.List;

import static java.lang.System.out;

//@SpringBootTest //통합테스트 +기본 commit
@DataJpaTest //기본적으로 내장 DB를 자동 설정해서 테스트를 수행한다. + 기본 rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Spring Boot에서 테스트 시 사용하는 DB 설정 방식을 .properties 설정에서 변경하지 말라는 의미
@Rollback(false)
@Slf4j
public class BoardRepoTest {

    //    @Autowired
//    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("초기화 검색")
    @Disabled
    public void test() {
        log.info("boardRepo:{}", boardRepository);
        //log.info("boardService:{}", boardService);
    }

    /**
     * 레코드 추가
     */
    @Test
    @DisplayName("레코드 추가")
    @Disabled
    public void insert() {
        //save 메소드는 등록 or 수정
        //저장
        /*  boardRepository.save(Board.builder().title("제목1").content("내용1").author("민지1").build());*/
        //pk값이 이미 존재하는 값을 넣으면 등록이 아니라 수정이 된다.
//       boardRepository.save(Board.builder().bno(1L).title("수정1").content("내용-수정1").author("민지1").build());
//        System.out.println("insert 성공...");

        for (int i = 2; i <= 200; i++) {
            boardRepository.save(Board.builder().title("제목" + i).content("내용" + i).author("민지" + i).build());
        }
        out.println("insert 성공...");
    }// 메소드가 끝나면 commit이 된다!! (그래서 update 문은 나중에 날라간것!!)

    /// ////////////////////////////////////////////
    /**
     * 전체검색
     */
    @Test
    @DisplayName("전체검색")
    public void selectAll() {
        List<Board> list = boardRepository.findAll();
        list.forEach(System.out::println);
    }

    /**
     * pk를 조건으로 검색
     */
    @Test
    @DisplayName("pk 조건검색")
    @Disabled
//    @Disabled
    public void select() {
      //  boardRepository.findById(255L).ifPresent(System.out::println);
        Board board = boardRepository.findById(5L).orElse(null);
        if (board != null) {
            out.println(board);
            board.setContent("졸립다.11");
        }

    }

    /**
     * 수정하기
     */
    @Test
    @DisplayName("수정하기")
    @Disabled
    public void update() {

    }

    /**
     * 삭제하기
     */
    @Test
    @DisplayName("삭제하기")
    @Disabled
    public void delete() {
        boardRepository.deleteById(5L);
    }

    /// //////////////////////////
    /**
     * 페이징 처리 하기
     */
    @Test
    @DisplayName("페이징처리")
    @Disabled
    public void page() {
        Pageable pageable =
                PageRequest.of(18,8, Sort.Direction.DESC,"bno");
        Page<Board> page = boardRepository.findAll(pageable);

        System.out.println("-----------------------");
        System.out.println("page.getNumber() = " + page.getNumber());
        System.out.println("page.getSize() = " + page.getSize());
        System.out.println("page.getTotalPages() = " + page.getTotalPages());
        System.out.println("page.previousPageable() = " + page.previousPageable());
        System.out.println("page.nextPageable() = " + page.nextPageable());

        System.out.println("page.isFirst() = " + page.isFirst());
        System.out.println("page.isLast() = " + page.isLast());
        System.out.println("page.hasNext() = " + page.hasNext());
        System.out.println("page.hasPrevious() = " + page.hasPrevious());

        System.out.println("****************************************");
        List<Board> list = page.getContent();
        list.forEach(board -> System.out.println(board));


        System.out.println("----------------------------");

    }

}