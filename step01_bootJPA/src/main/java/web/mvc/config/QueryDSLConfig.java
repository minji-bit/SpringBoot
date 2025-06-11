package web.mvc.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //환경설정을 돕는 전용 클래스 ( 서버를 start 할 때 @Configuration 안에 있는 @bean을 실행해서 bean 으로 등록한다.
@Slf4j
public class QueryDSLConfig {
    @PersistenceContext //EntityManagerFactory 로부터 EntityManager를 주입받는다.
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
       log.info("JPAQueryFactory() call...");
       log.info("EntityManager={}", entityManager);
        return new JPAQueryFactory(entityManager);
    }
}
