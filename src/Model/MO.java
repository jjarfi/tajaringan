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
public class MO {

    private final StringProperty mid;
    private final StringProperty mkdprg;
    private final StringProperty mkds;
    private final StringProperty mip;

    public MO(String mid, String mkdprg, String mkds, String mip) {
        this.mid = new SimpleStringProperty (mid);
        this.mkdprg = new SimpleStringProperty (mkdprg);
        this.mkds = new SimpleStringProperty (mkds);
        this.mip = new SimpleStringProperty (mip);
        
        
     
    }

    public final String getMid() {
        return mid.get();
    }

    public final void setMid(String value) {
        mid.set(value);
    }

    public StringProperty midProperty() {
        return mid;
    }

    public final String getMkdprg() {
        return mkdprg.get();
    }

    public final void setMkdprg(String value) {
        mkdprg.set(value);
    }

    public StringProperty mkdprgProperty() {
        return mkdprg;
    }

    public final String getMkds() {
        return mkds.get();
    }

    public final void setMkds(String value) {
        mkds.set(value);
    }

    public StringProperty mkdsProperty() {
        return mkds;
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
    

}
