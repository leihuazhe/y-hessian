package com.alibaba.dubbo.rpc.protocol.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.remoting.transport.CodecSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.dubbo.rpc.protocol.dubbo.CallbackServiceCodec.decodeInvocationArgument;


/**
 * 因为 CallbackServiceCodec 包类可见，所以只能定义 package name 兼容一下
 *
 * @author Denim.leihz 2019-10-21 11:21 AM
 */
public class YunjiDecodeableRpcInvocation extends DecodeableRpcInvocation {

    private static final Logger log = LoggerFactory.getLogger(DecodeableRpcInvocation.class);

    private byte serializationType;


    public YunjiDecodeableRpcInvocation(Channel channel, Request request, InputStream is, byte id) {
        super(channel, request, is, id);
        this.serializationType = id;
    }


    @Override
    public Object decode(Channel channel, InputStream input) throws IOException {
        ObjectInput in = CodecSupport.getSerialization(channel.getUrl(), serializationType)
                .deserialize(channel.getUrl(), input);

        setAttachment(Constants.DUBBO_VERSION_KEY, in.readUTF());
        setAttachment(Constants.PATH_KEY, in.readUTF());
        setAttachment(Constants.VERSION_KEY, in.readUTF());

        setMethodName(in.readUTF());
        try {
            Object[] args;
            Class<?>[] pts;
            String desc = in.readUTF();
            if (desc.length() == 0) {
                pts = DubboCodec.EMPTY_CLASS_ARRAY;
                args = DubboCodec.EMPTY_OBJECT_ARRAY;
            } else {
                pts = ReflectUtils.desc2classArray(desc);
                args = new Object[pts.length];
                //自定义 hessian3的序列化模式,支持流式的序列化协议
                if (serializationType == (byte) 30) {
                    //Fixme tag index out bound of exception.
                    if (pts.length != 0) {
                        int tag;
                        while ((tag = in.readInt()) != -1) {
                            try {
                                args[tag] = in.readObject(pts[tag]);
                            } catch (Exception e) {
                                if (log.isWarnEnabled()) {
                                    log.warn("Decode argument failed: " + e.getMessage(), e);
                                }
                            }
                        }
                    }
                    //Dubbo 原生的序列化模式
                } else {
                    for (int i = 0; i < args.length; i++) {
                        try {
                            args[i] = in.readObject(pts[i]);
                        } catch (Exception e) {
                            if (log.isWarnEnabled()) {
                                log.warn("Decode argument failed: " + e.getMessage(), e);
                            }
                        }
                    }
                }
            }
            setParameterTypes(pts);

            Map<String, String> map = (Map<String, String>) in.readObject(Map.class);
            if (map != null && map.size() > 0) {
                Map<String, String> attachment = getAttachments();
                if (attachment == null) {
                    attachment = new HashMap<String, String>();
                }
                attachment.putAll(map);
                setAttachments(attachment);
            }
            //decode argument ,may be callback
            for (int i = 0; i < args.length; i++) {
                args[i] = decodeInvocationArgument(channel, this, pts, i, args[i]);
            }

            setArguments(args);

        } catch (ClassNotFoundException e) {
            throw new IOException(StringUtils.toString("Read invocation data failed.", e));
        }
        return this;
    }
}
