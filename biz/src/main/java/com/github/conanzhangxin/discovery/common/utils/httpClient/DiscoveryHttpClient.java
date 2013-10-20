package com.github.conanzhangxin.discovery.common.utils.httpClient;

import com.github.conanzhangxin.discovery.common.exception.DiscoveryException;
import com.github.conanzhangxin.discovery.dataobject.HttpTokenResult;
import com.github.conanzhangxin.discovery.dataobject.QuotaResult;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-3
 * Time: ÏÂÎç9:07
 * To change this template use File | Settings | File Templates.
 */
public interface DiscoveryHttpClient {

    HttpTokenResult getAccessToken() throws DiscoveryException;

    String pushAFile(String fileContent) throws DiscoveryException;

    String getAFile(String key) throws DiscoveryException;

    String deleteAFile(String key) throws DiscoveryException;

    QuotaResult getQuota(String accessToken) throws DiscoveryException;
}
