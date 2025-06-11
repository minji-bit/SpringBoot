package web.mvc;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Board;
import web.mvc.domain.QBoard;
import web.mvc.dto.BoardDTO;
import web.mvc.repository.BoardRepository;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest //통합테스트 +기본 commit
@Slf4j
public class QueryDSLRepoTest {
    /**
     * @RequiredArgsConstructor는 테스트 클래스에서 동작하지 않는다
     * @RequiredArgsConstructor는 Lombok이 생성자 코드를 만들어 주는 기능임
     *
     * Spring이 관리하는 Bean에는 생성자 주입이 잘 되지만,
     * 테스트 클래스는 Spring이 직접 생성자 주입을 하지 않는다.
     *
     * 즉, SpringBootTest 환경에서는 필드 주입 (@Autowired) 또는 생성자 명시만 정상 작동한다,
     *
     * 참고 :https://www.notion.so/RequiredArgsConstructor-20da7a6c42ce8002a44fc297e1b93293
     * */
    @Autowired
    private JPAQueryFactory jpaQueryFactory; //주입받기 위해서는 통합테스트로만 가능!!
    @Autowired
    private BoardRepository boardRepository;
    @Test
    @Disabled
    public void init(){
        log.info("jpaQueryFactory={}",jpaQueryFactory);
        log.info("boardRepository={}",boardRepository);
    }
    /**
     * Board 겁색
     */
    @Test
    @Disabled
    public void queryDSL01(){
        QBoard board = QBoard.board;
        List<Board> list =jpaQueryFactory.selectFrom(board).fetch();
        System.out.println("--1.board 검색--");
        list.forEach(System.out::println);
    }
    /**
     * 조건검색
     */
    @Test
    @Disabled
    public void queryDSL02(){
        QBoard board = QBoard.board;
        List<Board> list =jpaQueryFactory.selectFrom(board).
                        where(board.bno.lt(30L)
                                .or(board.title.eq("제목68"))).fetch();
        System.out.println("--2.board 조건검색--");
        list.forEach(System.out::println);
    }

    /**
     * Board 삭제
     */
    @Test
    @Transactional//단위테스트는 기본이 rollback 이다.
    @Rollback(false)
    @Disabled
    public void queryDSL03(){
        QBoard board = QBoard.board;
        long re= jpaQueryFactory.delete(board).where(board.bno.lt(10L)).execute();
        log.info("--3.board 삭제--");
        System.out.println(re);
    }

