package com.yunji.dubbo.remoting;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.*;


/**
 * NettyTransporter 的 Wrapper类
 * 作用是 provider 在创建 netty server 时, 修改 dubbo protocol serializer Codec2 的实现类(SPI),采用 YunjiDubboCountCodec.
 *
 * @author Denim.leihz 2019-10-21 11:01 AM
 */
public class NettyTransporterWrapper implements Transporter {

    private Transporter transporter;

    private static final String DUBBO_CODEC_NAME = "yunji_dubbo_compatible";

    public NettyTransporterWrapper(Transporter transporter) {
        if (transporter == null) {
            throw new IllegalArgumentException("transporter == null");
        }
        this.transporter = transporter;
    }

    @Override
    public Server bind(URL url, ChannelHandler handler) throws RemotingException {
        url = url.addParameter(Constants.CODEC_KEY, DUBBO_CODEC_NAME);
        return transporter.bind(url, handler);
    }

    @Override
    public Client connect(URL url, ChannelHandler handler) throws RemotingException {
        return transporter.connect(url, handler);
    }
}
