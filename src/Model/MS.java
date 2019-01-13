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
 * @author HellCat
 */
public class MS {

    private final StringProperty id;
    private final StringProperty kode;
    private final StringProperty nama;
    private final StringProperty status;
    private final StringProperty kondisi;
    private final StringProperty kdlokasi;
    private final StringProperty nmlokasi;
    private final StringProperty ipaddr;
    private final StringProperty getway;
    private final StringProperty mac;

    public MS(String id, String kode, String nama, String status, String kondisi, String kdlokasi, String nmlokasi, String ipaddr, String getway, String mac) {

        this.id = new SimpleStringProperty(id);
        this.kode = new SimpleStringProperty(kode);
        this.nama = new SimpleStringProperty(nama);
        this.status = new SimpleStringProperty(status);
        this.kondisi = new SimpleStringProperty(kondisi);
        this.kdlokasi = new SimpleStringProperty(kdlokasi);
        this.nmlokasi = new SimpleStringProperty(nmlokasi);
        this.ipaddr = new SimpleStringProperty(ipaddr);
        this.getway = new SimpleStringProperty(getway);
        this.mac = new SimpleStringProperty(mac);
    }

    public final String getId() {
        return id.get();
    }

    public final void setId(String value) {
        id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }

    public final String getKode() {
        return kode.get();
    }

    public final void setKode(String value) {
        kode.set(value);
    }

    public StringProperty kodeProperty() {
        return kode;
    }

    public final String getNama() {
        return nama.get();
    }

    public final void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public final String getStatus() {
        return status.get();
    }

    public final void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getKondisi() {
        return kondisi.get();
    }

    public final void setKondisi(String value) {
        kondisi.set(value);
    }

    public StringProperty kondisiProperty() {
        return kondisi;
    }

    public final String getKdlokasi() {
        return kdlokasi.get();
    }

    public final void setKdlokasi(String value) {
        kdlokasi.set(value);
    }

    public StringProperty kdlokasiProperty() {
        return kdlokasi;
    }

    public final String getNmlokasi() {
        return nmlokasi.get();
    }

    public final void setNmlokasi(String value) {
        nmlokasi.set(value);
    }

    public StringProperty nmlokasiProperty() {
        return nmlokasi;
    }

    public final String getIpaddr() {
        return ipaddr.get();
    }

    public final void setIpaddr(String value) {
        ipaddr.set(value);
    }

    public StringProperty ipaddrProperty() {
        return ipaddr;
    }

    public final String getGetway() {
        return getway.get();
    }

    public final void setGetway(String value) {
        getway.set(value);
    }

    public StringProperty getwayProperty() {
        return getway;
    }

    public final String getMac() {
        return mac.get();
    }

    public final void setMac(String value) {
        mac.set(value);
    }

    public StringProperty macProperty() {
        return mac;
    }

}
