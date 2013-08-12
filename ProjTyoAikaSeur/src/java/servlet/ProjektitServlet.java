/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import database.Kayttaja;
import database.Projekti;
import database.Rekisteri;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
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

    private Rekisteri rekisteri = new Rekisteri();
    private Projekti proj;
    
    public ProjektitServlet() {
        proj = new Projekti();
        proj.setProjektinNimi("Testi projekti");
        rekisteri.lisaaProjekti(proj);
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
        RequestDispatcher dispatcher;      
        if(request.getParameter("projektin_nimi")!=null) {
            String projektinNimi = request.getParameter("projektin_nimi");
            Projekti lisattava = new Projekti();
            lisattava.setProjektinNimi(projektinNimi);
            rekisteri.lisaaProjekti(lisattava); 
        }      
        List<Projekti> projektit = rekisteri.getProjektit();
        request.setAttribute("projektit", projektit);
        dispatcher = request.getRequestDispatcher("kirjautunut.jsp");
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