/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import tietokanta.Kayttaja;
import tietokanta.Rekisteri;
import tietokanta.TietokantaYhteys;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhaanran
 */
public class KirjauduServlet extends HttpServlet {

//    private Rekisteri rekisteri = new Rekisteri();
    private TietokantaYhteys rekisteri = new TietokantaYhteys();
    
    public KirjauduServlet() {
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
            throws ServletException, IOException, SQLException {
        HttpSession session;         
        RequestDispatcher dispatcher;
        
        String kayttajatunnus = request.getParameter("kayttajatunnus");
        String salasana = request.getParameter("salasana");
        Kayttaja kayttaja;
        
        if(kayttajatunnus!=null) {
            kayttaja = new Kayttaja(kayttajatunnus,salasana); 
        }
        else {
            kayttaja=null;
        }
        
        if(kayttaja!=null&&rekisteri.onkoKayttajaOlemassa(kayttaja.getKayttajatunnus(),kayttaja.getSalasana())) { 
            session = request.getSession(true);
            session.setAttribute("ktunnus", kayttajatunnus);
            if(rekisteri.mikaRooli(kayttajatunnus)) {
                session.setAttribute("rooli", true);
            }
//            request.setAttribute("ktunnus", kayttajatunnus);
            response.sendRedirect("/ProjTyoAikaSeur/Projektit");
//            dispatcher = request.getRequestDispatcher("kirjautunut.jsp");
//            dispatcher.forward(request, response);
        }
        else if(kayttaja!=null&&!rekisteri.onkoKayttajaOlemassa(kayttaja.getKayttajatunnus(),kayttaja.getSalasana())) {
            request.setAttribute("viesti", "Väärä käyttäjätunnus tai salasana!");
            dispatcher = request.getRequestDispatcher("kirjaudu.jsp");        
            dispatcher.forward(request, response);
        }
        else {
            dispatcher = request.getRequestDispatcher("kirjaudu.jsp");        
            dispatcher.forward(request, response);
        }     
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(KirjauduServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(KirjauduServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
