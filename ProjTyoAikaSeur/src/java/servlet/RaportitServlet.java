/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tietokanta.Kirjaus;
import tietokanta.TietokantaYhteys;

/**
 *
 * @author mhaanran
 */
public class RaportitServlet extends HttpServlet {

    private TietokantaYhteys db = new TietokantaYhteys();
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession(false);
        if(session.getAttribute("ktunnus")==null) {
            response.sendRedirect("/ProjTyoAikaSeur/Kirjaudu");
        }
        String projektinNimi = request.getParameter("name");
        String alkamisPaiva = request.getParameter("alkamisPaiva");
        Calendar alkamisPaivaCalender = Calendar.getInstance();
        if (alkamisPaiva!=null) {
            alkamisPaivaCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alkamisPaiva.substring(0, alkamisPaiva.length() - 6)));
            alkamisPaivaCalender.set(Calendar.MONTH, Integer.parseInt(alkamisPaiva.substring(2, alkamisPaiva.length() - 4)) - 1);
            alkamisPaivaCalender.set(Calendar.YEAR, Integer.parseInt(alkamisPaiva.substring(4, alkamisPaiva.length())));
        }
        java.sql.Date alkamisPaivaDate = new java.sql.Date(alkamisPaivaCalender.getTime().getTime());
        
        String loppumisPaiva = request.getParameter("loppumisPaiva");
        Calendar loppumisPaivaCalender = Calendar.getInstance();
        if (loppumisPaiva!=null) {
            loppumisPaivaCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(loppumisPaiva.substring(0, loppumisPaiva.length() - 6)));
            loppumisPaivaCalender.set(Calendar.MONTH, Integer.parseInt(loppumisPaiva.substring(2, loppumisPaiva.length() - 4)) - 1);
            loppumisPaivaCalender.set(Calendar.YEAR, Integer.parseInt(loppumisPaiva.substring(4, loppumisPaiva.length())));
        }
        java.sql.Date loppumisPaivaDate = new java.sql.Date(loppumisPaivaCalender.getTime().getTime());
        
        request.setAttribute("projektinNimi", projektinNimi);
        ArrayList<Kirjaus> viikkoRaportti = db.viikkoRaportti(alkamisPaivaDate, loppumisPaivaDate);
        request.setAttribute("viikkoraportti", viikkoRaportti);
        dispatcher = request.getRequestDispatcher("raportit.jsp");
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
