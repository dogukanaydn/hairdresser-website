
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@ManagedBean(name ="Hata")
@SessionScoped
public class Hata {
    private String hata;
    
    
      public String getHata() {
        return hata;
    }

    public void setHata(String hata) {
        this.hata = hata;
    }

}
