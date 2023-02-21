package com.wff.goods.entity;

import com.wff.sellergoods.pojo.Specification;
import com.wff.sellergoods.pojo.SpecificationOption;

import java.util.List;

public class SpecificationItemOption {
    private Specification specification;
    private List<SpecificationOption> specificationOptions;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptions() {
        return specificationOptions;
    }

    public void setSpecificationOptions(List<SpecificationOption> specificationOptions) {
        this.specificationOptions = specificationOptions;
    }
}
