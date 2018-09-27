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
public class MK {

    private final StringProperty mks;
    private final StringProperty mns;
    private final StringProperty mket;

    public MK(String mks, String mns, String mket) {
        this.mks = new SimpleStringProperty (mks);
        this.mns = new SimpleStringProperty (mns);
        this.mket = new SimpleStringProperty (mket);
        
        
    }

    public final String getMks() {
        return mks.get();
    }

    public final void setMks(String value) {
        mks.set(value);
    }

    public StringProperty mksProperty() {
        return mks;
    }

    public final String getMns() {
        return mns.get();
    }

    public final void setMns(String value) {
        mns.set(value);
    }

    public StringProperty mnsProperty() {
        return mns;
    }

    public final String getMket() {
        return mket.get();
    }

    public final void setMket(String value) {
        mket.set(value);
    }

    public StringProperty mketProperty() {
        return mket;
    }
    
    

}
