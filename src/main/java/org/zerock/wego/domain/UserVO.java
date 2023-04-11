package org.zerock.wego.domain;

import lombok.Setter;
import lombok.Value;

@Value
public class UserVO {

	private Integer userId;
	private String nickname;
	private String address;
	private String sanRange;
	private String sanTaste;
	private String userPic;
	private String socialId;
}