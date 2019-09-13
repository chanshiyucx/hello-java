package com.chanshiyu.netty.attribute;

import com.chanshiyu.netty.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    // 会话
    AttributeKey<Session> SESSION = AttributeKey.newInstance("web_session");

}
