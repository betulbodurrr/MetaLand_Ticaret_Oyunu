package metaland;

public class Kullanici {
    
    String isim;
    int kullaniciID = 0, yemek = 0, esya = 0, para = 0;
    
    public Kullanici(int id, String ad, String soyad){
        isim = ad+" "+soyad;
        kullaniciID = id;
    }
    
}
