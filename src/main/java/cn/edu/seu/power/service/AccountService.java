package cn.edu.seu.power.service;

import cn.edu.seu.power.dao.AccountDao;
import cn.edu.seu.power.domain.Account;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhh on 2016/7/12.
 */
@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    @Value("#{appProp.projectName}")
    private String projectName;

    public boolean addUser(String username, String password, String authority, String email) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && !StringUtils.isEmpty(email)) {
            Account account = accountDao.selectUserByUsername(username);
            if (account != null)
                return false;
            accountDao.insertUser(username, projectName + "." + password, authority, email);
            return true;
        } else
            return false;
    }

    public boolean validateUser(String username, String password) {
        if (!StringUtils.isEmpty(username)) {
            Account account = accountDao.selectUserByUsername(username);
            String[] passString = account.getPassword().split("\\.");
            return passString[passString.length - 1].equals(password);
        } else
            return false;
    }

    public Account getAccountByUsername(String username) {
        return accountDao.selectUserByUsername(username);
    }


    public List<Account> getAllUser(){
        return accountDao.getAllUser();
    }

    public boolean modifyUserById(HashMap<String,String> userinfo){
        int ret = accountDao.modifyUserById(userinfo.get("id"),userinfo.get("username"),userinfo.get("password"),userinfo.get("authority"));
        if (ret == 0) {
            return false;
        }
        return true;
    }

    public boolean deleteUserById(String userId){
        int ret = accountDao.deleteUserById(userId);
        if(ret == 0)
            return false;
        return true;
    }
}