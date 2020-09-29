package com.example.nytarticlesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Article {
    String mAbstract;
    String mWebUrl;
    String mSnippet;
    String mLeadParagraph;
    String mImageUrl;
    String mHeadline;
    Author mAuthor;
    String mOriginalByLine;

    public String getmAbstract() {
        return mAbstract;
    }

    public void setmAbstract(String mAbstract) {
        this.mAbstract = mAbstract;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }

    public void setmWebUrl(String mWebUrl) {
        this.mWebUrl = mWebUrl;
    }

    public String getmSnippet() {
        return mSnippet;
    }

    public void setmSnippet(String mSnippet) {
        this.mSnippet = mSnippet;
    }

    public String getmLeadParagraph() {
        return mLeadParagraph;
    }

    public void setmLeadParagraph(String mLeadParagraph) {
        this.mLeadParagraph = mLeadParagraph;
    }

    public String getmImageUrl() {
        return "https://static01.nyt.com/"+mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmHeadline() {
        return mHeadline;
    }

    public void setmHeadline(String mHeadline) {
        this.mHeadline = mHeadline;
    }

    public Author getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(Author mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmOriginalByLine() {
        return mOriginalByLine;
    }

    public void setmOriginalByLine(String mOriginalByLine) {
        this.mOriginalByLine = mOriginalByLine;
    }

    public Article(JSONObject jsonArticle) throws JSONException{
        mAbstract=jsonArticle.getString("abstract");
        mWebUrl=jsonArticle.getString("web_url");
        mSnippet=jsonArticle.getString("snippet");
        mLeadParagraph=jsonArticle.getString("lead_paragraph");
        mHeadline=jsonArticle.getJSONObject("headline").getString("print_headline");
        mOriginalByLine=jsonArticle.getJSONObject("byline").getString("original");
        JSONArray articleMultimedia = jsonArticle.getJSONArray("multimedia");
        if (articleMultimedia.length()>0) {
            JSONObject firstMultimediaObject = articleMultimedia.getJSONObject(0);
            if (firstMultimediaObject!= null)
                mImageUrl = firstMultimediaObject.getString("url");
        }
    }

    public static ArrayList<Article> extractFromJsonResponse(JSONArray listArticles){
        ArrayList<Article> alArticles =new ArrayList<Article>(listArticles.length());
        try{
            int articleSize = listArticles.length();
            for (int i=0;i<articleSize;i++){
                System.out.println(listArticles.get(i).toString());
                Article article=new Article((JSONObject)listArticles.get(i));
                alArticles.add(article);
            }
        } catch(JSONException e){
            e.printStackTrace();
         }
        return alArticles;
    }
}
