/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tietokanta.Kirjaus;
import tietokanta.TietokantaYhteys;
import tietokanta.Tyotehtava;

/**
 *
 * @author mhaanran
 */
public class KirjaaTuntejaServlet extends HttpServlet {

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
        String kayttajatunnus = (String) session.getAttribute("ktunnus");
        String paivamaara = request.getParameter("paivamaara");
        float tehdytTunnit = 0;
        if (request.getParameter("tehdytTunnit") != null) {
            tehdytTunnit = Float.parseFloat(request.getParameter("tehdytTunnit"));
            String selitys = request.getParameter("selitys");
            String tyotehtavanNimi = request.getParameter("tyotehtavanNimi");
            Calendar paivamaaraCalender = Calendar.getInstance();
            if (!paivamaara.isEmpty()) {
                paivamaaraCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(paivamaara.substring(0, paivamaara.length() - 6)));
                paivamaaraCalender.set(Calendar.MONTH, Integer.parseInt(paivamaara.substring(2, paivamaara.length() - 4)) - 1);
                paivamaaraCalender.set(Calendar.YEAR, Integer.parseInt(paivamaara.substring(4, paivamaara.length())));
            }
            java.sql.Date paivamaaraDate = new java.sql.Date(paivamaaraCalender.getTime().getTime());
            Kirjaus kirjaus = new Kirjaus(paivamaaraDate, tehdytTunnit, selitys, kayttajatunnus, projektinNimi, tyotehtavanNimi);
            db.lisaaTyotuntiKirjaus(kirjaus);
        }
        request.setAttribute("projektinNimi", projektinNimi);
        ArrayList<Kirjaus> kirjaukset = db.getKirjaukset(kayttajatunnus, projektinNimi);
        request.setAttribute("kirjaukset", kirjaukset);
        List<String> tyotehtavat = db.getProjektinTyotehtavat(projektinNimi);
        request.setAttribute("tyotehtavat", tyotehtavat);

        dispatcher = request.getRequestDispatcher("kirjaatunteja.jsp");
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
