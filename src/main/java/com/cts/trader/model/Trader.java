package com.cts.trader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Trader")
public class Trader implements Serializable {
    @Id
    @Column(nullable = false, name = "trader_id")
    private Long traderID;

    @NotNull(message = "用户名不能为空")
    @Column(unique = true, nullable = false, name = "trader_name")
    private String traderName;

    @NotNull(message = "密码不能为空")
    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = true, name = "firm_id")
    private Long firmID;

    @Column(nullable = true, name = "role")
    private String role;

    public Trader() {}

    public Trader(Long traderID, String traderName, String password, Long firmID, String role) {
        this.traderID = traderID;
        this.traderName = traderName;
        this.password = password;
        this.firmID = firmID;
        this.role = role;
    }

    public Long getTraderID() {
        return traderID;
    }

    public void setTraderID(Long traderID) {
        this.traderID = traderID;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getFirmID() {
        return firmID;
    }

    public void setFirmID(Long firmID) {
        this.firmID = firmID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
