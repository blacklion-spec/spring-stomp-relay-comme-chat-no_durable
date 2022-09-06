package com.stomp.custom.service;

import com.stomp.custom.transfer.LoginTransfer;
import com.stomp.custom.vo.LoginVo;

/**
 * @createTime 2022年09月02日 17:00:00
 */
public interface ILoginService {

    LoginTransfer doLogin(LoginVo loginVo);

}
