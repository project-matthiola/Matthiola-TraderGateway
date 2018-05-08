package com.cts.trader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Future")
public class Future implements Serializable {
    @Id
    @Column(nullable = false, name = "future_id")
    private String futureID;

    @NotNull(message = "用户名不能为空")
    @Column(nullable = false, name = "future_name")
    private String futureName;

    @NotNull(message = "密码不能为空")
    @Column(nullable = false, name = "period")
    private String period;

    public Future() {}

    public Future(String futureID, String futureName, String period) {
        this.futureID = futureID;
        this.futureName = futureName;
        this.period = period;
    }

    public String getFutureID() {
        return futureID;
    }

    public void setFutureID(String futureID) {
        this.futureID = futureID;
    }

    public String getFutureName() {
        return futureName;
    }

    public void setFutureName(String futureName) {
        this.futureName = futureName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
