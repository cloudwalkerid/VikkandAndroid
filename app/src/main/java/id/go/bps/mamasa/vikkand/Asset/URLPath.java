package id.go.bps.mamasa.vikkand.Asset;

import id.go.bps.mamasa.vikkand.Entity.ObjectUser;

/**
 * Created by ASUS on 27/06/2018.
 */

public class URLPath {


    private static final String urlBaseBase = "http://vikkandser.bpssulbarapp.com"+"/";
    private static final String urlBase = urlBaseBase+"api/";


    public static String getUrlAuthLogin(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/auth/login";
        }else{
            return urlBase + "responden/auth/login";
        }
    }

    public static String getUrlAuthRefresh(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/auth/refresh";
        }else{
            return urlBase + "responden/auth/refresh";
        }
    }

    public static String getUrlAuthLogout(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/auth/logout";
        }else{
            return urlBase + "responden/auth/logout";
        }
    }

    public static String getUrlPhoto(boolean isPetugas){
        if(isPetugas){
            return urlBaseBase + "img/user/";
        }else{
            return urlBaseBase + "img/responden/";
        }
    }

    public static String getUrlAuthme(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/auth/me";
        }else{
            return urlBase + "responden/auth/me";
        }
    }

    public static String getUrlGetSurvei(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/listSurvei";
        }else{
            return urlBase + "responden/listSurvei";
        }
    }

    public static String getUrlDownloadSurvei(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/downloadSurvei";
        }else{
            return urlBase + "responden/downloadSurvei";
        }
    }

    public static String getUrlUpdate(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/refreshResponden";
        }else{
            return urlBase + "responden/refreshSurvei";
        }
    }

    public static String getUrlSubmit(boolean isPetugas){
        if(isPetugas){
            return urlBase + "petugas/submitResponden";
        }else{
            return urlBase + "responden/submitSurvei";
        }
    }
}
