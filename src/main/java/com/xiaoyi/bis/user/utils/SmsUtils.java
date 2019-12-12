package com.xiaoyi.bis.user.utils;

import com.xiaoyi.bis.user.dao.SmsMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @Description：互亿短信
 * @Author：kk
 * @Date：2019/8/28 13:13
 */
@Deprecated
public class SmsUtils {

    private @Autowired
    SmsMapper smsMapper;

    private static String url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    private static String account = "C08421367";
    private static String password = "f4bda90a856bed0798c16029e9365e1b";

    /**
     * 发送验证码
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public static boolean sendCode(String mobile, int smsCode) {
        /*
         * 实例话对象
         */
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);

        method.getParams().setContentCharset("GBK");

        /*
         * 设置请求头
         */
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        //   int smsCode = (int) ((Math.random() * 9 + 1) * 100000);
        String content = "验证码：" + smsCode + "，请您在30分钟内填写。如非本人操作，请忽略本短信。";
        /*
         * 发送配置信息
         */
        NameValuePair[] info = {
                new NameValuePair("account", account),
                new NameValuePair("password", password),
                new NameValuePair("mobile", mobile),
                new NameValuePair("content", content),
        };

        /*
         * 设置请求内容
         */
        method.setRequestBody(info);

        try {
            // 执行短信发送
            client.executeMethod(method);
            // 接受返回值
            String result_info = method.getResponseBodyAsString();
            //解析xml
            Document doc = DocumentHelper.parseText(result_info);
            Element ele = doc.getRootElement();

            String code = ele.elementText("code");
            String msg = ele.elementText("msg");
            String smsid = ele.elementText("smsid");
            if (StringUtils.equals("2", code)) {
                System.out.println("短信提交成功");
                return true;
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // Release connection
            method.releaseConnection();
            //client.getConnectionManager().shutdown();

        }

        return false;
    }

}

