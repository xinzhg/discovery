package com.github.conanzhangxin.discovery.dataobject;

/**
 * Created with IntelliJ IDEA.
 * User: xinnan.zx
 * Date: 13-10-5
 * Time: обнГ11:20
 * To change this template use File | Settings | File Templates.
 */
public class QuotaResult extends BasicHttpResult {

    private Long quota;

    private Long used;

    public Long getQuota() {
        return quota;
    }

    public void setQuota(Long quota) {
        this.quota = quota;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }
}
