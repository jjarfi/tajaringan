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
public class MJ {

    private final StringProperty mkp;
    private final StringProperty mnp;
    private final StringProperty mjp;
    private final StringProperty mlp;
    private final StringProperty mkonpe;
    private final StringProperty mfoto;

    public MJ(String mkp, String mnp, String mjp, String mlp, String mkonpe, String mfoto) {
        this.mkp = new SimpleStringProperty(mkp);
        this.mnp = new SimpleStringProperty(mnp);
        this.mjp = new SimpleStringProperty(mjp);
        this.mlp = new SimpleStringProperty(mlp);
        this.mkonpe = new SimpleStringProperty(mkonpe);
        this.mfoto = new SimpleStringProperty(mfoto);

    }

    public final String getMkp() {
        return mkp.get();
    }

    public final void setMkp(String value) {
        mkp.set(value);
    }

    public StringProperty mkpProperty() {
        return mkp;
    }

    public final String getMnp() {
        return mnp.get();
    }

    public final void setMnp(String value) {
        mnp.set(value);
    }

    public StringProperty mnpProperty() {
        return mnp;
    }

    public final String getMjp() {
        return mjp.get();
    }

    public final void setMjp(String value) {
        mjp.set(value);
    }

    public StringProperty mjpProperty() {
        return mjp;
    }

    public final String getMlp() {
        return mlp.get();
    }

    public final void setMlp(String value) {
        mlp.set(value);
    }

    public StringProperty mlpProperty() {
        return mlp;
    }

    public final String getMkonpe() {
        return mkonpe.get();
    }

    public final void setMkonpe(String value) {
        mkonpe.set(value);
    }

    public StringProperty mkonpeProperty() {
        return mkonpe;
    }

    public final String getMfoto() {
        return mfoto.get();
    }

    public final void setMfoto(String value) {
        mfoto.set(value);
    }

    public StringProperty mfotoProperty() {
        return mfoto;
    }

}
