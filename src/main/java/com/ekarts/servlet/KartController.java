package com.ekarts.servlet;

import com.ekarts.dao.KartDao;
import com.ekarts.dto.Kart;
import com.ekarts.enums.EnumKartType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@WebServlet("/kart")
public class KartController extends HttpServlet {

    private static final long serialVersionUID = -7558166539389234332L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperam l'acció a realitzar i es crida a la funció corresponent
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "edit":
                    this.editKart(request, response);
                    break;
                default:
                    this.showListKart(request, response);
            }
        } else {
            this.showListKart(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Recuperam l'acció a realitzar i es crida a la funció corresponent
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "delete":
                    this.deleteKart(request, response);
                    break;
                case "insert":
                    this.insertKart(request, response);
                    break;
                case "update":
                    this.updateKart(request, response);
                    break;
                default:
                    this.showListKart(request, response);
            }
        } else {
            this.showListKart(request, response);
        }
    }

    private void showListKart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Kart> karts = new KartDao().listar();

        System.out.println("karts = " + karts);

        // Dades a desar a la sessió de la classe
        HttpSession session = request.getSession();
        session.setAttribute("karts", karts);
        //session.setAttribute("totalKarts", karts.size());
        //session.setAttribute("saldoTotal", this.calcularSaldoTotal(karts));

        // request.getRequestDispatcher("frmKart.jsp").forward(request, response);
        response.sendRedirect("frmKart.jsp");
    }

    private void editKart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperamos el idKart
        int idKart = Integer.parseInt(request.getParameter("idKart"));
        Kart kart = new KartDao().findById(new Kart(idKart));
        request.setAttribute("kart", kart);
        String jspEditar = "/editKart.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);

    }

    private void insertKart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");

        // recuperamos los valores del formulario agregarKart
        String nombre = request.getParameter("name");
        EnumKartType tipo = EnumKartType.valueOf(request.getParameter("kartTypeEnum"));
        double precio = 0;
        String precioString = request.getParameter("pricePerMinute");
        if (precioString != null && !"".equals(precioString)) {
            precio = Double.parseDouble(precioString);
        }

        // Creamos el objeto de kart (modelo)
        Kart kart = new Kart(nombre, tipo, precio);

        // Insertamos el nuevo objeto en la base de datos
        int registrosModificados = new KartDao().create(kart);
        System.out.println("Registres modificats:" + registrosModificados);

        // Redirigimos hacia accion por default
        this.showListKart(request, response);
    }

    private void updateKart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");
        System.out.println("Modificar kart");

        // Recuperam els valors del formulari editKart
        int idKart = Integer.parseInt(request.getParameter("idKart"));
        String nombre = request.getParameter("name");
        System.out.println("name:" + nombre);

        EnumKartType tipo = EnumKartType.valueOf(request.getParameter("kartTypeEnum"));
        double precio = 0;
        String precioString = request.getParameter("precio");
        if (precioString != null && !"".equals(precioString)) {
            precio = Double.parseDouble(precioString);
        }

        // Creamos el objeto de kart (modelo)
        Kart kart = new Kart(idKart, nombre, tipo, precio);

        // Modificar el objeto en la base de datos
        int registrosModificados = new KartDao().update(kart);
        System.out.println("Registres modificats:" + registrosModificados);

        // Redirigimos hacia accion por default
        this.showListKart(request, response);
    }

    private void deleteKart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // recuperamos los valores del formulario editarKart
        int idKart = Integer.parseInt(request.getParameter("idKart"));

        // Creamos el objeto de kart (modelo)
        Kart kart = new Kart(idKart);

        // Eliminamos el objeto en la base de datos
        int registrosModificados = new KartDao().delete(kart);
        System.out.println("Registres modificats:" + registrosModificados);

        // Redirigimos hacia accion por default
        this.showListKart(request, response);
    }

}
