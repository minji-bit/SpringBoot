package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import web.mvc.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>
        , QuerydslPredicateExecutor<Board> {
       /////////JPQL///////////////////////////
    /**
     * 글번호를 인수로 받아서 인수보다 큰 레코드 삭제
     * DML 문장일 경우에는 반드시 @Modifying 선언 필수!!
     */
    @Query(value = "delete from Board b where b.bno > ?1")
    @Modifying
    void delGraterByBno(Long bno);


    /**
     * 글번호or 제목을 인수로 전달받아 그에 해당하는 레코드 검색
     */
//    @Query(value = "select b from Board b where b.bno=?1 or b.title like ?2")
    @Query(value = "select * from board where bno=?1 or title like ?2", nativeQuery = true)
    List<Board> findByBnoOrTitleTest(Long bno, String title);

    /**
     * 글번호 , 제목, 작성자에 해당하는 레코드 검색
     */
    @Query(value = "select b from Board b where b.bno=:#{#bo.bno} or b.title=:#{#bo.title} or b.author=:#{#bo.author}")
    List<Board> findByWhere(@Param("bo") Board board);

    ////////QUERY METHOD 사용법//////////////////////////////////
   //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<Board> findByBnoLessThanAndAuthor(Long bno, String author);
}
