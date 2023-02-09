package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

    @RequestMapping(value = "/ui/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/ui/employee")
    public ModelAndView employee() {
        return mav("employee.html");
    }

    @RequestMapping(value = "/ui/admin")
    public ModelAndView admin() {
        return mav("user.html");
    }

    @RequestMapping(value = "/ui/brand")
    public ModelAndView brand() {
        return mav("brand.html");
    }

    @RequestMapping(value = "/ui/products")
    public ModelAndView products() {
        return mav("product.html");
    }

    @RequestMapping(value = "/ui/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/order")
    public ModelAndView customer() {
        return mav("order.html");
    }


    @RequestMapping(value = "/ui/report")
    public ModelAndView reports() {
        return mav("reports.html");
    }

    @RequestMapping(value = "/ui/report/inventoryReport")
    public ModelAndView inventoryReport() {
        return mav("inventoryReport.html");
    }

    @RequestMapping(value = "/ui/report/brandReport")
    public ModelAndView brandReport() {
        return mav("brandReport.html");
    }

    @RequestMapping(value = "/ui/report/salesReport")
    public ModelAndView salesReport() {
        return mav("salesReport.html");
    }

    @RequestMapping(value = "/ui/report/dailySalesReport")
    public ModelAndView dailySalesReport() {
        return mav("dailySalesReport.html");
    }


}
