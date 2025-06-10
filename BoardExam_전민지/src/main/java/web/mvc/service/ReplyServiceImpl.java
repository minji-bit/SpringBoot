package web.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Reply;
import web.mvc.repository.ReplyRepository;
@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public void delete(Long id) {
        replyRepository.deleteById(id);
    }

    @Override
    public void insert(Reply reply) {
        replyRepository.save(reply);
    }
}
