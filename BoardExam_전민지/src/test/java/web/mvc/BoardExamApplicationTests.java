package web.mvc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import web.mvc.domain.FreeBoard;
import web.mvc.domain.User;
import web.mvc.repository.FreeBoardRepository;
import web.mvc.repository.UserRepository;

@SpringBootTest
class BoardExamApplicationTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FreeBoardRepository freeBoardRepository;
    @Test
    @Rollback(false)
//    @Disabled
    void userInsert() {
        userRepository.save(User.builder().userId("jeon").name("전민지").pwd("1234").build());
    }

    @Test
    @Rollback(false)
//    @Disabled
    void boardInsert() {
        for (int i = 1; i <= 45; i++) {
            freeBoardRepository.save(FreeBoard.builder()
                            .subject("제목" + i)
                            .content("내용" + i)
                            .writer("User" + i)
                            .readnum(0)
                            .password("1234")
                        .build());
        }
    }

}
