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
 * @author Rexnet
 */
public class MN {
    
    private final StringProperty mip;
    private final StringProperty mmac;
    private final StringProperty mkdprg;
    private final StringProperty mnmprg;
    private final StringProperty mkdlok;
    private final StringProperty mnmlok;
    
    
    
     public MN(String mip, String mmac, String mkdprg, String mnmprg, String mkdlok, String mnmlok) {
        this.mip = new SimpleStringProperty (mip);
        this.mmac = new SimpleStringProperty(mmac);
        this.mkdprg = new SimpleStringProperty (mkdprg);
        this.mnmprg = new SimpleStringProperty (mnmprg);
        this.mkdlok = new SimpleStringProperty (mkdlok);
        this.mnmlok = new SimpleStringProperty (mnmlok);

        
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
        return mip.get();
    }

    public final void setMmac(String value) {
        mmac.set(value);
    }

    public StringProperty mmacProperty() {
        return mmac;
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
    
     public final String getMnmprg() {
        return mnmprg.get();
    }

    public final void setMnmprg(String value) {
        mnmprg.set(value);
    }

    public StringProperty mnmprgProperty() {
        return mnmprg;
    }


    public final String getMkdlok() {
        return mkdlok.get();
    }

    public final void setMkdlok(String value) {
        mkdlok.set(value);
    }

    public StringProperty mkdlokProperty() {
        return mkdlok;
    }

    public final String getMnmlok() {
        return mnmlok.get();
    }

    public final void setMnmlok(String value) {
        mnmlok.set(value);
    }

    public StringProperty mnmlokProperty() {
        return mnmlok;
    }
    

}


