package com.ssafy.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.spring.board.dto.BoardDto;
import com.ssafy.spring.board.dto.CommentDto;
import com.ssafy.spring.board.service.BoardService;
import com.ssafy.spring.member.dto.MemberDto;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService service;

	@Autowired
	public BoardController(BoardService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		List<BoardDto> list = service.list();
		mav.addObject("articles", list);
		mav.setViewName("board/list");
		return mav;
	}
	
	@GetMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("/write")
	public ModelAndView write(ModelAndView mav, BoardDto dto) {
		System.out.println(dto);
		int res = service.write(dto);
		mav.setViewName("redirect:/board/list");
		return mav;
	}
	
	@GetMapping("/view")
	public ModelAndView view(ModelAndView mav, String articleNo) {
		BoardDto board = service.view(articleNo);
//		List<CommentDto> comment = service.getComment(articleNo);
//		System.out.println(comment);
		mav.addObject("article", board);
//		mav.addObject("comment", comment);
		mav.setViewName("board/view");
		return mav;
	}
	
	@PostMapping("/view")
	public ModelAndView writecomment(ModelAndView mav) {
		System.out.println("==================");
		System.out.println("들어옴?");
		System.out.println("==================");
		return mav;
	}
	
	@PostMapping("/incom")
	public ModelAndView insertComment(ModelAndView mav, CommentDto dto) {
		System.out.println(dto);
		int res = service.insertComment(dto);
		mav.setViewName("redirect:/board/view?articleNo=" + dto.getArticleNo());
		return mav;
	}
	@PostMapping("/deletecomment")
	public ModelAndView deleteComment(ModelAndView mav, String articleno, String commentno) {
		int res = service.deleteComment(commentno);
		mav.setViewName("redirect:/board/view?articleNo=" + articleno);
		return mav;
	}
	
	@GetMapping("/followdetail/{followId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> FollowingBoard(@PathVariable("followId")String followId) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<BoardDto> res = service.followingboard(followId);
			map.put("resmsg", "성공");
			map.put("resdata", res);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("resmsg", "출력 실패");
			map.put("resdata", e.getMessage());
		}
		ResponseEntity<Map<String,Object>> res = new ResponseEntity(map,HttpStatus.OK);
		return res;
	}
}