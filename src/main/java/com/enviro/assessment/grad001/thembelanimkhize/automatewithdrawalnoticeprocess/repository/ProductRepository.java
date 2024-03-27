package com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.repository;

import com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
