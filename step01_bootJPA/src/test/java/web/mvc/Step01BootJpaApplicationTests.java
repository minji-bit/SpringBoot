package web.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class Step01BootJpaApplicationTests {
    @Test
    @DisplayName("사전처리")
    @BeforeEach
    void before(){
        log.info("before.....");
    }
    @Test
    @DisplayName("사후처리")
    @AfterEach
    void after(){
        log.info("after.....");
    }

    @Test
    @DisplayName("등록하기")
    void contextLoads() {
     //   System.out.println("등록하기 테스트입니다~~");
        log.info("등록하기 테스트입니다~~");
    }

}
