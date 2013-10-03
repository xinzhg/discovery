package com.github.conanzhangxin.discovery.common.utils.httpClient;

import com.github.conanzhangxin.discovery.common.exception.DiscoveryException;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-3
 * Time: ����9:07
 * To change this template use File | Settings | File Templates.
 */
public interface HttpClient {

    String getAccessToken() throws DiscoveryException;

    String pushAFile(String fileContent) throws DiscoveryException;

    String getAFile(String key) throws DiscoveryException;

    String deleteAFile(String key) throws DiscoveryException;
}