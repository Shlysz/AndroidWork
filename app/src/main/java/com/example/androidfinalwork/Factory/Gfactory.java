package com.example.androidfinalwork.Factory;

import com.example.androidfinalwork.Service.UrlService;

public  class  Gfactory
{
    static UrlService urlService =null;
    static void init(){
        urlService = UrlService.getOKhttpService();
    }

    public static UrlService getUrlService(){
        if(urlService == null){
            init();
        }
        return urlService;
    }
}
