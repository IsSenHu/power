package com.cdsen.power.server.email.controller;

import com.cdsen.power.core.IPageRequest;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.PageResult;
import com.cdsen.power.server.email.model.ao.EmailUpdateAO;
import com.cdsen.power.server.email.model.query.EmailQuery;
import com.cdsen.power.server.email.model.vo.SimpleEmailVO;
import com.cdsen.power.server.email.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuSen
 * create on 2019/11/4 14:22
 */
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 更新邮件
     *
     * @param ao 更新邮件数据模型
     * @return 更新结果
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody EmailUpdateAO ao) {
        return mailService.update(ao);
    }

    /**
     * 分页
     *
     * @param request 分页请求参数
     * @return 分页结果
     */
    @PostMapping("/page")
    public JsonResult<PageResult<SimpleEmailVO>> page(@RequestBody IPageRequest<EmailQuery> request) {
        return mailService.page(request);
    }
}
