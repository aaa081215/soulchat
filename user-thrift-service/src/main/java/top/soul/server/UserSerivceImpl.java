package top.soul.server;


import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.soul.mapper.UserMapper;
import top.soul.thrift.user.UserInfo;
import top.soul.thrift.user.UserService;


@Service
public class UserSerivceImpl implements UserService.Iface {


    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {

        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return userMapper.getTeacherById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        UserInfo userInfo = userMapper.getUserByName(username);
        return userInfo;
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}
