package com.zmn.pojo.po;

import com.zmn.pojo.bo.MsgBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Msg {

    private Long mid;
    private String sessionId;
    private Long deviceId;
    private String username;
    private String msg;
    private LocalDateTime createTime;

    public static Msg fromBo(MsgBo msgBo) {
        Msg bean = new Msg();
        BeanUtils.copyProperties(msgBo, bean);
        bean.setMid(new Random().nextLong())
                .setCreateTime(LocalDateTime.now());
        return bean;
    }

}
