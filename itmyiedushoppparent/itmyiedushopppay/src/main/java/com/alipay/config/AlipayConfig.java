package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091900547891";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGzMlCeo/2qU+Mj8ICfvL++XI0bWE1l3VTTlT28Hc92WcRpg8RLIF3C4oFrOmgJKoWO/5PnJGMExLRB0j/hJga6Ev5LC1jeCZ60ZJq4ly3gb9rWKe2L9bN/iXRQyRT8KsayRtuNpeXQfhFnfhTZM2NLHbTckkadq2PHeLFIsXZU0q4CamZN5gIADIdAkIHWvq8awYPS9bsMZyFsm/KGaRtMbBHDGZ50cnMdVkdUzB0dFv8u8BapiEGWKnuSWJf5DEbUPHHRl1RETKgb3K/06X4bAfGxA3XHEJiUtkOU+L4W1dw8NSMqF9HEMndAj3Zr2u2TMAhBeyTHC0Ht9dgpM9TAgMBAAECggEAdWQH7RXxI3zk4uMasLO/iB7Rvyh2HIauzQKNaZOWNE2MvcjgnDMm9/Ybl7+yoRzs5noOiWQvOE4v98shXiwpjl04NGGtkU9EK3cwzFYfKkwa2DAXtchJh5DJfEi9bUBMsgHrGnr+XuI7hN0CMhW6EDRlstR3J02s7dK39rfGlu/qUnXIezurhnNZeiZKQQyslhYBAnQMKK44r11x37VT9sCCmk0/mkQc9B7Otc8xjvjn/WLcUAd0JQyTVWpoM3a6dleT0WOAPKR0UA5BwoKAhVclI7WcKxhO9gngfrrl/fDG+tjJLskNiasN3f9Mf0ZcDP9L6yJL9vWwAQUafzMRIQKBgQD3dWSUi0f/BXc+fsHrh6l8BgEAiZCY0kZUNegtIIRn8iqOK8Gyka8PD+ydHLhMI+ay5u4OLN/00+2Vt1qG7ChPkMm4chcHSJphB8e2YRwaOqsotPnAtYuCU4KP8Aho0zI6rQBRoG5A28XElMpD88e8Lddo8Ge9nmxoD45Rd5gMkQKBgQCLc+m13Qn7fh12Oo2zokSMd1Phe+T9n6btMgc/mkcPa0b27PrX+OULSSVomdtED6DeFHDKH3CGqrmE9jRFlWy4tsSzhsU/QVCFmdiGdukWgBfMQg4fUntit8csV1t91mIRw6GMgE4j/KIuHiKWUDK7GFL+4LZouSONqQLIvPtfowKBgGtgv1JN1eHtZz7xFgi0B3FP0aOADgSiSe5ErgjW8V2BkcG7bwOjf8kTaeMLEVXIxlSfLHFuO4grZg7vTxL46s0L0Nx+dUutz3+HNrwHC4C+MIMrdKyQmobk4eS/jYafDk6zv02sRetS8lBJGDPAVs1rPuvEVd/MHNJS3biwIoRhAoGAfVwsAkK8EIqkrX2hmJQMj0FN9GtNxPlaM+w/O9vSeXf0iVErhi7Gp8iPimKVC7AI6trxMaHlpAmhUHeLMepLnBkg5koG0wBCBHfyf54xlvp7ajEPRPT+2hVfROsa4hpweouklAtbM1qN6GtKqFlb4XjSUzFqiOW1okkGqbHJP4UCgYAzLQehwcI+19vvAsIYNmFFVYSfpzcKasXvxjMazHhprikoh98MJpbA/tXOA+LOOIBuzUS/xKH8/tJnioyUfaHnaWIsTbmRmEpTAa8/LWnqrGKbphoTJ2iGQKReFQ1jaUjiSa/54jmnyMtMaUQClsTwlFebiIxj7dPZN9RWUdm7xQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzgqPMQ06ZGA/2CdnX46gO6xFRxN3hoVaojN8QyKrNVjqkq5vfD/hjOejY6PJ5iZqxULP4pE0OBx+VnTomZeqm68SzTxNhTSMz00Stf/C71kwxx5hb3Ry+Z2DZMs+8N8SbtvGe7v92JLpe/sdOWChSqV/Xs98hgJ8LWqaa2mZf3WM8+npoa+PCVBirPneUBGzprbARolXINcu4FSfp+8OEHlizWAP60v7GRJCet7HmiAOTs3FoQl2p1w8zhs5QJcuj6RT5+J6Sq44gKo9jciSY0KF6wqS6PerZgyn2bgySv0EWGpYOm4f0KpA+Nl8zObR3uTG2O+PrJdsLQmw8+W2ewIDAQAB\n";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://itmyedu.imwork.net/callback/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://itmyedu.imwork.net/callback/return_url";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\AlipayLog";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
