package com.myframe.core.util;

import com.myframe.core.mail.MailBean;
import com.myframe.core.mail.MailConfig;
import jodd.mail.*;
import org.slf4j.Logger;

import java.util.List;

/**
 * 邮件发送工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class MailUtils {
    private static final Logger logger = LogUtils.get();

    private static Email createEmail(MailBean mailBean) {
        Email email = Email.create()
                .addHtml(mailBean.getContent(), Encoding.S_UTF8)
                .from(mailBean.getFrom())
                .subject(mailBean.getSubject());
        List<String> mailTo = mailBean.getTo();
        if (CollectUtils.isNotEmpty(mailTo)) {
            for (String mail : mailTo) {
                email.to(mail);
            }
        }
        List<String> bccTo = mailBean.getBcc();
        if (CollectUtils.isNotEmpty(bccTo)) {
            for (String mail : bccTo) {
                email.bcc(mail);
            }
        }
        List<String> ccTo = mailBean.getCc();
        if (CollectUtils.isNotEmpty(ccTo)) {
            for (String mail : ccTo) {
                email.cc(mail);
            }
        }
        List<String> attaches = mailBean.getAttachedFileList();
        if (CollectUtils.isNotEmpty(attaches)) {
            for (String file : attaches) {
                email.embed(EmailAttachment.attachment().file(file));
            }
        }

        email.setCurrentSentDate();

        return email;
    }

    private static SmtpServer createSmtpServer(MailConfig mailConfig) {
        SmtpServer smtpServer = null;
        if (mailConfig.isSsl()) {
            smtpServer = SmtpSslServer.create(mailConfig.getHost(), mailConfig.getPort());
        } else {
            smtpServer = SmtpServer.create(mailConfig.getHost(), mailConfig.getPort());
        }
        if (StringUtils.isNotEmpty(mailConfig.getUserName())) {
            smtpServer.authenticateWith(mailConfig.getUserName(), mailConfig.getPassword());
        }

        return smtpServer;
    }

    public static boolean sendMail(MailBean mailBean, MailConfig mailConfig) {
        try {
            Email email = createEmail(mailBean);
            SmtpServer smtpServer = createSmtpServer(mailConfig);
            SendMailSession session = smtpServer.createSession();
            session.open();
            session.sendMail(email);
            session.close();
            return true;
        } catch (Throwable t) {
            logger.error("发送邮件失败！", t);
        }
        return false;
    }

    public static int sendMails(List<MailBean> mailBeans, MailConfig paramMail) {
        if (CollectUtils.isEmpty(mailBeans)) {
            return 0;
        }
        int success = 0;
        SmtpServer smtpServer = createSmtpServer(paramMail);
        SendMailSession session = smtpServer.createSession();
        session.open();
        for (MailBean mb : mailBeans) {
            try {
                Email email = createEmail(mb);
                session.sendMail(email);
                ++success;
            } catch (Exception e) {
                logger.error("邮件" + mb.getTo().toString() + "发送失败！", e);
            }
        }
        session.close();

        return success;
    }

}
