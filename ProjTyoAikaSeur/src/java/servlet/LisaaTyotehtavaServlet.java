/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tietokanta.Kayttaja;
import tietokanta.Projekti;
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if(session.getAttribute("ktunnus")==null) {
            response.sendRedirect("/ProjTyoAikaSeur/Kirjaudu");
        }
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
        Projekti projekti=db.getProjekti(projektinNimi);
        float projTyotuntibudjetti = 0;
        if(projekti!=null) {
        projTyotuntibudjetti = projekti.getBudjetoidutTyotunnit();
        request.setAttribute("projTyotuntibudjetti", projTyotuntibudjetti);
       
        request.setAttribute("projekti",projekti);
        float tyotehtavienTuntienSumma=0;
        List<Tyotehtava> tyotehtavat = db.getTyotehtavat(projektinNimi);
        for (Tyotehtava tyotehtava : tyotehtavat) {
            tyotehtavienTuntienSumma += tyotehtava.getBudjetoidutTyotunnit();
        }
        request.setAttribute("tyotehtavienTuntienSumma", tyotehtavienTuntienSumma);
        request.setAttribute("tyotehtavat", tyotehtavat);
        List<String> tyontekijat = db.getProjektinTyontekijat(projektinNimi);
        for (String string : tyontekijat) {
            System.out.println(string);
        }
        request.setAttribute("tyontekijat", tyontekijat);
        List<Kayttaja> kayttajat = db.getKayttajat();
        request.setAttribute("kayttajat", kayttajat);
        }
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
