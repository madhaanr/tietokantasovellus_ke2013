/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import database.Kayttaja;
import database.Rekisteri;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mhaanran
 */
public class KirjauduServlet extends HttpServlet {

    private Rekisteri rekisteri = new Rekisteri();
    private Kayttaja admin;
    private Kayttaja marko;
    
    public KirjauduServlet() {
        admin = new Kayttaja("admin","123456","admin",true);
        rekisteri.lisaaKayttaja(admin);
        
        marko = new Kayttaja("marko","marko","marko",false);
        rekisteri.lisaaKayttaja(marko);
    }
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String kayttajatunnus = request.getParameter("kayttajatunnus");
        String salasana = request.getParameter("salasana");
        Kayttaja kayttaja = new Kayttaja(kayttajatunnus,salasana); 
        
        if(rekisteri.onkoKayttajaOlemassa(kayttaja.getKayttajatunnus(),kayttaja.getSalasana())) {     
            request.setAttribute("viesti", "Kirjautuminen onnistui!");     
        }
        else {
            request.setAttribute("viesti", "Väärä käyttätunnus tai salasana!");
        }
        RequestDispatcher dispatcher = 
                request.getRequestDispatcher("kirjaudu.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
