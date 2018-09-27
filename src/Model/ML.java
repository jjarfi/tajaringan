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
public class ML {

    private final StringProperty mip;
    private final StringProperty msm;
    private final StringProperty mg;
    private final StringProperty mma;
  

    public ML(String mip, String msm, String mg, String mma) {
        this.mip = new SimpleStringProperty(mip);
        this.msm = new SimpleStringProperty(msm);
        this.mg = new SimpleStringProperty(mg);
        this.mma = new SimpleStringProperty(mma);
      
        
        
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

    public final String getMsm() {
        return msm.get();
    }

    public final void setMsm(String value) {
        msm.set(value);
    }

    public StringProperty msmProperty() {
        return msm;
    }

    public final String getMg() {
        return mg.get();
    }

    public final void setMg(String value) {
        mg.set(value);
    }

    public StringProperty mgProperty() {
        return mg;
    }

    public final String getMma() {
        return mma.get();
    }

    public final void setMma(String value) {
        mma.set(value);
    }

    public StringProperty mmaProperty() {
        return mma;
    }



}
