package io.reflectoring.services.impl;

import io.reflectoring.entity.UserDb;
import io.reflectoring.entity.UserDbExample;
import io.reflectoring.mapper.UserDbMapper;
import io.reflectoring.services.IuserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.RequestWrapper;
import java.util.List;

@Service("userservice")
public class IuserServiceImpl implements IuserService {

    @Resource
    private UserDbMapper userDbMapper;

    @Override
    public UserDb getUserById(Long userId) {

        UserDbExample userDbExample = new UserDbExample();
        UserDbExample.Criteria criteria=userDbExample.createCriteria();
        criteria.andIdEqualTo(userId);

//        return userDbMapper.selectByExample(userDbExample);
        return userDbMapper.selectByPrimaryKey(userId);
    }
}
