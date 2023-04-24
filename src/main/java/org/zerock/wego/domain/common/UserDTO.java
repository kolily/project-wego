package org.zerock.wego.domain.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.domain.oauth.naver.NaverUserInfoDTO;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {

	private Integer userId;
	private String socialId;
	private String nickname;
	private String address;
	private String sanRange;
	private String sanTaste;
	private String userPic;
	
	@Builder
	public UserDTO(Integer userId, String socialId, String nickname, String address, String sanRange, String sanTaste, String userPic) {
		super();
		this.userId = userId;
		this.socialId = socialId;
		this.nickname = nickname;
		this.address = address;
		this.sanRange = sanRange;
		this.sanTaste = sanTaste;
		this.userPic = userPic;
	} // constructer
	
	
	public static UserDTO createByKakao(KakaoUserInfoDTO kakaoInfo) {
		LocalDateTime now = LocalDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		
		String nickname = nowString + "K";
		String socialId = kakaoInfo.getKakao_account().getEmail();
		
		UserDTO userDTO = UserDTO.builder()
									.nickname(nickname)
									.socialId(socialId)
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	public static UserDTO createByNaver(NaverUserInfoDTO naverInfo) {
		LocalDateTime now = LocalDateTime.now();
		String nowString = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		
		String nickname = nowString + "N";
		String socialId = naverInfo.getResponse().getEmail();
		
		UserDTO userDTO = UserDTO.builder()
									.nickname(nickname)
									.socialId(socialId)
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	
}// end class
