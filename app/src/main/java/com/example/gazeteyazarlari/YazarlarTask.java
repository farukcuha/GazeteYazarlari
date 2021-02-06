package com.example.gazeteyazarlari;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class YazarlarTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private String gazeteUrl;
    private Document document;
    private ArrayList<Model> list;
    private RecyclerView recyclerView;
    private int i = 0;

    public YazarlarTask(Context context, String gazeteUrl, ArrayList<Model> list, RecyclerView recyclerView) {
        this.context = context;
        this.gazeteUrl = gazeteUrl;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Yükleniyor...");
        Log.d("a", gazeteUrl);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        switch (gazeteUrl){
            case "https://www.sabah.com.tr/yazarlar":
                sabah();
                break;
            case "https://www.haberturk.com/htyazarlar":
                haberturk();
                break;
            case "https://www.hurriyet.com.tr/yazarlar/":
                hurriyet();
                break;
            case "https://www.karar.com/yazarlar":
                karar();
                break;

            case "https://www.sozcu.com.tr/kategori/yazarlar/":
                sozcu();
                break;
            case "https://www.milliyet.com.tr/yazarlar/":
                milliyet();
                break;

            case "https://www.turkiyegazetesi.com.tr/yazarlar":
                turkiye();
                break;
            case "https://www.yenisafak.com/yazarlar/bugun-yazanlar":
                yenisafak();
                break;
            case "https://www.yeniakit.com.tr/yazarlar/":
                yeniakit();
                break;
            case "https://www.takvim.com.tr/yazarlar":
                takvim();
                break;


        }
        return null;
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        YazarlarAdapter adapter = new YazarlarAdapter(list, context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();



    }



    private void sabah(){
        try {

            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("figure[class=multiple boxShadowSet]").size());

            for (Element element : document.select("figure[class=multiple boxShadowSet]")){
                String yaziUrl = "https://www.sabah.com.tr" + element.select("figcaption > a").attr("href");
                Document document = Jsoup.connect(yaziUrl).get();
                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("h3[class=postTitle]").text(),
                        element.select("img[class=lazyload]").attr("data-src"),
                        element.select("strong[class=postCaption]").text(),
                        yaziUrl,
                        clearText(yaziUrl, "div.newsBox"),
                        document.select("span.postInfo").text()
                ));
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void haberturk() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("div[class=author type2]").size());
            for (Element element : document.select("div[class=author type2]")){
                String yaziUrl = "https://www.haberturk.com"+element.select("div.info").parents().attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("span[class=name]").text(),
                        element.select("div[class=author-info] > figure > img").attr("src"),
                        element.select("span.last-article-title").text(),
                        yaziUrl,
                        clearText(yaziUrl, "article[class=content type1 newsArticle]"),
                        document.select("span.date > time").first().text()

                        ));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void hurriyet() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("a[class=author-box]").size());
            for (Element element : document.select("a[class=author-box]")){
                String yaziUrl = "https://www.hurriyet.com.tr" + element.attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("span.name").text(),
                        element.select("div.author-box-image > img").attr("src"),
                        element.select("span.title").text(),
                        yaziUrl,
                        clearText( yaziUrl,"div[class=article-content news-text]"),
                        document.select("div.article-date").text()

                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void karar() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("div[class=title line-camp line-2]").size());
            for (Element element : document.select("div[class=title line-camp line-2]")) {
                String yaziUrl ="https://www.karar.com"+element.parent().attr("href");
                Document yazidoc = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.parent().select("div[class=author-name]").text(),
                        element.parent().select("img.lazy").attr("data-src"),
                        element.text(),
                        yaziUrl,
                        clearText(yaziUrl, "div.text-content"),
                        yazidoc.select("div.content-date > time").text()
                ));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void sozcu() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("div[class=cas-inner]").size());
            for (Element element : document.select("div[class=cas-inner]")){
                String yaziUrl = element.select("a").attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                String eskiresim = element.select("span[class=news-img]").attr("style");
                char[] yeniresim = new char[eskiresim.length()-20];
                eskiresim.getChars(21, eskiresim.length()-1, yeniresim,0);
                Log.d("a", String.valueOf(yeniresim));

                list.add(new Model(
                        element.select("div[class=columnist-name]").text(),
                        String.valueOf(yeniresim),
                        element.select("div[class=c-text]").text(),
                        yaziUrl,
                        clearText(yaziUrl, "div[class=author-the-content content-element]"),
                        document.select("span[class=date]").text()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void milliyet() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("a[class=card-listing__link]").size());
            for (Element element : document.select("a[class=card-listing__link]")){
                String yaziUrl = "https://www.milliyet.com.tr"+element.select("a").attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("span[class=card-listing__author-name]").text(),
                        element.select("img[class=card-listing__image]").attr("src"),
                        element.select("span[class=card-listing__title]").text(),
                        yaziUrl,
                        clearText(yaziUrl, "div[class=article__content]"),
                        document.select("span[class=article__date]").text()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void turkiye() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("table[class=yazar-kutu]").size());
            for (Element element : document.select("table[class=yazar-kutu]")){
                String yaziUrl = element.select("tbody > tr > td > a").attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("span[id=contentOrta_dl_yazarlar_Label1_0]").text(),
                        element.select("img[id=contentOrta_dl_yazarlar_img_yazar_0]").attr("src"),
                        element.select("span[id=contentOrta_dl_yazarlar_Label2_0]").text(),
                        yaziUrl,
                        clearText(yaziUrl, "div[style=font-size: 17.3333px;]"),
                        document.select("p[class=article-date]").text()
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void yenisafak() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("a[class=article]").size());
            for (Element element : document.select("a[class=article]")){
                String yaziUrl = "https://www.yenisafak.com"+element.attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("div[class=name]").text(),
                        element.select("div[class=photo circle] > img").attr("src"),
                        element.attr("title"),
                        yaziUrl,
                        clearText(yaziUrl, "div[class=text]"),
                        document.select("time[class=item time]").text()
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void yeniakit() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("p[class=postTitle]").size());
            for (Element element : document.select("p[class=postTitle]")){
                String yaziUrl = element.parent().parent().select("a").attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.parent().select("p[class=authorName]").text(),
                        element.parent().parent().select("img[class=b-lazy]").attr("data-src"),
                        element.parent().select("p[class=postTitle]").text(),
                        yaziUrl,
                        clearText(yaziUrl, "div[class=content forReklamUp]"),
                        document.select("div[class=date]").text()
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void takvim() {
        try {
            document = Jsoup.connect(gazeteUrl).ignoreContentType(true).get();
            progressDialog.setMax(document.select("ul[class=list] > li").size());
            for (Element element : document.select("ul[class=list] > li")){
                String yaziUrl = "https://www.takvim.com.tr"+element.select("figure > a").attr("href");
                Document document = Jsoup.connect(yaziUrl).get();

                i++;
                progressDialog.setProgress(i);

                list.add(new Model(
                        element.select("ol > li[class=title] > a").text(),
                        element.select("figure > a > img").attr("src"),
                        element.select("ol > li[class=writing]").text(),
                        yaziUrl,
                        clearText(yaziUrl, "article[id=haberDescription]"),
                        document.select("div[class=info] > ul > li").text()
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String clearText(String url, String html_yazi){
        String strWithNweLines = null;

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(html_yazi);
            Document.OutputSettings outputSettings = new Document.OutputSettings();
            document.outputSettings(outputSettings);
            outputSettings.prettyPrint(false);
            elements.select("em").remove();
            elements.select("img").remove();
            elements.select("div").remove();
            elements.select("br").before("\\n");
            elements.select("br").after("\\n");

            String str = elements.html()
                    .replaceAll("\\\\n", "\n")
                    .replaceAll("&nbsp;", "");

            strWithNweLines= Jsoup.clean(str, "", Whitelist.none(), outputSettings);


        } catch (IOException e) {
            e.printStackTrace();
        }


        return strWithNweLines;
    }
}
