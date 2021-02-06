package com.example.yazarlar;

public class Model {
    private String yazarAd, yazarResim, yazarBaslik, yaziUrl, yazi, tarih;


    public Model(String yazarAd, String yazarResim, String yazarBaslik, String yaziUrl, String yazi, String tarih) {
        this.yazarAd = yazarAd;
        this.yazarResim = yazarResim;
        this.yazarBaslik = yazarBaslik;
        this.yaziUrl = yaziUrl;
        this.yazi = yazi;
        this.tarih = tarih;

    }


    public String getYaziUrl() {
        return yaziUrl;
    }

    public String getYazarAd() {
        return yazarAd;
    }

    public String getYazarResim() {
        return yazarResim;
    }

    public String getYazarBaslik() {
        return yazarBaslik;
    }

    public String getYazi() {
        return yazi;
    }

    public String getTarih() {
        return tarih;
    }
}
