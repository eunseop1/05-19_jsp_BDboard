package kr.human.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.human.board.dao.BoardDAO;
import kr.human.board.dao.BoardDAOImpl;
import kr.human.board.vo.BoardVO;
import kr.human.board.vo.PagingVO;
import kr.human.jdbc.JDBCUtill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardServiceImpl implements BoardService{
	//-------------------------------------------------------------------------
	// 싱글톤으로 작성
	private static BoardService instance = new BoardServiceImpl();
	private BoardServiceImpl() {}
	public static BoardService getInstance() { return instance; }
	//-------------------------------------------------------------------------
	@Override
	public BoardVO selectByIdx(int idx, boolean isClick) {
		log.info("BoardService selectByIdx 호출 : " + idx + ", " + isClick);
		BoardVO vo = null;
		Connection conn = null;
		BoardDAO   dao = null;
		try {
			conn = JDBCUtill.getConnection();
			conn.setAutoCommit(false);
			dao = BoardDAOImpl.getInstance();
			//--------------------------------------------------------------
			vo = dao.selectByIdx(conn, idx);
			if(vo!=null && isClick) { // 글이 존재하면서 isClick이 참이면 조회수 증가
				vo.setClickCount(vo.getClickCount()+1); // 나의 조회수 증가
				dao.increment(conn, idx); // DB의 조회수 증가
			}
			//--------------------------------------------------------------
			conn.commit();
		}catch (SQLException e) {
			JDBCUtill.rollback(conn);
			e.printStackTrace();
		}finally {
			JDBCUtill.close(conn);
		}
		log.info("BoardService selectByIdx 리턴 : " + vo);
		return vo;
	}
	@Override
	public PagingVO<BoardVO> selectList(int currentPage, int pageSize, int blockSize) {
		log.info("BoardService selectList 호출 : " + currentPage + ", " + pageSize + ", " + blockSize);
		PagingVO<BoardVO> pagingVO = null;
		Connection conn = null;
		BoardDAO   dao = null;
		try {
			conn = JDBCUtill.getConnection();
			conn.setAutoCommit(false);
			dao = BoardDAOImpl.getInstance();
			//--------------------------------------------------------------
			int totalCount = dao.selectCount(conn); // 전체 개수 얻기
			pagingVO = new PagingVO<>(totalCount, currentPage, pageSize, blockSize); // 페이지 계산
			// 리스트 얻기
			List<BoardVO> list = dao.selectList(conn, pagingVO.getStartNo(), pagingVO.getPageSize());
			// 리스트 넣기
			pagingVO.setList(list);
			//--------------------------------------------------------------
			conn.commit();
		}catch (SQLException e) {
			JDBCUtill.rollback(conn);
			e.printStackTrace();
		}finally {
			JDBCUtill.close(conn);
		}
		
		log.info("BoardService selectList 리턴 : " + pagingVO);
		return pagingVO;
	}
	@Override
	public void insert(BoardVO vo) {
		log.info("BoardService insert 호출 : " + vo);
		Connection conn = null;
		BoardDAO   dao = null;
		try {
			conn = JDBCUtill.getConnection();
			conn.setAutoCommit(false);
			dao = BoardDAOImpl.getInstance();
			//--------------------------------------------------------------
			if(vo!=null) {
				dao.insert(conn, vo);
			}
			//--------------------------------------------------------------
			conn.commit();
		}catch (SQLException e) {
			JDBCUtill.rollback(conn);
			e.printStackTrace();
		}finally {
			JDBCUtill.close(conn);
		}
	}
	@Override
	public void update(BoardVO vo) {
		log.info("BoardService update 호출 : " + vo);
		Connection conn = null;
		BoardDAO   dao = null;
		try {
			conn = JDBCUtill.getConnection();
			conn.setAutoCommit(false);
			dao = BoardDAOImpl.getInstance();
			//--------------------------------------------------------------
			// 비밀번호가 같을때만 수정을 처리한다.
			if(vo!=null) {
				if(dao.pwdCheck(conn, vo.getIdx(), vo.getPassword())==1) {
					dao.update(conn, vo);
				}
			}
			//--------------------------------------------------------------
			conn.commit();
		}catch (SQLException e) {
			JDBCUtill.rollback(conn);
			e.printStackTrace();
		}finally {
			JDBCUtill.close(conn);
		}	
	}
	@Override
	public void delete(BoardVO vo) {
		log.info("BoardService delete 호출 : " + vo);
		Connection conn = null;
		BoardDAO   dao = null;
		try {
			conn = JDBCUtill.getConnection();
			conn.setAutoCommit(false);
			dao = BoardDAOImpl.getInstance();
			//--------------------------------------------------------------
			// 비밀번호가 같을때만 삭제를 처리한다.
			if(vo!=null) {
				if(dao.pwdCheck(conn, vo.getIdx(), vo.getPassword())==1) {
					dao.delete(conn, vo.getIdx());
				}
			}
			//--------------------------------------------------------------
			conn.commit();
		}catch (SQLException e) {
			JDBCUtill.rollback(conn);
			e.printStackTrace();
		}finally {
			JDBCUtill.close(conn);
		}		
	}

}
