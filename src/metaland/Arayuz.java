package metaland;

import java.sql.*;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Arayuz {
       
    int kullaniciID = -1, kullaniciIndex = 0, girisID = -1;
    int ilanIsletmeID = -1, elendi = 0, iskurID = -1;
    String query = "";
    
    boolean ilanKey = false;
    
    JFrame f = new JFrame();
    JPanel background = new JPanel();
    
    String isim = "", pass = "";
    
    JTextField adSoyad = new JTextField();
    JTextField sifre = new JTextField();
    JTextField yeniYemek = new JTextField();
    JTextField yeniEsya = new JTextField();
    JTextField yeniPara = new JTextField();
    JTextField isciSayisi = new JTextField();
    JTextField gunSayisi = new JTextField();
    JTextField maasBilgisi = new JTextField();
    JTextField zamanIleri = new JTextField();
    JTextField kullaniciUcret = new JTextField();
    JTextField fiyat = new JTextField();
       
    JMenuItem ilanlar = new JMenuItem("İş İlanı");
    JMenuItem arsaAl = new JMenuItem("Arsa Al");
    JMenuItem esyaAl = new JMenuItem("Eşya Al");
    JMenuItem yemekAl = new JMenuItem("Yemek Al");
    JMenuItem sahipID = new JMenuItem();
    JMenuItem ilanBasvur = new JMenuItem("Başvur");
    JMenuItem isletmeKur = new JMenuItem("İş-Kur");
    
    JButton adSoyadOnayla = new JButton("Onayla");
    JButton degerDegistir = new JButton("Degerleri Degistir");
    JButton miktarGoster = new JButton("Goster");
    JButton ilanOlustur = new JButton("İş İlanı Oluştur");
    JButton ilanOnay = new JButton();
    JButton zamanOnay = new JButton();
    JButton isletmeOnay = new JButton("Kur");
    
    Calendar takvim = Calendar.getInstance();
    
    JComboBox oyuncular = new JComboBox();
    
    JPopupMenu islemler = new JPopupMenu();
    JPopupMenu ilan = new JPopupMenu();
    
    JLabel yemek = new JLabel();
    JLabel esya = new JLabel();
    JLabel para = new JLabel();
    JLabel adSoyadBilgisi = new JLabel();
    JLabel amountOfEmployee = new JLabel();
    JLabel meslek = new JLabel();
        
    ResultSet rs, rs2, rs3;
    Statement stm, stm2, miktarStm, miktarStm2, yoneticiUpdateStm, miktarEksiltmeStm;
    Statement miktarVerileriStm, ilanStm, calisanStm;
    
    JRadioButton marketKur = new JRadioButton("Market");
    JRadioButton magazaKur = new JRadioButton("Magaza");
    JRadioButton emlakKur = new JRadioButton("Emlak");
    
    ButtonGroup bg = new ButtonGroup();
    
    //stm olusturduktan sonra con.createStatement() yapmayi unutma !!
    
    Arsa a;
    Kullanici k, girisYapanOyuncu;
    
    ArrayList <Arsa> areas = new ArrayList <Arsa>();
    ArrayList <Kullanici> users = new ArrayList <Kullanici>();   
       
    Timer zaman = new Timer();
    TimerTask gunKontrol = new TimerTask(){
        @Override
        public void run() { //her gün sonunda ne olacağı buraya yazılacak.
            System.out.println("Zaman Degisti");
            try {
                miktarGuncelle();
                maasYatir();               
            } catch (SQLException ex) {
                Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    };
    
    public void zamanYolculugu(int gun) throws SQLException{
        for (int i = 0; i < gun; i++) {
            miktarGuncelle();
            maasYatir();
        }
    }
    
    public void zamanBaslat(){
        zaman.schedule(gunKontrol, 30000, 30000); // 1 dakika = 60.000 ms
    }
    
    public void miktarGuncelle() throws SQLException{
       
        rs = miktarVerileriStm.executeQuery("select * from miktar");        
        
        while(rs.next()){
            
            rs2 = calisanStm.executeQuery("select * from calisan where kullaniciNo="+rs.getInt("kullaniciNo"));
                
            girisYapanOyuncu = users.get(rs.getInt("kullaniciNo"));
            
            girisYapanOyuncu.yemek = rs.getInt("yemek");
            girisYapanOyuncu.esya = rs.getInt("esya");
            girisYapanOyuncu.para = rs.getInt("para");
        
            if(rs2.next()){
            
                if(rs2.getString("isletmeTuru").equals("magaza") || rs2.getString("isletmeTuru").equals("emlak"))
                    girisYapanOyuncu.yemek -=25;
            
                if(rs2.getString("isletmeTuru").equals("market") || rs2.getString("isletmeTuru").equals("emlak"))
                    girisYapanOyuncu.esya -=25;           
            }
            
            else{
                girisYapanOyuncu.yemek -=25;
                girisYapanOyuncu.esya -=25;
            }
                
            girisYapanOyuncu.para -=25;
        
            yeniYemek.setText(Integer.toString(girisYapanOyuncu.yemek));
            yeniEsya.setText(Integer.toString(girisYapanOyuncu.esya));
            yeniPara.setText(Integer.toString(girisYapanOyuncu.para));
            
            if(girisID != 0){
                yemek.setText("Yemek Miktari: "+Integer.toString(users.get(girisID).yemek));
                esya.setText("Esya Miktari: "+Integer.toString(users.get(girisID).esya));
                para.setText("Para Miktari: "+Integer.toString(users.get(girisID).para));
            }
        
            miktarEksiltmeStm.executeUpdate("update miktar set yemek="+girisYapanOyuncu.yemek+", esya="+girisYapanOyuncu.esya+", para="+girisYapanOyuncu.para+" where kullaniciNo="+rs.getInt("kullaniciNo"));
            
            if((girisYapanOyuncu.yemek <= 0 || girisYapanOyuncu.esya <= 0 || girisYapanOyuncu.para <= 0) && elendi == 0 && girisID == girisYapanOyuncu.kullaniciID){
                adSoyadBilgisi.setText(adSoyadBilgisi.getText()+" ELENDİ");
                adSoyadBilgisi.setForeground(Color.white);
                elendi = 1;
                for (Arsa area : areas) {
                    area.setEnabled(false);
                }
            }
        }
    }
    
    public Arayuz(){
        query = ""; // her tablo işlemeleri için query yazılması gerek
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prolab3", "root", "123456");
            System.out.println(con);                
            stm = con.createStatement();
            stm2 = con.createStatement();
            miktarStm = con.createStatement();
            miktarStm2 = con.createStatement();
            yoneticiUpdateStm = con.createStatement();
            miktarEksiltmeStm = con.createStatement();
            miktarVerileriStm = con.createStatement();
            ilanStm = con.createStatement();
            calisanStm = con.createStatement();
            
            //query = "insert into oyun values(64, 100, 100, 1000)";
            //stm.executeUpdate(query);
                      
            //con.close();        
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaLand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MetaLand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    public void maasYatir() throws SQLException{
        rs = calisanStm.executeQuery("select * from calisan");
           
        while(rs.next()){        
            
            rs2 = stm.executeQuery("select * from isletme where alanNo="+rs.getInt("isletmeID"));
            rs2.next();
            
            String baslangicTarih = rs.getString("isBaslangic");            
            int baslangicGun = Integer.parseInt(baslangicTarih.substring(0,baslangicTarih.indexOf(".")));
            
            String bitisTarih = rs.getString("isBitis");
            int bitisGun = Integer.parseInt(bitisTarih.substring(0,bitisTarih.indexOf(".")));
            
            if(bitisGun-baslangicGun == rs.getInt("calisilanGun") || 31-baslangicGun+bitisGun == rs.getInt("calisilanGun")){
                meslek.setVisible(false);
                stm.executeUpdate("delete from calisan where kullaniciNo="+rs.getInt("kullaniciNo"));
                stm.executeUpdate("update isletme set calisanSayisi="+(rs2.getInt("calisanSayisi")-1)+" where kullaniciNo="+rs.getInt("kullaniciNo"));                
                break;
            }
            
            rs3 = ilanStm.executeQuery("select * from miktar where kullaniciNo="+rs.getInt("kullaniciNo"));
            rs3.next();
            
            int yeniPara = rs3.getInt("para")+rs2.getInt("kullaniciUcret")+rs2.getInt("yoneticiUcret");
            
            ilanStm.executeUpdate("update miktar set para="+yeniPara+" where kullaniciNo="+rs.getInt("kullaniciNo"));
            ilanStm.executeUpdate("update calisan set calisilanGun="+(rs.getInt("calisilanGun")+1)+", calismaSaat="+(rs.getInt("calismaSaat")+24));           
        }
    }
    
    public void goster(){      
        background.setBounds(0, 0, 800, 800);
        background.setBackground(Color.gray);
        f.add(background);
        
        f.setSize(1010,800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);      
    }
    
    public void components(){

        amountOfEmployee.setBounds(0,0,200,30);
        ilan.add(amountOfEmployee);

        ilan.add(ilanBasvur);
        
        adSoyad.setBounds(350, 50, 200, 30);
        adSoyad.setText("Ad Soyad");
        f.add(adSoyad);               
        
        adSoyadOnayla.setBounds(560, 85, 80, 30);
        adSoyadOnayla.setText("Giris");
        f.add(adSoyadOnayla);

        sifre.setBounds(350, 85, 200, 30);
        sifre.setText("Şifre");
        f.add(sifre);
      
        zamanIleri.setBounds(820,540,50,30);
        zamanIleri.setText("İleri");
        zamanIleri.setBackground(Color.gray);
        zamanIleri.setVisible(false);
        background.add(zamanIleri);
        
        zamanOnay.setBounds(750,500, 200, 30);
        zamanOnay.setText("Zamani ileri al");
        zamanOnay.setVisible(false);
        background.add(zamanOnay);
        
        degerDegistir.setBounds(750,200,200,30);
        degerDegistir.setVisible(false);
        background.add(degerDegistir);
        
        miktarGoster.setBounds(900, 300, 80, 30);
        miktarGoster.setVisible(false);
        background.add(miktarGoster);
        
        oyuncular.setBounds(750,250,200,30);
        oyuncular.setVisible(false);
        background.add(oyuncular);
        
        meslek.setBounds(800, 35, 200, 30);
        meslek.setVisible(false);
        meslek.setForeground(Color.orange);
        background.add(meslek);
        
        adSoyadBilgisi.setBounds(800, 55, 200, 30);
        adSoyadBilgisi.setForeground(Color.green);
        background.add(adSoyadBilgisi);
        adSoyadBilgisi.setVisible(false);
        
        yemek.setBounds(800, 85, 200, 30);
        yemek.setText("Yemek Miktari: oo");  
        yemek.setForeground(Color.green);
        background.add(yemek);
        yemek.setVisible(false);
        
        esya.setBounds(800, 115, 200, 30);
        esya.setText("Esya Miktari : oo");
        esya.setForeground(Color.green);
        background.add(esya);
        esya.setVisible(false);
        
        para.setBounds(800, 145, 200, 30);
        para.setText("Para Miktari : oo");
        para.setForeground(Color.green);
        background.add(para);
        para.setVisible(false); 
        
        yeniYemek.setBounds(720,300,50,30);
        yeniYemek.setText("yemek");
        yeniYemek.setBackground(Color.red);
        yeniYemek.setVisible(false);
        background.add(yeniYemek);
        
        yeniEsya.setBounds(780,300,50,30);
        yeniEsya.setText("esya");
        yeniEsya.setBackground(Color.green);
        yeniEsya.setVisible(false);
        background.add(yeniEsya);
        
        yeniPara.setBounds(840,300,50,30);
        yeniPara.setText("para");
        yeniPara.setBackground(Color.blue);
        yeniPara.setVisible(false);
        background.add(yeniPara);               
        
        kullaniciUcret.setBounds(710,460,80,30);
        kullaniciUcret.setBackground(Color.red);
        kullaniciUcret.setForeground(Color.white);
        kullaniciUcret.setText("Maaş");
        kullaniciUcret.setVisible(false);
        background.add(kullaniciUcret);
        
        fiyat.setBounds(800, 460, 80, 30);
        fiyat.setBackground(Color.green);
        fiyat.setForeground(Color.black);
        fiyat.setText("Fiyat");
        fiyat.setVisible(false);
        background.add(fiyat);
        
        ilanOlustur.setBounds(750,340,200,30);
        ilanOlustur.setVisible(false);
        background.add(ilanOlustur);
        
        ilanOnay.setBounds(840,380,80,30);
        ilanOnay.setText("Done");
        ilanOnay.setBackground(Color.white);
        ilanOnay.setVisible(false);
        background.add(ilanOnay);
        
        gunSayisi.setBounds(780,380,50,30);
        gunSayisi.setText("Gün S.");
        gunSayisi.setBackground(Color.magenta);
        gunSayisi.setVisible(false);
        background.add(gunSayisi);
        
        bg.add(marketKur);
        bg.add(magazaKur);
        bg.add(emlakKur);
        
        marketKur.setBounds(710, 420, 80, 30);
        marketKur.setVisible(false);
        background.add(marketKur);
        
        magazaKur.setBounds(800, 420, 80, 30);
        magazaKur.setVisible(false);
        background.add(magazaKur);
        
        emlakKur.setBounds(890, 420, 80, 30);
        emlakKur.setVisible(false);
        background.add(emlakKur);
        
        isletmeOnay.setBounds(890, 460, 80, 30);
        isletmeOnay.setBackground(Color.gray);
        isletmeOnay.setForeground(Color.green);
        isletmeOnay.setVisible(false);
        background.add(isletmeOnay);
        
        //Buradan sonrası basma olayları
     
        yemekAl.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){                  
                try {
                    rs = miktarStm.executeQuery("select * from miktar where kullaniciNo="+girisID);
                    rs.next();
                    
                    if(rs.getInt("para") >= 100){
                        miktarStm.executeUpdate("update miktar set yemek="+(rs.getInt("yemek")+20)+", para="+(rs.getInt("para")-100)+" where kullaniciNo="+girisID);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });
        
        esyaAl.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){                  
                try {
                    rs = miktarStm.executeQuery("select * from miktar where kullaniciNo="+girisID);
                    rs.next();
                    
                    if(rs.getInt("para") >= 100){
                        miktarStm.executeUpdate("update miktar set esya="+(rs.getInt("esya")+20)+", para="+(rs.getInt("para")-100)+" where kullaniciNo="+girisID);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });
        
        arsaAl.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){                  
                try {
                    rs = miktarStm.executeQuery("select * from miktar where kullaniciNo="+girisID);
                    rs.next();
                                       
                    int x =(int) (MouseInfo.getPointerInfo().getLocation().getX()-50)/81;
                    int y =(int) (MouseInfo.getPointerInfo().getLocation().getY()-50)/81;
                    
                    if(rs.getInt("para") >= 800 && rs.getInt("arsaSayisi") < 2){
                        
                        rs2 = miktarStm2.executeQuery("select * from alan");
                        
                        while(rs2.next()){
                            if(rs2.getString("alanTuru").equals("Arsa") && rs2.getInt("sahipID") == 0){ // arsaysa
                                int alan = rs2.getInt("alanNo");
                                miktarStm2.executeUpdate("update alan set sahipID="+girisID+" where alanNo="+alan);
                                miktarStm.executeUpdate("update miktar set para="+(rs.getInt("para")-800)+", arsaSayisi="+(rs.getInt("arsaSayisi")+1)+" where kullaniciNo="+girisID);
                                
                                areas.get(alan-1).sahipID = girisID;
                                
                                break;
                            }
                        }
                        
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });
        
        ilanBasvur.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                
                int x =(int) (MouseInfo.getPointerInfo().getLocation().getX()-50)/81;
                int y =(int) (MouseInfo.getPointerInfo().getLocation().getY()-50)/81;
                
                int gun = 0, ay = 0;
                
                String tarih = takvim.get(Calendar.DATE)+"."+(takvim.get(Calendar.MONTH)+1)+"."+takvim.get(Calendar.YEAR);
                
                ilanIsletmeID = 8*y+x+1;
                System.out.println(ilanIsletmeID);
                
                try {
                    rs = calisanStm.executeQuery("select * from isletme where alanNo="+ilanIsletmeID);
                    rs.next();
                    
                    gun = takvim.get(Calendar.DATE) + +rs.getInt("ilanGun");
                    ay = takvim.get(Calendar.MONTH)+1;
                    
                    if(gun > 31){
                        gun -= 31;
                        ay++;
                    }
                    
                    String bitisTarih = (gun+"."+ay+"."+takvim.get(Calendar.YEAR));
                   
                    if(girisID != 0){
                        calisanStm.executeUpdate("insert into calisan (kullaniciNo, isBaslangic, isBitis, isletmeTuru, isletmeID) values("+girisID+", '"+tarih+"','"+bitisTarih+"','"+rs.getString("isletmeTuru")+"' ,"+ilanIsletmeID+")");
                    
                        rs = calisanStm.executeQuery("select * from isletme where alanNo="+ilanIsletmeID);
                        rs.next();
                    
                         meslek.setVisible(true);
                        
                        if(rs.getString("isletmeTuru").equals("market"))
                            meslek.setText("Kasiyer");
                        
                        if(rs.getString("isletmeTuru").equals("magaza"))
                            meslek.setText("Satış Görevlisi");
                        
                        if(rs.getString("isletmeTuru").equals("emlak"))
                            meslek.setText("Emlakçı");
                        
                        calisanStm.executeUpdate("update isletme set calisanSayisi="+(rs.getInt("calisanSayisi")+1)+" where alanNo="+ilanIsletmeID);                                              
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }    
            }  
        });
        
        ilanlar.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){                  
                
                int x =(int) (MouseInfo.getPointerInfo().getLocation().getX()-50)/81;
                int y =(int) (MouseInfo.getPointerInfo().getLocation().getY()-50)/81;
                
                try {
                    rs = ilanStm.executeQuery("select * from isletme where alanNo="+(8*y+x+1));
                    rs.next();                    
                    
                    if(rs.getInt("ilanVerildi") == 1){
                        areas.get(8*y+x).isciSayisi = rs.getInt("kapasite")-rs.getInt("calisanSayisi");
                        areas.get(8*y+x).gunSayisi = rs.getInt("ilanGun");
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                amountOfEmployee.setText(Integer.toString(areas.get(8*y+x).isciSayisi)+" işçi - "+areas.get(8*y+x).gunSayisi+" gün");
                
                ilan.show(background, x*81+50, y*81+50);
            }  
        });
        
        ilanOnay.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) { 
                System.out.println(areas.get(ilanIsletmeID-1).id);
                ilanOlustur.setEnabled(true);
                
                try {
                    rs = ilanStm.executeQuery("select * from isletme where alanNo="+ilanIsletmeID);
                    rs.next();                    
                    
                    areas.get(ilanIsletmeID-1).isciSayisi = rs.getInt("kapasite")-rs.getInt("calisanSayisi");
                    
                    if(girisID == 0)
                        areas.get(ilanIsletmeID-1).yoneticiUcret = rs.getInt("yoneticiUcret");
                    
                    else
                        areas.get(ilanIsletmeID-1).kullaniciUcret = rs.getInt("kullaniciUcret");
                    
                    if(rs.getInt("ilanVerildi") == 0)
                        ilanStm.executeUpdate("update isletme set ilanVerildi = 1 where alanNo="+ilanIsletmeID);
                       
                    areas.get(ilanIsletmeID-1).gunSayisi = Integer.parseInt(gunSayisi.getText());
                    
                    ilanStm.executeUpdate("update isletme set ilanGun="+areas.get(ilanIsletmeID-1).gunSayisi+" where alanNo="+ilanIsletmeID);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
                                           
                isciSayisi.setVisible(false);
                gunSayisi.setVisible(false);
                maasBilgisi.setVisible(false);
                ilanOnay.setVisible(false);
            }  
        });
        
        ilanOlustur.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                
                for (Arsa area : areas) {
                    if(area.isletmeID != 0 && area.sahipID == girisID && area.isciSayisi == 0){
                        area.setBackground(Color.white);
                        ilanKey = true;
                    }
                }
                
                ilanOlustur.setEnabled(false);
            }  
        });
        
        isletmeKur.addActionListener(new ActionListener(){  ///////////////////////////////////////////////////
            public void actionPerformed(ActionEvent e){ 
                for (Arsa area : areas) {
                    if(area.sahipID == girisID && area.isletmeID == 0){
                        area.iskur = true;
                        area.setBackground(Color.magenta);
                        marketKur.setVisible(true);
                        magazaKur.setVisible(true);
                        emlakKur.setVisible(true);
                        isletmeOnay.setVisible(true);
                        kullaniciUcret.setVisible(true);
                        fiyat.setVisible(true);
                    }
                }               
            }  
        });
        
        isletmeOnay.addActionListener(new ActionListener(){  ///////////////////////////////////////////////////
            public void actionPerformed(ActionEvent e){ 
                try {
                    rs = miktarStm.executeQuery("select * from miktar where kullaniciNo="+girisID);
                    rs.next();
                    
                    String isletme = "";
                    
                    if(rs.getInt("para") > 1000){ // isletme kurmak 1000 lira
                        miktarStm.executeUpdate("update miktar set para="+(rs.getInt("para")-1000)+", arsaSayisi="+(rs.getInt("arsaSayisi")-1)+" where kullaniciNo="+girisID);                       
                        
                        if(marketKur.isSelected()){ 
                            isletme = "market";
                            areas.get(iskurID-1).isletmeID = 1;
                        }
                        
                        if(magazaKur.isSelected()){ 
                            isletme = "magaza";
                            areas.get(iskurID-1).isletmeID = 3;
                        }
                        
                        if(emlakKur.isSelected()){ 
                            isletme = "emlak";
                            areas.get(iskurID-1).isletmeID = 2;
                        }
                        
                        stm.executeUpdate("update alan set alanTuru='"+isletme+"' where alanNo="+iskurID);
                        stm.executeUpdate("insert into isletme values("+areas.get(iskurID-1).id+", '"+isletme+"' , 0, "+kullaniciUcret.getText()+", 0, 3, 0, 0, 0 ,0 ,0)");                        
                        
                        if(isletme.equals("market"))
                            stm.executeUpdate("update isletme set yiyecekUcret ="+fiyat.getText()+" where alanNo="+areas.get(iskurID-1).id);
                        
                        if(isletme.equals("magaza"))
                            stm.executeUpdate("update isletme set esyaUcret ="+fiyat.getText()+" where alanNo="+areas.get(iskurID-1).id);
                        
                        for (Arsa area : areas) {
                            if(area.sahipID == girisID && area.isletmeID == 0)
                                area.setBackground(Color.gray);
                        }
                        
                        areas.get(iskurID-1).setText(isletme);
                        areas.get(iskurID-1).setBackground(Color.yellow);
                        
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });
        
        adSoyadOnayla.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                try {       
                    girisOnay();
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });  
        
        miktarGoster.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                isim = (String)oyuncular.getSelectedItem();
                System.out.println(isim);  
                
                degerDegistir.setEnabled(true);
                
                for (Kullanici user : users)
                    if(user.isim.equals(isim)){
                        k = user;
                        kullaniciID = user.kullaniciID; 
                        kullaniciIndex = users.indexOf(user);
                        System.out.println(kullaniciIndex);
                        yeniYemek.setText(Integer.toString(user.yemek));
                        yeniEsya.setText(Integer.toString(user.esya));
                        yeniPara.setText(Integer.toString(user.para));
                    }                                          
            }  
        });
        
        degerDegistir.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                
                degerDegistir.setEnabled(false);
                //k = users.get(kullaniciIndex);
                
                try {
                    yoneticiUpdateStm.executeUpdate("update miktar set yemek = "+yeniYemek.getText()+", esya = "+yeniEsya.getText()+", para = "+yeniPara.getText()+" where kullaniciNo = "+kullaniciID);
                    k.yemek = Integer.parseInt(yeniYemek.getText());
                    k.esya = Integer.parseInt(yeniEsya.getText());
                    k.para = Integer.parseInt(yeniPara.getText());
                    
                    if(girisID != 0){
                        yemek.setText("Yemek Miktari: "+Integer.toString(k.yemek));
                        esya.setText("Esya Miktari: "+Integer.toString(k.esya));
                        para.setText("Para Miktari: "+Integer.toString(k.para));
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }  
        }); 
        
        zamanOnay.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                try {
                    zamanYolculugu(Integer.parseInt(zamanIleri.getText()));
                } catch (SQLException ex) {
                    Logger.getLogger(Arayuz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        });
    }
    
    public void arsaEkrani() throws SQLException{
        
        rs = stm2.executeQuery("select * from alan");
        background.setLayout(null);
        ilanOlustur.setVisible(true);
       
        int i = 0;
        
        while(rs.next()) {
            
            a = new Arsa(i%8, i/8, rs.getInt("alanNo"), rs.getInt("sahipID"));
            a.setBounds(a.x*81+50, a.y*81+50, 80, 80);
            a.setText(Integer.toString(a.id));
            a.setBackground(Color.gray);
            a.tur = rs.getString("alanTuru");
                     
            if(rs.getString("alanTuru").equals("market")){
                a.isletmeID = 1;
                a.setBackground(Color.yellow);
                a.setText("Market");
            }
            
            if(rs.getString("alanTuru").equals("emlak")){
                a.isletmeID = 2;
                a.setBackground(Color.yellow);
                a.setText("Emlak");
            }
            
            if(rs.getString("alanTuru").equals("magaza")){
                a.isletmeID = 3;
                a.setBackground(Color.yellow);
                a.setText("Magaza");
            }
            
            a.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){
                    
                    for (Arsa area : areas)
                        if(area.isletmeID != 0 && area.sahipID == girisID) 
                            area.setBackground(Color.yellow);                                      

                    int x =(int) (MouseInfo.getPointerInfo().getLocation().getX()-50)/81;
                    int y =(int) (MouseInfo.getPointerInfo().getLocation().getY()-50)/81;
                    //System.out.println(x +" and "+ y);
                    //System.out.println(areas.get(8*y+x).id);
                    
                    if(ilanKey){
                        ilanIsletmeID = areas.get(8*y+x).id;
                        ilanKey = false; // ilan için arsaya tıkladım ve id bilgisini aldım. işim bitti key = false ;)

                        isciSayisi.setVisible(true);
                        gunSayisi.setVisible(true);
                        maasBilgisi.setVisible(true);
                        ilanOnay.setVisible(true);
                    }
                    
                    if(areas.get(8*y+x).iskur){ ///////////////////////////////////////////
                        areas.get(8*y+x).iskur = false;
                        iskurID = areas.get(8*y+x).id;
                    }
                    
                    //Bunlar popup menude çıkanlar
                    
                    ilanlar.setVisible(false);
                    yemekAl.setVisible(false);
                    arsaAl.setVisible(false);
                    esyaAl.setVisible(false);
                    sahipID.setVisible(false);
                    isletmeKur.setVisible(false);
                    
                    sahipID.setText("SahipID: "+Integer.toString(areas.get(8*y+x).sahipID));
                    sahipID.setVisible(true);
                    sahipID.setEnabled(false);
                    
                    switch(areas.get(8*y+x).isletmeID){
                        case 1: 
                            ilanlar.setVisible(true);
                            yemekAl.setVisible(true);
                            break;
                        case 2:
                            ilanlar.setVisible(true);
                            arsaAl.setVisible(true);
                            isletmeKur.setVisible(true);
                            break;
                        case 3:
                            ilanlar.setVisible(true);
                            esyaAl.setVisible(true);
                            break;                           
                    }
                    
                    islemler.show(background, x*81+50, y*81+50);
                }  
            });
            
            background.add(a);
            areas.add(a);
            i++;
        }
                     
        background.setBackground(Color.black);
        
        yemek.setVisible(true);
        esya.setVisible(true);
        para.setVisible(true);
        adSoyadBilgisi.setText(Integer.toString(girisID)+" - "+users.get(girisID).isim.toUpperCase());
        adSoyadBilgisi.setForeground(Color.red);
        adSoyadBilgisi.setVisible(true);
        
        if(kullaniciID != 0)
            setMiktar();
        
        else{ //id = 0 ise
            yoneticiIslemler();
        }
        
        islemler.add(ilanlar);
        islemler.add(arsaAl);
        islemler.add(esyaAl);
        islemler.add(yemekAl);
        islemler.add(sahipID);
        islemler.add(isletmeKur);
    }   
    
    public void yoneticiIslemler() throws SQLException{
        degerDegistir.setVisible(true);
        degerDegistir.setEnabled(false);
        oyuncular.setVisible(true);
        yeniYemek.setVisible(true);
        yeniEsya.setVisible(true);
        yeniPara.setVisible(true);
        miktarGoster.setVisible(true);
        
        zamanIleri.setVisible(true);
        zamanOnay.setVisible(true);
        
        miktarAyarla();
    }
    
    public void miktarAyarla() throws SQLException{
        rs = miktarStm2.executeQuery("select * from miktar");
        rs.next();
        
        for (Kullanici u : users) {
            if(u.kullaniciID == rs.getInt("kullaniciNo")){
                u.yemek = rs.getInt("yemek");
                u.esya = rs.getInt("esya");
                u.para = rs.getInt("para");
                rs.next();
            }
        }
    }
    
    public void setMiktar() throws SQLException{
        
        rs2 = stm2.executeQuery("select * from miktar");
        int tmp = 0;
        
        while(rs2.next()){
            if(rs2.getInt("kullaniciNo")==girisID)
                tmp++;
        }
        
        if(tmp == 0)
            stm2.executeUpdate("insert into miktar values("+girisID+", 100,100,1000,0)");
        
        rs = miktarStm.executeQuery("select * from miktar where kullaniciNo = "+kullaniciID);
        rs.next(); //Burayı çözmem birkaç saat aldı. (nokta) bunu yazmam gerek !             
        
        yemek.setText("Yemek Miktari: "+rs.getInt("yemek")); 
        esya.setText("Esya Miktari: "+rs.getInt("esya"));
        para.setText("Para Miktari: "+rs.getInt("para"));
        
        if((rs.getInt("yemek") <= 0 || rs.getInt("esya") <= 0 || rs.getInt("para") <= 0) && elendi == 0 && girisID != 0){
                adSoyadBilgisi.setText(adSoyadBilgisi.getText()+" ELENDİ");
                adSoyadBilgisi.setForeground(Color.white);
                elendi = 1;
                for (Arsa area : areas) {
                    area.setEnabled(false);
                }
            }
        
        miktarAyarla();
    }
    
    public void girisOnay() throws SQLException{
        isim = adSoyad.getText();
        pass = sifre.getText();
        
        rs = stm.executeQuery("select * from kullanici");
        //rs3 = miktarStm.executeQuery("select * from miktar");        
        
        if(!isim.equals("")){             
            while(rs.next()){                
                if(isim.trim().equalsIgnoreCase(rs.getString("kullaniciAdi")+" "+rs.getString("kullaniciSoyadi")) && pass.equals(rs.getString("kullaniciSifre"))){
                    adSoyad.setVisible(false);
                    sifre.setVisible(false);
                    adSoyadOnayla.setVisible(false);
                    kullaniciID = rs.getInt("kullaniciNo");
                    girisID = kullaniciID;
                    System.out.println("Oyuncu ID = "+kullaniciID);                                        
                }  
                
                oyuncular.addItem(rs.getString("kullaniciAdi")+" "+rs.getString("kullaniciSoyadi"));
                k = new Kullanici(rs.getInt("kullaniciNo"), rs.getString("kullaniciAdi"), rs.getString("kullaniciSoyadi"));
                users.add(k);
                
                rs2 = ilanStm.executeQuery("select * from calisan");
                
                while(rs2.next())
                    if(rs2.getInt("kullaniciNo") == rs.getInt("kullaniciNo")){
                        meslek.setVisible(true);
                        
                        if(rs2.getString("isletmeTuru").equals("market"))
                            meslek.setText("Kasiyer");
                        
                        if(rs2.getString("isletmeTuru").equals("magaza"))
                            meslek.setText("Satış Görevlisi");
                        
                        if(rs2.getString("isletmeTuru").equals("emlak"))
                            meslek.setText("Emlakçı");
                    }
            }
            
            arsaEkrani();
            this.zamanBaslat();
        }                      
    }
    
}
