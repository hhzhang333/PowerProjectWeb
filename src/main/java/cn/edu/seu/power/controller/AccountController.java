package cn.edu.seu.power.controller;

import cn.edu.seu.power.dao.DevmessDao;
import cn.edu.seu.power.dao.ReporterDao;
import cn.edu.seu.power.domain.Account;
import cn.edu.seu.power.domain.GraphParam;
import cn.edu.seu.power.domain.GraphTime;
import cn.edu.seu.power.service.AccountService;
import cn.edu.seu.power.service.DevmessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by zhh on 2016/7/16.
 */
@Controller
public class AccountController {
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private DevmessService devmessService;

    @Autowired
    private ReporterDao reporterDao;

    private void initResult(int start, int end, int type) {
        for (int i = start; i <= end; i++) {
            Random random = new Random(System.currentTimeMillis());

            ArrayList<String> re = new ArrayList<String>();

            for (int j=0; j < 37; j++) {
                int tmp = random.nextInt(100) + 1;
                re.add(String.valueOf(tmp));
            }

            Date time = new Date();
            if (type == 0) {
                time.setHours(i);
            }
            if (type == 1) {
                time.setDate(i);
            }
            if (type == 2)
                time.setMonth(i-1);

            reporterDao.insertReporter(time, "2", "test", re.get(29), re.get(30),
                    re.get(5), re.get(16), re.get(17), re.get(24), re.get(31),
                    re.get(6), re.get(15), re.get(18), re.get(25), re.get(32),
                    re.get(7), re.get(14), re.get(19), re.get(26), re.get(33),
                    re.get(8), re.get(13), re.get(20), re.get(27), re.get(34),
                    re.get(9), re.get(12), re.get(21), re.get(28), re.get(35),
                    re.get(10), re.get(11), re.get(22), type, 2
                    );
        }
    }

    @RequestMapping(value = "powerMonitor", method = RequestMethod.GET)
    public String powerMonitor() {
        return "pages/powerMonitor.ftl";
    }

    @RequestMapping(value = "warnManagement", method = RequestMethod.GET)
    public String warnManagement() {
        return "pages/warnManagement.ftl";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String showPage() {
        devmessService.init();
        return "index.ftl";
    }

    @RequestMapping(value = "start", method = RequestMethod.GET)
    public String showStartPage() {
        return "index.ftl";
    }

    @RequestMapping(value = "generate/{start}/{end}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public void generateData(
            @PathVariable int start,
            @PathVariable int end,
            @PathVariable int type) {
        initResult(start, end, type);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                         HttpSession httpSession) {
        try {
            if (accountService.validateUser(username, password)) {
                Account account = accountService.getAccountByUsername(username);
                httpSession.setAttribute("username", username);
                httpSession.setAttribute("authority", account.getAuthority());
                return account.getAuthority();
            } else {
                return "error";
            }
        } catch (Exception ex) {
            logger.error("Exception in login", ex);
            return "error";
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public boolean register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "email") String email) {
        try {
            return accountService.addUser(username, password, "normal", email);
        } catch (Exception ex) {
            logger.error("exception in register", ex);
            return false;
        }
    }
}
