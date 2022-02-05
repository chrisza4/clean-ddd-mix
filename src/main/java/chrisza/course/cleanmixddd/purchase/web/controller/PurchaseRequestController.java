package chrisza.course.cleanmixddd.purchase.web.controller;

import org.springframework.web.bind.annotation.*;

import chrisza.course.cleanmixddd.purchase.web.requests.*;

@RestController
public class PurchaseRequestController {

    @PostMapping("/pr")
    public PurchaseRequest create(@RequestBody PurchaseRequest model) {
        return model;
    }

    @PostMapping("/pr")
    public PurchaseRequest approve(@RequestBody PurchaseRequest model) {
        return model;
    }
}