package com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.controllers;

import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.Investor;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.Product;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.WithdrawalNotice;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.services.InvestorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investors")
public class InvestmentController {

    private final InvestorService investorService;

    public InvestmentController(InvestorService investorService) {
        this.investorService = investorService;
    }
    @GetMapping
    public List<Investor> getAll() {
        return investorService.getAllInvestors();
    }
    @PostMapping
    public ResponseEntity<Investor> createInvestor(@Valid @RequestBody Investor investor) {
        Investor createdInvestor = investorService.createInvestor(investor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvestor);
    }

    @GetMapping("/{investorId}")
    public ResponseEntity<Investor> getInvestor(@PathVariable int investorId) {
        Investor investor = investorService.getInvestorById(investorId);
        if (investor != null) {
            return ResponseEntity.ok(investor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{investorId}/products")
    public ResponseEntity<List<Product>> getInvestorProducts(@PathVariable int investorId) {
        List<Product> products = investorService.getProductsByInvestor(investorId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{investorId}/products")
    public ResponseEntity<Object> createProductForInvestor(@PathVariable int investorId,
                                                           @Valid @RequestBody Product product) {

        ResponseEntity<Object> createdProduct = investorService.createProduct(investorId, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);

    }

    @PostMapping("/{investorId}/{productId}/withdrawal")
    public ResponseEntity<Object> createProductForWithdrawal(@PathVariable int investorId,
                                                             @PathVariable int productId,
                                                             @Valid @RequestBody WithdrawalNotice withdrawalNotice) {
        System.out.println(investorId+" "+productId);

        ResponseEntity<Object> createdWithdrawalNotice = investorService.createWithdrawalNotice(investorId, productId, withdrawalNotice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWithdrawalNotice);
    }

}
