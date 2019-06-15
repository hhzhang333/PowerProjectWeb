package cn.edu.seu.power.dao;

import cn.edu.seu.power.domain.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhh on 2016/7/12.
 */
public interface AccountDao {
    public void insertUser(@Param(value = "username") String username,
                           @Param(value = "password") String password,
                           @Param(value = "authority") String authority,
                           @Param(value = "email") String email);

    public Account selectUserByUsername(@Param(value = "username") String username);

    public List<Account> getAllUser();

    public int modifyUserById(@Param(value = "id") String id,
                               @Param(value = "username") String username,
                               @Param(value = "password") String password,
                               @Param(value = "authority") String authority);

    public int deleteUserById(@Param(value = "userId")String userId);
}
