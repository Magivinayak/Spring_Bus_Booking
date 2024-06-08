package com.example.VoyageTravel.controller;

import com.example.VoyageTravel.entity.BusEntity;
import com.example.VoyageTravel.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BusController {

    @Autowired
    private BusService busService;


    // Show bus registration form
    @GetMapping("/busRegister")
    public String showBusRegisterForm(Model model) {
        model.addAttribute("addBus", new BusEntity());
        return "busRegister";
    }

    // Save bus entity
    @PostMapping("/saveBus")
    public String addBus(@ModelAttribute("addBus") BusEntity busEntity) {
        busService.addBus(busEntity);
        return "redirect:/adminDashboard";
    }

    // Search for buses
    @PostMapping("/search")
    public String search(@RequestParam("from") String from, @RequestParam("to") String to,
                         @RequestParam("date") String date, Model model) {
        try {

            List<BusEntity> buses = busService.searchBuses(from, to ,date);
            model.addAttribute("buses", buses);
            return "busDetails";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while searching for buses.");
            return "error";
        }
    }


    //List of Buses
    @GetMapping("/adminDashboard")
    public String busList(Model model){
        model.addAttribute("BusList",busService.getAllBuses());
        return "adminDashboard";
    }

    //Update bus form
    @GetMapping("/updateBus/{id}")
    public String updateBus(@PathVariable("id") Long id,Model model){
        BusEntity busEntity=busService.getBusById(id);
        model.addAttribute("editBus",busEntity);
        return "updateBus";
    }

    //Update bus data
    @RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.PUT})
    public String UpdateEmployee(@ModelAttribute("editBus") BusEntity busEntity){
        System.out.println("update id "+busEntity.getBusId());
        busService.updateBus(busEntity);
        return "redirect:/adminDashboard";
    }


    // Delete a bus
    @RequestMapping(value = "/deleteBus/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteBus(@PathVariable("id") Long id) {
        busService.deleteBusById(id);
        return "redirect:/adminDashboard";
    }







}
