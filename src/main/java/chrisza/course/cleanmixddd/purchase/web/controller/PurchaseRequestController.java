package chrisza.course.cleanmixddd.purchase.web.controller;

import chrisza.course.cleanmixddd.purchase.domain.valueobjects.NewPurchaseRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class PurchaseRequestController {

    @PostMapping("/pr")
    public NewPurchaseRequest create(@RequestBody NewPurchaseRequest model) {
        return model;
    }

    @PostMapping("/pr")
    public NewPurchaseRequest approve(@RequestBody NewPurchaseRequest model) {
        return model;
    }
}