package com.github.conanzhangxin.discovery.dataobject;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-5
 * Time: ÏÂÎç11:20
 * To change this template use File | Settings | File Templates.
 */
public class BasicHttpResult {

    private boolean isSuccess;

    private String error;

    private String errorDescription;

    private String accessToken;

    private Integer expiresIn;

    private String sessionKey;

    private String sessionSecret;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionSecret() {
        return sessionSecret;
    }

    public void setSessionSecret(String sessionSecret) {
        this.sessionSecret = sessionSecret;
    }
}
