package com.wjf.mygreen.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016/9/5.
 */
@Entity
public class Message {
    @Id
    private Long id;
    @Property
    private String content;
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 16870723)
    public Message(Long id, String content) {
        this.id = id;
        this.content = content;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
}
