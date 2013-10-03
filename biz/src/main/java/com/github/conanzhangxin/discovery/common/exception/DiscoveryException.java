package com.github.conanzhangxin.discovery.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-3
 * Time: ÏÂÎç9:10
 * To change this template use File | Settings | File Templates.
 */
public class DiscoveryException extends Exception {

    private DiscoveryErrorCode discoveryErrorCode;

    public DiscoveryException() {
        super();
    }

    public DiscoveryException(String message) {
        super(message);
    }

    public DiscoveryException(String message , Throwable throwable) {
        super(message , throwable);
    }

    public DiscoveryException(Throwable throwable) {
        super(throwable);
    }

    public DiscoveryException(DiscoveryErrorCode discoveryErrorCode) {
        //todo use discoveryErrorCode  express errorContent
        super();
        this.discoveryErrorCode = discoveryErrorCode;
    }

    public DiscoveryException(String message , DiscoveryErrorCode discoveryErrorCode) {
        //todo use discoveryErrorCode and message express errorContent
        super();
        this.discoveryErrorCode = discoveryErrorCode;
    }


}
