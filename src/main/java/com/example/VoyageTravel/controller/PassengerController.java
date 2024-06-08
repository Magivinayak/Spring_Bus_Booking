package com.example.VoyageTravel.controller;

import com.example.VoyageTravel.entity.BusEntity;
import com.example.VoyageTravel.entity.PassengerEntity;
import com.example.VoyageTravel.service.BusService;
import com.example.VoyageTravel.service.PassengerService;
import com.example.VoyageTravel.service.PdfGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private BusService busService;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    private PassengerEntity passengerBookingPdf;



    // Show seat booking page
    @GetMapping("/seatBooking")
    public String showSeatBookingPage(@RequestParam("busId") Long busId, Model model) {
       try{
            model.addAttribute("busId", busId);
            List<Integer> selectedSeats = passengerService.getSelectedSeatsByBusId(busId);
            model.addAttribute("passedSeats", selectedSeats);
            return "seatBooking";
    } catch (Exception e) {
        // Handle exceptions appropriately
           e.printStackTrace();
           model.addAttribute("errorMessage","An error occurred while calling seat booking page");
           return "error";
    }

}

    // Book seats
    @PostMapping("/bookSeats")
    public String bookSeats(@RequestParam("selectedSeats") String selectedSeats,
                            @RequestParam("totalFare") Integer totalFare,
                            @RequestParam("busId") Long busId,
                            @RequestParam("seatCount") Integer seatCount,
                            Model model) {

        try {
            PassengerEntity passengerEntity = new PassengerEntity();
            model.addAttribute("passengerEntity", passengerEntity);
            model.addAttribute("selectedSeats", selectedSeats);
            model.addAttribute("totalFare", totalFare);
            model.addAttribute("busId", busId);
            model.addAttribute("seatCount", seatCount);

            return "passenger";
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while booking seats.");
            return "error";
        }
    }

    // Process passenger details form
    @PostMapping("/newPassenger")
    public String processPassengerDetailsForm(@RequestParam("selectedSeats") String selectedSeats,
                                              @RequestParam("totalFare") Double totalFare,
                                              @RequestParam("busId") Long busId,
                                              @RequestParam("seatCount") Integer seatCount,
                                              @ModelAttribute("passengerEntity") PassengerEntity passengerEntity,
                                              Model model
                                              ) {
        try {
            BusEntity busEntity = busService.getBusById(busId);

            busService.updateAvailableSeats(busId,seatCount);//Update available seats

            passengerEntity.setBusId(busId);
            passengerEntity.setBusName(busEntity.getBusName());
            passengerEntity.setStartLocation(busEntity.getRouteFrom());
            passengerEntity.setEndLocation(busEntity.getRouteTo());
            passengerEntity.setJourneyDate(busEntity.getDepartureDate());
            passengerEntity.setSelectedSeat(selectedSeats);
            passengerEntity.setTotalFare(totalFare);
            passengerService.createPassenger(passengerEntity);

            this.passengerBookingPdf = passengerEntity;
            return "pdfTemplate";
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while Creating Passenger or" +
                                     " While updating the available seats");
            return "error";
        }
    }

    // Generate PDF
    @PostMapping("/generatePdf")
    public String generatePdf(Model model) {
        PassengerEntity passengerEntity = this.passengerBookingPdf;
        Map<String, Object> data = new HashMap<>();
        data.put("passengerEntity", passengerEntity);
        try {
            pdfGenerateService.generatePdfFile("pdfTemplate", data, "generatedPdf.pdf");
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while creating PDF document");
            return "error";
        }
    }

    // Download PDF
    @GetMapping("/downloadPdf")
    public ResponseEntity<byte[]> downloadPdf() throws IOException {
        File pdfFile = new File("C:\\Users\\Magesh\\Desktop\\Project\\VoyageTravel\\generatedPdf.pdf");
        byte[] pdfContent = Files.readAllBytes(pdfFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Ticket.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

}
