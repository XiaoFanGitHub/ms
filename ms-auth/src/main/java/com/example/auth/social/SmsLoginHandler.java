package com.example.auth.social;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.auth.service.AuthenticationUserService;
import com.example.common.resource.entity.SysUser;
import com.example.common.resource.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 手机号码登录处理
 */
@Slf4j
@Component("SMS")
@AllArgsConstructor
public class SmsLoginHandler extends AbstractLoginHandler {
	private final AuthenticationUserService authenticationUserService;


	/**
	 * 验证码登录传入为手机号
	 * 不用不处理
	 *
	 * @param mobile
	 * @return
	 */
	@Override
	public String identify(String mobile) {
		return mobile;
	}

	/**
	 * 通过mobile 获取用户信息
	 *
	 * @param identify
	 * @return
	 */
	@Override
	public UserInfo info(String identify) {
		SysUser user = authenticationUserService
				.getOne(Wrappers.<SysUser>query()
						.lambda().eq(SysUser::getPhone, identify));

		if (user == null) {
			log.info("手机号未注册:{}", identify);
			return null;
		}
		return UserInfo.builder().user(user).build();
	}
}