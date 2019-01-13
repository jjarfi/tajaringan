/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author pacevil
 */
public class MNN {

    private final StringProperty mno;
    private final StringProperty mip;
    private final StringProperty mmac;
    private final StringProperty mkdp;
    private final StringProperty mnmp;
    private final StringProperty kdl;
    private final StringProperty knml;

    public MNN(String mno, String mip, String mmac, String mkdp, String mnmp,String kdl,String knml) {
        this.mno = new SimpleStringProperty(mno);
        this.mip = new SimpleStringProperty(mip);
        this.mmac = new SimpleStringProperty(mmac);
        this.mkdp = new SimpleStringProperty(mkdp);
        this.mnmp = new SimpleStringProperty(mnmp);
        this.kdl = new SimpleStringProperty(kdl);
        this.knml = new SimpleStringProperty(knml);

    }

    public final String getMno() {
        return mno.get();
    }

    public final void setMno(String value) {
        mno.set(value);
    }

    public StringProperty mnoProperty() {
        return mno;
    }

    public final String getMip() {
        return mip.get();
    }

    public final void setMip(String value) {
        mip.set(value);
    }

    public StringProperty mipProperty() {
        return mip;
    }

    public final String getMmac() {
        return mmac.get();
    }

    public final void setMmac(String value) {
        mmac.set(value);
    }

    public StringProperty mmacProperty() {
        return mmac;
    }

    public final String getMkdp() {
        return mkdp.get();
    }

    public final void setMkdp(String value) {
        mkdp.set(value);
    }

    public StringProperty mkdpProperty() {
        return mkdp;
    }

    public final String getMnmp() {
        return mnmp.get();
    }

    public final void setMnmp(String value) {
        mnmp.set(value);
    }

    public StringProperty mnmpProperty() {
        return mnmp;
    }

    public final String getKdl() {
        return kdl.get();
    }

    public final void setKdl(String value) {
        kdl.set(value);
    }

    public StringProperty kdlProperty() {
        return kdl;
    }

    public final String getKnml() {
        return knml.get();
    }

    public final void setKnml(String value) {
        knml.set(value);
    }

    public StringProperty knmlProperty() {
        return knml;
    }

  

}
