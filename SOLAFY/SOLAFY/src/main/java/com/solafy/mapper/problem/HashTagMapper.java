package com.solafy.mapper.problem;

import org.apache.ibatis.annotations.Mapper;

import com.solafy.model.HashTagDto;

/**
* @FileName : HashTagMapper.java
* @Project : SOLAFY
* @Date : 2020. 12. 20.
* @작성자 : Lee Ayoung

* @변경이력 :
* @프로그램 설명 : HashTag Mapper
*/

@Mapper
public interface HashTagMapper {
	// TODO : hashTag테이블에서 두 컬럼 전부 PK로 둬야할 것 같음.
	/**
	* @param hashTag - 해시태그 이름
	* @return HashTagDto - 해시태그 : hashTagNo, hashTag
	* @Method 설명 : 해시태그 이름으로 해시태그 번호 반환
	* @변경이력 :
	*/
	public HashTagDto selectHashTagNoByHashTag(String hashTag);
	
	/**
	* @param hashTagDto - 해시태그 
	* @return int : 등록된 행의 개수 반환
	* @Method 설명 : 해시태그 등록
	* @변경이력 :
	*/
	public int createHashTag(String hashTag);
}
