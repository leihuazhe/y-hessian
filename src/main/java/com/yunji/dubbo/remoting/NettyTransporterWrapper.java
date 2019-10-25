package com.yunji.dubbo.remoting;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.*;


/**
 * @author Denim.leihz 2019-10-21 11:01 AM
 */
public class NettyTransporterWrapper implements Transporter {

    private Transporter transporter;

    private static final String DUBBO_CODEC_NAME = "yunji_dubbo_compatible";
    private static final String REMOTING_SERIALIZATION = "hessian2_compatible";

    public NettyTransporterWrapper(Transporter transporter) {
        if (transporter == null) {
            throw new IllegalArgumentException("transporter == null");
        }
        this.transporter = transporter;
    }

    @Override
    public Server bind(URL url, ChannelHandler handler) throws RemotingException {
//        url = url.addParameters(Constants.CODEC_KEY, DUBBO_CODEC_NAME,
//                Constants.SERIALIZATION_KEY, REMOTING_SERIALIZATION);
        url = url.addParameter(Constants.CODEC_KEY, DUBBO_CODEC_NAME);
        return transporter.bind(url, handler);
    }

    @Override
    public Client connect(URL url, ChannelHandler handler) throws RemotingException {
        return transporter.connect(url, handler);
    }
}
