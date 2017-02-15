package com.mobile.younthcanteen.bean;

import java.io.Serializable;

public class DownLoadBean implements Serializable {
    private String downVersion;//下载的版本
    private long downSize;//已经下载的大小
    private long totalSize;//总的大小.服务器端返回的数据
    private long totalSizeFrmContentLen;//总的大小下载文件URL中获取的

    public DownLoadBean(String downVersion, long downSize, long totalSize, long totalSizeFrmContentLen) {
        this.downVersion = downVersion;
        this.downSize = downSize;
        this.totalSize = totalSize;
        this.totalSizeFrmContentLen = totalSizeFrmContentLen;
    }

    public String getDownVersion() {
        return downVersion;
    }

    public void setDownVersion(String downVersion) {
        this.downVersion = downVersion;
    }

    public long getDownSize() {
        return downSize;
    }

    public void setDownSize(long downSize) {
        this.downSize = downSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalSizeFrmContentLen() {
        return totalSizeFrmContentLen;
    }

    public void setTotalSizeFrmContentLen(long totalSizeFrmContentLen) {
        this.totalSizeFrmContentLen = totalSizeFrmContentLen;
    }

    @Override
    public String toString() {
        return "DownLoadBean{" +
                "downVersion='" + downVersion + '\'' +
                ", downSize=" + downSize +
                ", totalSize=" + totalSize +
                ", totalSizeFrmContentLen=" + totalSizeFrmContentLen +
                '}';
    }
}
