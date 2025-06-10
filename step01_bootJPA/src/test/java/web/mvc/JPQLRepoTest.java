package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
public class JPQLRepoTest {

    //    @Autowired
//    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    /**
     * 삭제하기
     */
    @Test
    @DisplayName("글번호보다 큰 레코드 삭제")
//    @Disabled
    public void delete() {
        boardRepository.delGraterByBno(150L);
    }

    /**
     * 글번호 or 제목
     */
    @Test
    @DisplayName("글번호 or 제목")
  @Disabled
    public void select() {
        boardRepository.findByBnoOrTitleTest(50L,"%2%").forEach(System.out::println);

    }

    /**
     * 글번호 , 제목 , 작성자
     */
    @Test
    @DisplayName("글번호 or 제목 or 작성자")
    @Disabled
    public void selectByBnoOrTitle() {
        boardRepository.findByWhere(Board.builder().bno(60L).author("민지100").title("제목80").build()).forEach(System.out::println);
    }
/// /////////////Query Method//////////////////////////////////
    /**
     * 글번호 , 제목
     */
    @Test
    @DisplayName("Query Method Test")
//    @Disabled
    public void queryMethod() {
        boardRepository.findByBnoLessThanAndAuthor(50L,"민지40").forEach(System.out::println);
    }





}