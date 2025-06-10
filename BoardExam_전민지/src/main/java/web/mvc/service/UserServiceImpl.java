package web.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.User;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User loginCheck(User user) throws BasicException {
        User foundUser= userRepository.findById(user.getUserId()).orElseThrow(() -> new BasicException(ErrorCode.NOTFOUND_ID));
        if(!foundUser.getPwd().equals(user.getPwd())) throw new BasicException(ErrorCode.WRONG_PASS);
        return foundUser;
    }
}
