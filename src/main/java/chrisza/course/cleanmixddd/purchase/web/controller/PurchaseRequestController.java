package chrisza.course.cleanmixddd.purchase.web.controller;

import chrisza.course.cleanmixddd.purchase.DependencyResolver;
import chrisza.course.cleanmixddd.purchase.domain.entities.PurchaseRequest;
import chrisza.course.cleanmixddd.purchase.domain.exceptions.UnapprovableException;
import chrisza.course.cleanmixddd.purchase.domain.valueobjects.NewPurchaseRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

@RestController
public class PurchaseRequestController {

    @PostMapping("/pr")
    public PurchaseRequest create(@RequestBody NewPurchaseRequest model) {
        var service = DependencyResolver.getService();
        return service.AddNewPurchaseRequest(model);
    }

    @PostMapping("/pr/{id}/approve")
    public PurchaseRequest approve(@PathVariable int id) throws UnapprovableException, ChangeSetPersister.NotFoundException {
        var service = DependencyResolver.getService();
        return service.Approve(id);
    }
}