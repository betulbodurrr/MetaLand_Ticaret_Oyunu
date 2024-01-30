package metaland;

import javax.swing.JButton;

public class Arsa extends JButton{
    
    int id, x, y, sahipID = 0, isletmeID;
    String tur = "";
    int isciSayisi = 0, gunSayisi = 0, maas = 0;
    int yoneticiUcret = 0, kullaniciUcret = 0;
    boolean iskur = false;
            
    public Arsa(int x, int y, int id, int sahipID){
        this.x = x;
        this.y = y;
        this.id = id;
        this.sahipID = sahipID;
    }
     
    public String kiraIslemi(){ //bu fonksiyonlardan query dönecek
        return "";
    }
}