    /**
     * Board 수정
     */
    @Test
    @Transactional//단위테스트는 기본이 rollback 이다.
    @Rollback(false)
    @Disabled
    public void queryDSL04(){
        QBoard board = QBoard.board;
        long re= jpaQueryFactory.update(board)
                .set(board.author,"얼짱")
                .set(board.title,"수정된 제목")
                .set(board.updateDate, LocalDateTime.now())  // update() 사용하면 updateDate 직접 수정해줘야 함!!
                .where(board.bno.eq(11L)).execute();


        log.info("--4.board 수정 결과--");
        System.out.println(re);
    }
    /// ///////////////QuerydslPredicateExecutor 사용하기///////////////////////////////
    /**
     * interface에 QueryPredicateExecutor<> 상속받는다.
     *   - QueryPredicateExecutor안에서 제공하는 메소드를 사용해서 자바중심으로 조건(Predicate)을 만들수 있다.
     *   -Spring Data JPA + QueryDSL을 접목한 형태로 Repository에서 바로 QueryDSL의
     *   `Predicate`를 실행할 수 있도록 지원한다.
     *   - JPAQueryFactory 없이 간단하게 Predicate로 해결
     *   ex)  ~.findAll(Predicate p)
     *
     *   참고 : https://www.notion.so/QuerydslPredicateExecutor-T-208a7a6c42ce80b290bde247b33a49ef?pvs=12
     * */
    @Test
    @Disabled
    public void queryDSL05(){
        QBoard board = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder(); //Predicate를 상속함!!

        //1)
//        builder.and(board.bno.eq(11L));
//        builder.or(board.title.like("%2%"));

        //2) insert_date between ? and ?
//        LocalDateTime from = LocalDateTime.of(2025, 6, 1,0,0,0);
//        LocalDateTime to = LocalDateTime.of( 2025, 6, 5,12,0,0);
//        builder.and(board.insertDate.between(from, to)); //insert_date between ? and ?

        //3)
//        builder.and(board.author.eq("user1")); //대소문자구분한다.

        //4)
        //builder.and(board.author.equalsIgnoreCase("user9"));  //대소문자 구분안한다.

        //5)
        //builder.and(board.author.toUpperCase().eq("user9".toUpperCase()));

        //6)
//        builder.and(board.author.toUpperCase().eq("user9".toUpperCase())).or(board.bno.gt(140L));

        Iterable<Board> it=boardRepository.findAll(builder);

        //Iterable 를 List형태로 변환 하고 싶다.
       List<Board> list= Lists.newArrayList(it);

       list.forEach(System.out::println);

    }
    //////////////////////////////////QuerydslPredicateExecutor 와 JPAQueryFactory 비교////////
    /**
     * 제목에 키워드가 포함되고, 작성자가 특정인인 게시글 조회 - BooleanBuilder이용
     * */
    @Test
    public void queryDSL06(){
        String titleKeyword = "제목10";
        String authorName = null;
        QBoard board = QBoard.board;

        //BooleanBuilder는 여러 조건을 동적으로 조합할 때 매우 유용
        BooleanBuilder builder = new BooleanBuilder();

        if (titleKeyword != null && !titleKeyword.isEmpty()) {
            builder.and(board.title.containsIgnoreCase(titleKeyword));
        }

        if (authorName != null && !authorName.isEmpty()) {
            builder.and(board.author.eq(authorName));
        }

        Iterable<Board> it = boardRepository.findAll(builder);
    }

    /**
     * 제목에 키워드가 포함되고, 작성자가 특정인인 게시글 조회 -
     *  jpaQueryFactory의 where절에 직접 조건식 사용
     * */
    @Test
    public void queryDSL07(){
        String titleKeyword = "제목10";
        String authorName = null;

        QBoard board = QBoard.board;

        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .where(
                        titleKeyword != null && !titleKeyword.isEmpty() ? board.title.containsIgnoreCase(titleKeyword) : null,
                        authorName != null && !authorName.isEmpty() ? board.author.eq(authorName) : null
                )
                .fetch();
    }
    /**
     * 제목에 키워드가 포함되고, 작성자가 특정인인 게시글 조회 - List<BooleanExpression>
     * */
    @Test
    public void queryDSL08(){
        String titleKeyword = "제목10";
        String authorName = null;

        QBoard board = QBoard.board;

        // 동적 조건 조립 (null 체크 후 where절에 넣기 위해)
        List<BooleanExpression> conditions = new ArrayList<>();

        if (titleKeyword != null && !titleKeyword.isEmpty()) {
            conditions.add(board.title.containsIgnoreCase(titleKeyword));
        }

        if (authorName != null && !authorName.isEmpty()) {
            conditions.add(board.author.eq(authorName));
        }

// 조건 배열을 가변 인자로 넘김
        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .where(conditions.toArray(new BooleanExpression[0]))
                .fetch();
    }
    ///////////////////////////////////////////////
    /// /////////////////////////////////////////////////////////////////////
    /**
     * QueryDSL의 Projections
     *  : QueryDSL의 Projections는 쿼리 결과를 DTO에 매핑할 때 사용하는 도구
     * */
    @Test
    public void translateDTO(){
        QBoard board = QBoard.board;
        List<BoardDTO>  list =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        BoardDTO.class,
                                        board.bno, board.title,
                                        board.author,
                                        board.content,
                                        board.insertDate,
                                        board.updateDate)
                        )
                        .from(board)
                        .fetch();

        list.forEach(b->System.out.println(b));
    }


}