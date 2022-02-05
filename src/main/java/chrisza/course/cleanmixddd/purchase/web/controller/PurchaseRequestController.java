package chrisza.course.cleanmixddd.purchase.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import chrisza.course.cleanmixddd.purchase.web.requests.*;

@RestController
public class PurchaseRequestController {

    @PostMapping("/pr")
    public PurchaseRequest greeting(@RequestBody PurchaseRequest model) {
        return model;
    }

}