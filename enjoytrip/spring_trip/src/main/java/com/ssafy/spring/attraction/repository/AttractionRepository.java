package com.ssafy.spring.attraction.repository;

import java.util.List;

import com.ssafy.spring.attraction.dto.AttractionDto;
import com.ssafy.spring.attraction.dto.KeywordDto;

public interface AttractionRepository {
	public List<AttractionDto> getList(KeywordDto dto);

}