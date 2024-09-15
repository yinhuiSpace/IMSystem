package com.zmn.pojo.vo;

import com.zmn.pojo.po.Msg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MsgBean {

    private Long deviceId;
    private List<Msg> newMsgList;
}
