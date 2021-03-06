/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import tietokanta.Kayttaja;
import tietokanta.Projekti;
import tietokanta.TietokantaYhteys;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Projektien lisääminen tapahtuu tätä servletiä käyttäen.
 * @author mhaanran
 */
public class ProjektitServlet extends HttpServlet {
    
    private TietokantaYhteys db = new TietokantaYhteys();
    
    public ProjektitServlet() {   
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
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession(false);
        if (session.getAttribute("ktunnus")!=null) {
            if (request.getParameter("projektin_nimi") != null 
            && !request.getParameter("projektin_nimi").isEmpty()) {
                String projektinNimi = request.getParameter("projektin_nimi");
                float tyoTuntiBudjetti=0;
                if(!request.getParameter("tyoTuntiBudjetti").isEmpty()) {
                    tyoTuntiBudjetti = Float.parseFloat(request.getParameter("tyoTuntiBudjetti"));      
                }
                String alkamisPaivaMaara = request.getParameter("alkamisPaivaMaara");
                Calendar aCalendar = Calendar.getInstance();
                if(!alkamisPaivaMaara.isEmpty()&&alkamisPaivaMaara.length()==8) {
                    aCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alkamisPaivaMaara.substring(0, alkamisPaivaMaara.length()-6)));
                    aCalendar.set(Calendar.MONTH, Integer.parseInt(alkamisPaivaMaara.substring(2, alkamisPaivaMaara.length()-4))-1); 
                    aCalendar.set(Calendar.YEAR, Integer.parseInt(alkamisPaivaMaara.substring(4, alkamisPaivaMaara.length())));           
                }
                java.sql.Date dateA = new java.sql.Date(aCalendar.getTime().getTime());
                String loppumisPaivaMaara = request.getParameter("loppumisPaivaMaara");
                Calendar lCalendar = Calendar.getInstance();
                if(!loppumisPaivaMaara.isEmpty()&&loppumisPaivaMaara.length()==8) {
                    lCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(loppumisPaivaMaara.substring(0, loppumisPaivaMaara.length()-6)));
                    lCalendar.set(Calendar.MONTH, Integer.parseInt(loppumisPaivaMaara.substring(2, loppumisPaivaMaara.length()-4))-1);
                    lCalendar.set(Calendar.YEAR, Integer.parseInt(loppumisPaivaMaara.substring(4, loppumisPaivaMaara.length())));
                }
                java.sql.Date dateB = new java.sql.Date(lCalendar.getTime().getTime());
                if(!db.onkoProjektiOlemassa(projektinNimi)) {
                    Projekti lisattava = new Projekti(projektinNimi,tyoTuntiBudjetti,dateA,dateB);
                    db.lisaaProjekti(lisattava);                
                }
                else {
                    request.setAttribute("virhe", "Projekti nimellä "+projektinNimi+" on jo olemassa!");
                }
            }
            List<Kayttaja> kayttajat = db.getKayttajat();
            request.setAttribute("kayttajat", kayttajat);
            List<Projekti> projektit = db.getProjektit();
            request.setAttribute("projektit", projektit);
            List<String> kayttajanProjektit =db.getKayttajanProjektit((String)session.getAttribute("ktunnus"));
            request.setAttribute("kayttajanProjektit", kayttajanProjektit);
            dispatcher = request.getRequestDispatcher("kirjautunut.jsp");
            dispatcher.forward(request, response);
            
        } else {
            response.sendRedirect("/ProjTyoAikaSeur/Kirjaudu");
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
            Logger.getLogger(ProjektitServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProjektitServlet.class.getName()).log(Level.SEVERE, null, ex);
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
