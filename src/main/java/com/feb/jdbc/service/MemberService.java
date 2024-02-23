package com.feb.jdbc.service;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.feb.jdbc.dao.MemberDao;
import com.feb.jdbc.dto.EmailDto;
import com.feb.jdbc.util.EmailUtil;
import com.feb.jdbc.util.Sha512Encoder;

@Service
public class MemberService {
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	EmailUtil emailUtil;
	
	/**
	 * 
	 * @param params null체크 글자갯수 체크 등함  
	 * @return boolean결과를 반환하기 
	 */
	public boolean join(@RequestParam HashMap<String,String> params ) {
		if(Objects.nonNull(memberDao.selectMember(params.get("MemberId"))) ||params.get("memberId").length()<7 )
			return false;
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		params.put("passwd", encoder.getSecurePassword(params.get("passwd")));
		EmailDto emailDto = new EmailDto();
		
		if( memberDao.insertMember(params)==1) {
		emailDto.setFrom("whdudgms321@naver.com");
		emailDto.setReceiver("whdudgms123@naver.com");
		emailDto.setSubject("회원가입을 축하드려요!!!!." + params.get("memberId"));
		// 이메일로 먼저 비번 알려주고 바꿔야 데이터 수정은 가장 마지막에 해야한다 
		emailDto.setText("dkdkdkdk");
		try {
			// 이메일 발송 실패 시 예외처리 
			emailUtil.sendMail(emailDto);
		}catch (Exception e){
			e.printStackTrace();
		}
		return true;
	}else{
		return false;
	}
}
	
	
	
	
	
	
public boolean login(@RequestParam HashMap<String,String> params ) {
	if(Objects.isNull(memberDao.selectMember(params.get("memberId"))))
			return false;
	Sha512Encoder encoder = Sha512Encoder.getInstance();
	String equPasswd = memberDao.selectMember(params.get("memberId")).getPasswd();
	String inputPasswd = encoder.getSecurePassword(params.get("passwd"));
	return equPasswd.equals(inputPasswd);
	}

}
