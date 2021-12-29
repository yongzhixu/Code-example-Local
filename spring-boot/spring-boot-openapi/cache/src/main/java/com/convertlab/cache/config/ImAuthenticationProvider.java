package com.convertlab.cache.config;/*
package com.convertlab.common.service.impl;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ImAuthenticationProvider implements AuthenticationProvider {
 
    private SecurityOrgPeopleMapper securityOrgPeopleMapper;//根据项目需求注入

    private ImCheckTokenFactory imCheckTokenFactory;//根据项目需求注入

    public ImAuthenticationProvider(ImCheckTokenFactory imCheckTokenFactory, SecurityOrgPeopleMapper securityOrgPeopleMapper) {
        this.securityOrgPeopleMapper = securityOrgPeopleMapper;
        this.imCheckTokenFactory = imCheckTokenFactory;
    }
 
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//自定义的装载用户信息的类
        ImTokenAuthentication imTokenAuthentication = (ImTokenAuthentication) authentication;
//获取在过滤器中放入authentication的用户信息
        String token = authentication.getPrincipal().toString();
        Integer userId = Integer.parseInt(imTokenAuthentication.getUserId().toString());
        String client = imTokenAuthentication.getClient();
 
//获取验证token所在的sevice
        ImCheckTokenService imCheckTokenService = imCheckTokenFactory.getService(client);
 
        if (Objects.isNull(imCheckTokenService)) {
            authentication.setAuthenticated(false);
            throw new UserAuthenticationException(SecurityHttpServletResponse.TOKEN_INVALID, "authenticate.fail");
        }
//验证token逻辑
        Object object = imCheckTokenService.checkToken(userId, token);
        if (Objects.isNull(object)) {
            throw new BadCredentialsException("");
        }
 
        OrgPeople orgPeople = securityOrgPeopleMapper.getPeopleBySystemUserId(userId);
     
        imTokenAuthentication.setDetails(new SecurityUserDetails((Account) ；
//在servcice中验证不通过就已经抛出异常了，此处正常运行则设置验证通过
        authentication.setAuthenticated(true);
        return authentication;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return (ImTokenAuthentication.class.isAssignableFrom(authentication));
    }
}*/
