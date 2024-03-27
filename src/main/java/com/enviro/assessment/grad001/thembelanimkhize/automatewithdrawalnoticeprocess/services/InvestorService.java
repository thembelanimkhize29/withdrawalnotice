package com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.services;

import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.Investor;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.Product;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.WithdrawalNotice;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.exception.InvestorNotFoundException;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.repository.InvestorRepository;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.repository.ProductRepository;
import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.repository.WithdrawalNoticeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {


    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;
    private final WithdrawalNoticeRepository withdrawalNoticeRepository;

    public InvestorService(InvestorRepository investorRepository, ProductRepository productRepository, WithdrawalNoticeRepository withdrawalNoticeRepository) {
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
        this.withdrawalNoticeRepository = withdrawalNoticeRepository;
    }

    public List<Investor> getAllInvestors() {
        return investorRepository.findAll();
    }

    public Investor createInvestor(Investor investor) {
        return investorRepository.save(investor);
    }

    public Investor getInvestorById(int investorId) {
        return investorRepository.findById(investorId).orElse(null);
    }

    public List<Product> getProductsByInvestor(int investorId) {
        Optional<Investor> investor=investorRepository.findById(investorId);
        if(investor.isEmpty())
     		throw new InvestorNotFoundException("investor:"+investor);

        return investor.get().getProduct();
    }

    public ResponseEntity<Object> createProduct(int investorId, Product product) {
        Optional<Investor> investorOptional =investorRepository.findById(investorId);

        if(investorOptional.isEmpty())
            throw new InvestorNotFoundException("investor:"+investorId);

        Investor investor = investorOptional.get();
        Product.ProductType productType = product.getType();
        if (productType == Product.ProductType.RETIREMENT && investor.getAge() < 65) {
            throw new /*InvalidInvestorAgeException**/ InvestorNotFoundException("Investor must be over 65 years old to create a retirement product.");
        }


        product.setInvestor(investorOptional.get());
        System.out.println(product.getCurrentBalance());
        Product savedProduct= productRepository.save(product);
                        /**  /investors/2
                         *   /investors/{investorId}     */
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()//current url
                .path("/{investorId}")
                .buildAndExpand(savedProduct.getProductId())//created id, id of a product created
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Object> createWithdrawalNotice(int investorId, int productId, WithdrawalNotice withdrawalNotice) {
        Optional<Investor> investorOptional = investorRepository.findById(investorId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (investorOptional.isPresent() && productOptional.isPresent()) {
            Investor investor = investorOptional.get();
            Product product = productOptional.get();

            BigDecimal withdrawalAmount = withdrawalNotice.getWithdrawalAmount();
            BigDecimal currentBalance = BigDecimal.valueOf(product.getCurrentBalance());
            BigDecimal maxWithdrawalAmount = currentBalance.multiply(BigDecimal.valueOf(0.9));
            if (withdrawalAmount.compareTo(maxWithdrawalAmount) > 0) {
                return ResponseEntity.badRequest().body("Withdrawal amount cannot exceed 90% of the current balance.");
            }

            // Calculate the closing balance
            BigDecimal balanceBeforeWithdrawal = BigDecimal.valueOf(product.getCurrentBalance());
            BigDecimal closingBalance = balanceBeforeWithdrawal.subtract(withdrawalAmount);

            withdrawalNotice.setInvestor(investor);
            withdrawalNotice.setProduct(product);


            WithdrawalNotice savedWithdrawalNotice = withdrawalNoticeRepository.save(withdrawalNotice);

            //notification information
            String notificationMessage = "Withdrawal notice created successfully for investor " + investorId + " and product " + productId +
                    ". Balance before withdrawal: " + balanceBeforeWithdrawal + ", Amount withdrawn: " + withdrawalAmount + ", Closing balance: " + closingBalance;
            return ResponseEntity.ok().body(notificationMessage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //}

}
