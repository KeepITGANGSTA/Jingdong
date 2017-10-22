package bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 李英杰 on 2017/10/11.
 */
@Entity
public class User {
    @Id
    private long id;
    private String iconUrl;
    private String nickname;
    private String username;
    private int uid;

    @Generated(hash = 258404301)
    public User(long id, String iconUrl, String nickname, String username, int uid) {
        this.id = id;
        this.iconUrl = iconUrl;
        this.nickname = nickname;
        this.username = username;
        this.uid = uid;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
