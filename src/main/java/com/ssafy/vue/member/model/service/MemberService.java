package com.ssafy.vue.member.model.service;

import com.ssafy.vue.member.model.MemberDto;

import java.sql.SQLException;

public interface MemberService {

	MemberDto login(MemberDto memberDto) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;

    void join(MemberDto memberDto) throws SQLException;
}
