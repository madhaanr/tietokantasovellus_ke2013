/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tietokanta.Kayttaja;
import tietokanta.TietokantaYhteys;
import tietokanta.Tyotehtava;

/**
 *
 * @author mhaanran
 */
public class LisaaTyotehtavaServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        String projektinNimi = request.getParameter("name");
        request.setAttribute("projektinNimi", projektinNimi);
        String tyotehtavanNimi = request.getParameter("tyotehtavanNimi");
        if (tyotehtavanNimi != null) {
            float budjetoidutTyotunnit = Float.parseFloat(request.getParameter("budjetoidutTyotunnit"));
            if (!db.onkoTyotehtavaOlemassa(tyotehtavanNimi,projektinNimi)) {
                Tyotehtava tyotehtava = new Tyotehtava(tyotehtavanNimi, budjetoidutTyotunnit, projektinNimi);
                db.lisaaTyotehtava(tyotehtava);
            } else {
                request.setAttribute("viesti", "Tyotehtava nimellä " + tyotehtavanNimi + " on jo olemassa!");
            }
        }
        List<Tyotehtava> tyotehtavat = db.getTyotehtavat(projektinNimi);
        request.setAttribute("tyotehtavat", tyotehtavat);
        List<String> tyontekijat = db.getProjektinTyontekijat(projektinNimi);
        request.setAttribute("tyontekijat", tyontekijat);
        RequestDispatcher dispatcher = request.getRequestDispatcher("projekti.jsp");
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
