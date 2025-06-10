package web.mvc.service;

import org.springframework.stereotype.Service;
import web.mvc.domain.Board;

import java.util.List;

public interface BoardService {
    List<Board> findAll();
}
