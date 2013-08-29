/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import tietokanta.Kayttaja;
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

//    private Rekisteri db = new Rekisteri();
    private TietokantaYhteys db = new TietokantaYhteys();
    
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession(false);
        if(session!=null) {
            session.invalidate();    
        }
        RequestDispatcher dispatcher;
        
        String kayttajatunnus = request.getParameter("kayttajatunnus");
        String salasana = request.getParameter("salasana");
        String knimi="";
        Kayttaja kayttaja;
        session = request.getSession(true);
        if(kayttajatunnus!=null) {
            kayttaja = new Kayttaja(kayttajatunnus,salasana); 
        }
        else {
            kayttaja=null;
        }
        if(db.getKayttajanNimi(kayttajatunnus)!=null) {
            knimi=db.getKayttajanNimi(kayttajatunnus);
        }
        if(kayttaja!=null&&db.onkoKayttajaJaSalasanaOikein(kayttaja.getKayttajatunnus(),kayttaja.getSalasana())) { 
            session.setAttribute("ktunnus", kayttajatunnus);
            session.setAttribute("knimi", knimi);
            if(db.mikaRooli(kayttajatunnus)) {
                session.setAttribute("rooli", true);
            }
            else {
                session.setAttribute("rooli", false);
            }
            response.sendRedirect("/ProjTyoAikaSeur/Projektit");
        }
        else if(kayttaja!=null&&!db.onkoKayttajaJaSalasanaOikein(kayttaja.getKayttajatunnus(),kayttaja.getSalasana())) {
            request.setAttribute("virhe", "Väärä käyttäjätunnus tai salasana!");
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
