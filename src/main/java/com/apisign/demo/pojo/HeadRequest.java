package com.apisign.demo.pojo;

/**
 * 用来检验sign的头信息
 * liuhongdi
 *
*/
public class HeadRequest {
    /**
     * appId
     */
    private String appId;
    public String getAppId() {
        return this.appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    /**
     * appSecret秘钥
     */
    private String appSecret;
    public String getAppSecret() {
        return this.appSecret;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    /**
     * 10位时间戳
     */
    private String timestamp;
    public String getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * 参数签名
     */
    private String sign;
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    /**
     * 随机字符串
     */
    private String nonce;
    public String getNonce() {
        return this.nonce;
    }
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
    /**
     * api版本号
     */
    private String version;
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
}