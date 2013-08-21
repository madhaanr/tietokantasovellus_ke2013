/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import tietokanta.Kayttaja;
import tietokanta.Projekti;
import tietokanta.Rekisteri;
import tietokanta.TietokantaYhteys;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhaanran
 */
public class ProjektitServlet extends HttpServlet {
    
    private TietokantaYhteys rekisteri = new TietokantaYhteys();
    
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
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession(false);
        if (session.getAttribute("ktunnus")!=null) {
            if (request.getParameter("projektin_nimi") != null) {
                String projektinNimi = request.getParameter("projektin_nimi");
                float tyoTuntiBudjetti = Float.parseFloat(request.getParameter("tyoTuntiBudjetti"));
                SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");
                String alkamisPaivaMaara = request.getParameter("alkamisPaivaMaara");
                
                String loppumisPaivaMaara = request.getParameter("loppumisPaivaMaara");
                if(rekisteri.onkoProjektiOlemassa(projektinNimi)) {
                    Projekti lisattava = new Projekti(projektinNimi);
                    rekisteri.lisaaProjekti(lisattava);                
                }
                else {
                    request.setAttribute("viesti", "Projekti nimellä "+projektinNimi+" on jo olemassa!");
                }
            }
            
            List<Projekti> projektit = rekisteri.getProjektit();
            request.setAttribute("projektit", projektit);
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
