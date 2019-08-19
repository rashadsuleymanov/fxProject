/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.naxpoct.bank.model;

import java.util.Objects;

/**
 *
 * @author Rashad Suleymanov
 */
public class Bank implements Comparable<Bank> {

    private long terminalId;
    private double debetId;
    private double kreditId;
    private String teyinatId;

    public Bank(long terminalId, double debetId, double kreditId, String teyinatId) {
        this.terminalId = terminalId;
        this.debetId = debetId;
        this.kreditId = kreditId;
        this.teyinatId = teyinatId;
    }

    

    public long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(long terminalId) {
        this.terminalId = terminalId;
    }

    public double getDebetId() {
        return debetId;
    }

    public void setDebetId(double debetId) {
        this.debetId = debetId;
    }

    public double getKreditId() {
        return kreditId;
    }

    public void setKreditId(double kreditId) {
        this.kreditId = kreditId;
    }

    public String getTeyinatId() {
        return teyinatId;
    }

    public void setTeyinatId(String teyinatId) {
        this.teyinatId = teyinatId;
    }

    @Override
    public String toString() {
        return "Bank{" + "terminalId=" + terminalId + ", debetId=" + debetId + ", kreditId=" + kreditId + ", teyinatId=" + teyinatId + '}';
    }

    

    public int compareTo(Bank bank) {
        if (this.getTerminalId() < bank.getTerminalId()) {
            return -1;
        } else if (this.getTerminalId() > bank.getTerminalId()) {
            return 1;
        } else {
            return 0;
        }
    }

    // Two employees are equal if their IDs are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bank bank = (Bank) o;
        return terminalId == bank.getTerminalId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalId);
    }

}
