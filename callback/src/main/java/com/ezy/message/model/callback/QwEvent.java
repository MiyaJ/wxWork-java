package com.ezy.message.model.callback;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Caixiaowei
 * @ClassName QwEvent
 * @Description
 * @createTime 2020/9/3$ 17:00$
 */
@Data
@XStreamAlias("xml")
public class QwEvent implements Serializable {

    @XStreamAlias("Event")
    private String event;
}
