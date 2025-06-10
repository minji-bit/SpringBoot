package web.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.FreeBoard;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.FreeBoardRepository;

import java.util.List;
@Service
public class FreeBoardServiceImpl implements FreeBoardService {
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Override
    public List<FreeBoard> selectAll() {
        List<FreeBoard> list = freeBoardRepository.findAll();
        return list;
    }

    @Override
    public Page<FreeBoard> selectAll(Pageable pageable) {
        Page<FreeBoard> page = freeBoardRepository.findAll(pageable);
        return page;
    }
    @Transactional
    @Override
    public void insert(FreeBoard board) {
        freeBoardRepository.save(board);

    }
    @Transactional
    @Override
    public FreeBoard selectBy(Long bno, boolean state) {
        FreeBoard board= freeBoardRepository.findById(bno).orElseThrow(() -> new BasicException(ErrorCode.FAILED_DETAIL));
        if(state) board.setReadnum(board.getReadnum()+1);
        return board;
    }

    @Transactional
    @Override
    public FreeBoard update(FreeBoard board) {
        FreeBoard outFreeBoard= selectBy(board.getBno(),false);

        if(outFreeBoard==null) {throw new BasicException(ErrorCode.FAILED_UPDATE);};
        if(!outFreeBoard.getPassword().equals(board.getPassword())) {throw new BasicException(ErrorCode.WRONG_PASS);};

        outFreeBoard.setSubject(board.getSubject());
        outFreeBoard.setContent(board.getContent());

        return outFreeBoard;
    }

    @Transactional
    @Override
    public void delete(Long bno, String password) {
        FreeBoard outFreeBoard=selectBy(bno,false);
        if(outFreeBoard==null) {throw new BasicException(ErrorCode.FAILED_DELETE);};
        if(!outFreeBoard.getPassword().equals(password)) {throw new BasicException(ErrorCode.WRONG_PASS);};
        freeBoardRepository.deleteById(bno);
    }
}
