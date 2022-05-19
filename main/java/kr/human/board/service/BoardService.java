package kr.human.board.service;

import kr.human.board.vo.BoardVO;
import kr.human.board.vo.PagingVO;

public interface BoardService {
	// 1개 얻기 --> 내용 보기/수정하기/삭제하기에 사용
	BoardVO selectByIdx(int idx, boolean isClick); 
	// isClick: 조회수 증가 여부 (내용 볼때만 증가, 수정이나 삭제에는 증가하면 안됀다)
	
	// 1 페이지 얻기 --> 목록 보기에 사용
	PagingVO<BoardVO> selectList(int currentPage, int pageSize, int blockSize);
	
	// 저장 --> 저장할때 쓴다
	void insert(BoardVO vo);
	
	// 수정 --> 수정할때 쓴다
	void update(BoardVO vo);

	// 삭제 --> 삭제할때 쓴다
	void delete(BoardVO vo);
}
