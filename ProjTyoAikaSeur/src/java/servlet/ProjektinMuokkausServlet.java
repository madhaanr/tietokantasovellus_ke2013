/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tietokanta.Projekti;
import tietokanta.TietokantaYhteys;

/**
 *
 * @author mhaanran
 */
public class ProjektinMuokkausServlet extends HttpServlet {

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
        String projektinNimi="";
        HttpSession session = request.getSession(false);
        if (request.getParameter("projektin_nimi") != null) {
            projektinNimi = request.getParameter("projektin_nimi");
            float tyoTuntiBudjetti = Float.parseFloat(request.getParameter("tyoTuntiBudjetti"));
            String alkamisPaivaMaara = request.getParameter("alkamisPaivaMaara");
            Calendar aCalendar = Calendar.getInstance();
            if (!alkamisPaivaMaara.isEmpty()) {
                aCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(alkamisPaivaMaara.substring(0, alkamisPaivaMaara.length() - 6)));
                aCalendar.set(Calendar.MONTH, Integer.parseInt(alkamisPaivaMaara.substring(2, alkamisPaivaMaara.length() - 4)) - 1);
                aCalendar.set(Calendar.YEAR, Integer.parseInt(alkamisPaivaMaara.substring(4, alkamisPaivaMaara.length())));
            }
            java.sql.Date dateA = new java.sql.Date(aCalendar.getTime().getTime());
            String loppumisPaivaMaara = request.getParameter("loppumisPaivaMaara");
            Calendar lCalendar = Calendar.getInstance();
            System.out.println(loppumisPaivaMaara);
            if (!loppumisPaivaMaara.isEmpty()) {
                lCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(loppumisPaivaMaara.substring(0, loppumisPaivaMaara.length() - 6)));
                lCalendar.set(Calendar.MONTH, Integer.parseInt(loppumisPaivaMaara.substring(2, loppumisPaivaMaara.length() - 4)) - 1);
                lCalendar.set(Calendar.YEAR, Integer.parseInt(loppumisPaivaMaara.substring(4, loppumisPaivaMaara.length())));
            }
            java.sql.Date dateB = new java.sql.Date(lCalendar.getTime().getTime());
            System.out.println("täällä myös");
            if (db.onkoProjektiOlemassa(projektinNimi)) {
                System.out.println("ja lopulta täällä");
                Projekti muokattava = new Projekti(projektinNimi, tyoTuntiBudjetti, dateA, dateB);
                db.muokkaaProjektia(muokattava);
            }
        }
        response.sendRedirect("/ProjTyoAikaSeur/LisaaTyotehtava?name="+projektinNimi);
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
