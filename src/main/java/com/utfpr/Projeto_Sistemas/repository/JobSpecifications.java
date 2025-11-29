package com.utfpr.Projeto_Sistemas.repository;

import com.utfpr.Projeto_Sistemas.entities.Job;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class JobSpecifications {

    public static Specification<Job> likeTitle(String title) {
        if  (title == null || title.trim().equals("")) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.like(builder.lower(root.get("title")), "%" + title + "%");
        };
    }
    public static Specification<Job> likeArea(String area) {
        if  (area == null || area.trim().equals("")) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.like(builder.lower(root.get("area")), "%" + area + "%");
        };
    }
    public static Specification<Job> likeCompany(String company) {
        if  (company == null || company.trim().equals("")) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.like(builder.lower(root.get("company").get("name")), "%" + company + "%");
        };
    }
    public static Specification<Job> likeState(String state) {
        if  (state == null || state.trim().equals("")) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.like(builder.lower(root.get("state")), "%" + state + "%");
        };
    }
    public static Specification<Job> likeCity(String city) {
        if  (city == null || city.trim().equals("")) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.like(builder.lower(root.get("city")), "%" + city + "%");
        };
    }
    public static Specification<Job> salaryMax(BigDecimal max) {
        if  (max == null || max.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.lessThanOrEqualTo(root.get("salary"), max);
        };
    }
    public static Specification<Job> salaryMin(BigDecimal min) {
        if  (min == null || min.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return (root, query, builder) -> {
            return builder.greaterThanOrEqualTo(root.get("salary"), min);
        };
    }
}
